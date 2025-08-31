package dados;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import negocio.entidades.Categoria;
import negocio.entidades.Mensalidade;
import util.arquivoUtils;

/**
 * Classe de repositório responsável pela persistência dos objetos Mensalidade.
 * 
 * Essa classe abstrai o acesso ao sistema de arquivos, garantindo que a leitura e escrita
 * dos dados sejam feitas de forma consistente e segura.
 * 
 * Utiliza o padrão Repository para centralizar a lógica de persistência,
 * facilitando manutenção e reutilização.
 * 
 * @author Halina Mochel
 */
public class MensalidadeRepository {

    // Nome do arquivo onde as mensalidades são armazenadas
    private static final String ARQUIVO_MENSALIDADES = "mensalidades.txt";
    // Formato padrão para leitura e escrita das datas
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Salva a lista de mensalidades no arquivo.
     * Converte cada objeto Mensalidade para uma string formatada e delega a escrita para utilitário.
     * 
     * @param mensalidades Lista de objetos Mensalidade a serem salvos
     * @throws IOException Caso ocorra erro na escrita do arquivo
     */
    public static void salvar(List<Mensalidade> mensalidades) throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Mensalidade m : mensalidades) {
            linhas.add(m.toFileString()); // Converte o objeto para formato texto
        }
        arquivoUtils.salvarListaEmArquivo(ARQUIVO_MENSALIDADES, linhas);
    }

    /**
     * Carrega a lista de mensalidades do arquivo, reconstruindo os objetos a partir das linhas lidas.
     * Recebe a lista de categorias para associar corretamente cada mensalidade à sua categoria.
     * 
     * Trata erros de parsing e associações inválidas, reportando avisos sem interromper a carga.
     * 
     * @param categorias Lista de categorias existentes para associação
     * @return Lista de objetos Mensalidade carregados do arquivo
     * @throws IOException Caso ocorra erro na leitura do arquivo
     */
    public static List<Mensalidade> carregar(List<Categoria> categorias) throws IOException {
        List<Mensalidade> mensalidades = new ArrayList<>();
        List<String> linhas = arquivoUtils.lerDoArquivo(ARQUIVO_MENSALIDADES);

        if (linhas.isEmpty()) {
            return mensalidades; // Retorna lista vazia se arquivo estiver vazio
        }

        for (String linha : linhas) {
            try {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                double valor = Double.parseDouble(partes[2]);
                LocalDate dataCriacao = LocalDate.parse(partes[3], DATE_FORMATTER);
                int idCategoria = Integer.parseInt(partes[4]);
                LocalDate dataVencimento = LocalDate.parse(partes[5], DATE_FORMATTER);
                String recorrencia = partes[6];
                boolean isPago = Boolean.parseBoolean(partes[7]);

                // Busca a categoria correspondente pelo ID para manter integridade referencial
                Categoria categoria = categorias.stream()
                    .filter(c -> c.getId() == idCategoria)
                    .findFirst()
                    .orElse(null);

                if (categoria != null) {
                    // Cria o objeto Mensalidade com os dados lidos e associa a categoria correta
                    mensalidades.add(new Mensalidade(id, nome, valor, dataCriacao, categoria, dataVencimento, recorrencia, isPago));
                } else {
                    // Caso a categoria não seja encontrada, exibe aviso e ignora a mensalidade
                    System.err.println("Aviso: Categoria com ID " + idCategoria + " não encontrada para a mensalidade '" + nome + "'.");
                }
            } catch (Exception e) {
                // Captura erros de parsing e exibe mensagem para facilitar depuração
                System.err.println("Erro ao carregar mensalidade da linha: " + linha + " - Erro: " + e.getMessage());
            }
        }
        return mensalidades;
    }
}