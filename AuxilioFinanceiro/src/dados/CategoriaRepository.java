package dados;

import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Categoria;
import negocio.exceptions.CampoVazioException;
import util.arquivoUtils;

/**
 * Classe que lida com o CRUD de categorias
 * 
 * @author Pedro Farias
 */

public class CategoriaRepository {
	private List<Categoria> categorias;

	/**
	 * Construtor que inicializa a lista de categorias
	 */
	public CategoriaRepository() {
		categorias = new ArrayList<Categoria>();
		categorias = carregarCategorias();
	}

	/**
	 * Método que verifica se a lista de categorias está vazia
	 * 
	 * @return true se a lista estiver vazia false se não estiver
	 */
	public boolean isEmpty() {
		return categorias.isEmpty();
	}

	/**
	 * Método que cria uma listagem para console de todas as categorias
	 * 
	 * @return String com a lista
	 */
	public String listar() {
		String retorno = "";
		for (Categoria c : categorias) {
			retorno = retorno + c.exibir() + "\n";
		}
		return retorno;
	}

	/**
	 * Método que cria uma categoria nova e salva no arquivo
	 * 
	 * @param c A categoria a ser adicionada
	 */
	public void criar(Categoria c) {
		categorias.add(c);
		salvarTodas();
	}

	/**
	 * Método que remove uma categoria e salva o arquivo sem ela
	 * 
	 * @param c A categoria a ser removida
	 */
	public void remover(Categoria c) {
		int indice = categorias.indexOf(c);
		if (indice != -1) {
			categorias.remove(c);
			salvarTodas();
		}
	}

	/**
	 * Método que busca por uma categoria especificada pelo id
	 * 
	 * @param id Id da categoria buscada
	 * @return A categoria encontrada ou null se não for encontrada
	 */
	public Categoria consultar(int id) {
		return categorias.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
	}

	/**
	 * Método que retorna todas as categorias
	 * 
	 * @return Lista com todas as categorias
	 */
	public List<Categoria> getCategorias() {
		return categorias;
	}

	/**
	 * Método que atualiza uma categoria
	 * 
	 * @param c        A categoria a ser atualizada
	 * @param novoNome O novo nome da categoria
	 * @throws CampoVazioException
	 */
	public void atualizar(Categoria c, String novoNome) throws CampoVazioException {
		int indice = categorias.indexOf(c);
		if (indice != -1) {
			categorias.get(indice).setNome(novoNome);
			salvarTodas();
		}
	}

	/**
	 * Método que verifica se já existe uma categoria com o nome
	 * 
	 * @param nome Nome a ser buscado
	 * @return true se existir false se não existir
	 */
	public boolean existe(String nome) {
		boolean resultado = false;
		for (Categoria c : categorias) {
			if (c.getNome().equals(nome)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}

	/**
	 * Método que carrega as categorias do arquivo
	 * 
	 * @return A lista com as categorias carregadas
	 */
	private List<Categoria> carregarCategorias() {
		List<String> linhas = arquivoUtils.lerDoArquivo("categorias.txt");

		if (linhas.isEmpty()) {
			// Criar categorias padrão
			String[] padrao = { "Comida", "Transporte", "Lazer", "Aluguel", "Saúde", "Educação", "Mensal" };
			for (String nome : padrao) {
				try {
					categorias.add(new Categoria(nome));
				} catch (CampoVazioException e) {
					System.out.println("Erro ao criar categoria padrão: " + e.getMessage());
				}
			}
			salvarTodas();
			return categorias;
		}

		for (String linha : linhas) {
			try {
				String[] partes = linha.split(";");
				int id = Integer.parseInt(partes[0]);
				String nome = partes[1];
				categorias.add(new Categoria(id, nome));
			} catch (Exception e) {
				System.out.println("Erro ao carregar categoria: " + e.getMessage());
			}
		}
		return categorias;
	}

	/**
	 * Método que salva todas as categorias cadastradas
	 */
	private void salvarTodas() {
		List<String> linhas = new ArrayList<>();
		for (Categoria c : categorias) {
			linhas.add(c.getId() + ";" + c.getNome());
		}
		arquivoUtils.salvarListaEmArquivo("categorias.txt", linhas);
	}
}