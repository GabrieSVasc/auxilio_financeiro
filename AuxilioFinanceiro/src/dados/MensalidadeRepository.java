package dados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import negocio.CategoriaManager;
import negocio.entidades.Categoria;
import negocio.entidades.Mensalidade;
import negocio.exceptions.CampoVazioException;
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
	private List<Mensalidade> mensalidades;
    // Nome do arquivo onde as mensalidades são armazenadas
    private static final String ARQUIVO_MENSALIDADES = "mensalidades.txt";
    // Formato padrão para leitura e escrita das datas
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public MensalidadeRepository(CategoriaManager cm) {
    	mensalidades = new ArrayList<Mensalidade>();
    	mensalidades = carregar(cm);
    }
    
    public List<Mensalidade> getMensalidades(){
    	return mensalidades;
    }
    
    public void criar (Mensalidade m) {
    	mensalidades.add(m);
    	salvar();
    }
    
    public void remover(Mensalidade m) {
    	int indice = mensalidades.indexOf(m);
    	if(indice!=-1) {
    		mensalidades.remove(m);
    		salvar();
    	}
    }
    
    public void atualizar(Mensalidade m, String novoNome, double novoValor, LocalDate novaDataVencimento, String novaRecorrencia, boolean novoStatusPago) throws CampoVazioException {
    	int indice = mensalidades.indexOf(m);
    	if(indice!=-1) {
    		mensalidades.get(indice).setNome(novoNome);
    		mensalidades.get(indice).setValor(novoValor);
    		mensalidades.get(indice).setDataVencimento(novaDataVencimento);
    		mensalidades.get(indice).setRecorrencia(novaRecorrencia);
    		mensalidades.get(indice).setPago(novoStatusPago);
    		salvar();
    	}
    }
    
    public Mensalidade consultar(int id) {
		return mensalidades.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
	}
    
    /**
     * Salva a lista de mensalidades no arquivo.
     * Converte cada objeto Mensalidade para uma string formatada e delega a escrita para utilitário.
     */
    private void salvar(){
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
     * @param categoriasManager Manager de categorias para associação
     * @return Lista de objetos Mensalidade carregados do arquivo
     */
    private List<Mensalidade> carregar(CategoriaManager cm){
    	List<Categoria> categorias = cm.getCategorias();
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