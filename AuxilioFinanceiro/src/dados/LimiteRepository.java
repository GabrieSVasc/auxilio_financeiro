package dados;

import java.util.ArrayList;
import java.util.List;

import negocio.CategoriaManager;
import negocio.entidades.Categoria;
import negocio.entidades.Limite;
import negocio.exceptions.ValorNegativoException;
import util.arquivoUtils;

/**
 * Classe que lida com o CRUD de limite
 */

public class LimiteRepository {
	private List<Limite> limites;

	/**
	 * Construtor que inicializa os limites
	 * 
	 * @param categoriaManager O manager de categorias
	 */
	public LimiteRepository(CategoriaManager cm) {
		limites = new ArrayList<Limite>();
		limites = carregarLimites(cm);
	}

	/**
	 * Método que verifica se a lista de limites está vazia
	 * 
	 * @return true se a lista estiver vazia false se não estiver
	 */
	public boolean isEmpty() {
		return limites.isEmpty();
	}

	/**
	 * Método que cria uma listagem para console de todos os limites
	 * 
	 * @return String com a lista
	 */
	public String listar() {
		String retorno = "";
		for (Limite l : limites) {
			retorno = retorno + l.exibir() + "\n";
		}
		return retorno;
	}

	/**
	 * Método que cria um limite novo e salva no arquivo
	 * 
	 * @param l O limite a ser adicionado
	 */
	public void criar(Limite l) {
		limites.add(l);
		salvarTodos();
	}

	/**
	 * Método que remove um limite e salva o arquivo sem ele
	 * 
	 * @param l O limite a ser removido
	 */
	public void remover(Limite l) {
		int indice = limites.indexOf(l);
		if (indice != -1) {
			limites.remove(indice);
			salvarTodos();
		}
	}

	/**
	 * Método que busca por um limite especificado pelo id
	 * 
	 * @param id Id do limite buscado
	 * @return O limite encontrado ou null se não for encontrado
	 */
	public Limite consultar(int id) {
		return limites.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
	}

	/**
	 * Método que retorna todos os limites
	 * 
	 * @return Lista com todos os limites
	 */
	public List<Limite> getLimites() {
		return limites;
	}

	/**
	 * Método que atualiza um limite
	 * 
	 * @param l         O limite a ser atualizado
	 * @param novoValor Novo valor do limite
	 * @throws ValorNegativoException
	 */
	public void atualizar(Limite l, double novoValor) throws ValorNegativoException {
		int indice = limites.indexOf(l);
		if (indice != -1) {
			limites.get(indice).setValor(novoValor);
			salvarTodos();
		}
	}

	/**
	 * Método que verifica se já existe um limite associado à categoria
	 * 
	 * @param cat Nome da categoria
	 * @return true se existir, false se não
	 */
	public boolean existe(String cat) {
		boolean resultado = false;
		for (Limite l : limites) {
			if (l.getCategoria().getNome().equals(cat)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}

	// Recarrega limites do arquivo
	private List<Limite> carregarLimites(CategoriaManager cm) {
		List<Categoria> categorias = cm.getCategorias();
		List<Limite> limites = new ArrayList<>();
		List<String> linhas = arquivoUtils.lerDoArquivo("limites.txt");

		for (String linha : linhas) {
			try {
				String[] partes = linha.split(";");
				String nomeCategoria = partes[0];
				double valor = Double.parseDouble(partes[1]);

				Categoria cat = categorias.stream().filter(c -> c.getNome().equals(nomeCategoria)).findFirst()
						.orElse(null);

				if (cat != null) {
					limites.add(new Limite(cat, valor));
				}
			} catch (Exception e) {
				System.out.println("Erro ao carregar limite: " + e.getMessage());
			}
		}
		return limites;
	}

	// Para salvar toda a lista ao editar/deletar
	private void salvarTodos() {
		List<String> linhas = new ArrayList<>();
		for (Limite l : limites) {
			linhas.add(l.getCategoria().getNome() + ";" + l.getValorLimite());
		}
		arquivoUtils.salvarListaEmArquivo("limites.txt", linhas);
	}
}