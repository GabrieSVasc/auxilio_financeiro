package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.exceptions.CampoVazioException;

/**
 * Classe de repositório responsável pela persistência dos objetos Gasto.
 * 
 * Essa classe abstrai o acesso ao sistema de arquivos, garantindo que a leitura e escrita
 * dos dados sejam feitas de forma consistente e segura.
 * 
 * O uso de métodos estáticos facilita o acesso global sem necessidade de instanciar objetos,
 * respeitando o princípio da responsabilidade única ao centralizar a persistência.
 *  
 * @author Halina Mochel
 */
public class GastoRepository {

    // Caminho do arquivo onde os gastos são armazenados
    private static final String CAMINHO_ARQUIVO = "files/gastos.txt";
    // Formato padrão para leitura e escrita das datas
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Garante que o arquivo e seu diretório existam antes de qualquer operação de leitura ou escrita.
     * Isso evita erros de arquivo não encontrado e facilita a criação automática do ambiente.
     * 
     * @throws IOException Caso ocorra erro ao criar diretórios ou arquivo
     */
    private static void garantirArquivo() throws IOException {
        Path path = Paths.get(CAMINHO_ARQUIVO);
        Path dir = path.getParent();
        if (dir != null && !Files.exists(dir)) {
            Files.createDirectories(dir); // Cria diretórios se não existirem
        }
        if (!Files.exists(path)) {
            Files.createFile(path); // Cria o arquivo se não existir
        }
    }

    /**
     * Salva a lista de gastos no arquivo, sobrescrevendo o conteúdo anterior.
     * Utiliza BufferedWriter para escrita eficiente e segura.
     * 
     * @param gastos Lista de objetos Gasto a serem salvos
     * @throws IOException Caso ocorra erro na escrita do arquivo
     */
    public static void salvar(List<Gasto> gastos) throws IOException {
        garantirArquivo();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Gasto gasto : gastos) {
                writer.write(gasto.toFileString()); // Converte o gasto para formato texto
                writer.newLine();
            }
        }
    }

    /**
     * Carrega a lista de gastos do arquivo, reconstruindo os objetos Gasto a partir das linhas lidas.
     * Recebe a lista de categorias para associar corretamente cada gasto à sua categoria.
     * 
     * Trata erros de parsing e associações inválidas, reportando avisos sem interromper a carga.
     * 
     * @param categorias Lista de categorias existentes para associação
     * @return Lista de objetos Gasto carregados do arquivo
     * @throws IOException Caso ocorra erro na leitura do arquivo
     * @throws CampoVazioException Caso algum campo obrigatório esteja vazio durante a criação dos objetos
     */
    public static List<Gasto> carregar(List<Categoria> categorias) throws IOException, CampoVazioException {
        garantirArquivo();
        List<Gasto> gastos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue; // Ignora linhas vazias
                try {
                    String[] partes = linha.split(";");
                    if (partes.length >= 5) {
                        int id = Integer.parseInt(partes[0].trim());
                        String nome = partes[1].trim();
                        double valor = Double.parseDouble(partes[2].trim());
                        LocalDate dataCriacao = LocalDate.parse(partes[3].trim(), DATE_FORMATTER);
                        int idCategoria = Integer.parseInt(partes[4].trim());

                        // Busca a categoria correspondente pelo ID para manter integridade referencial
                        Categoria categoria = categorias.stream()
                            .filter(c -> c.getId() == idCategoria)
                            .findFirst()
                            .orElse(null);

                        if (categoria != null) {
                            // Cria o objeto Gasto com os dados lidos e associa a categoria correta
                            Gasto gasto = new Gasto(id, nome, valor, categoria, dataCriacao);
                            gastos.add(gasto);
                        } else {
                            // Caso a categoria não seja encontrada, exibe aviso e ignora o gasto
                            System.err.println("Aviso: Categoria com ID " + idCategoria + " não encontrada para o gasto '" + nome + "'.");
                        }
                    }
                } catch (Exception e) {
                    // Captura erros de parsing e exibe mensagem para facilitar depuração
                    System.err.println("Erro ao carregar linha do arquivo: " + linha + " - " + e.getMessage());
                }
            }
        }
        return gastos;
    }
}