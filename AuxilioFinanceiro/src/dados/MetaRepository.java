package dados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Meta;
import util.arquivoUtils;

/**
 * Classe que lida com o armazenamento de metas
 */

public class MetaRepository {
	private List<Meta> metas;
	private static final String ARQUIVO = "metas.txt";

	/**
	 * Construtor que inicializa as metas
	 */
	public MetaRepository() {
		metas = new ArrayList<Meta>();
		metas = carregarMetas();
	}

	/**
	 * Método que retorna se há metas cadastradas
	 * 
	 * @return true se houverem, false se não
	 */
	public boolean isEmpty() {
		return metas.isEmpty();
	}

	/**
	 * Método que faz listagem para console das metas cadastradas
	 * 
	 * @return String com a lista
	 */
	public String listar() {
		String retorno = "";
		for (Meta m : metas) {
			retorno = retorno + m.toString() + "\n";
		}
		return retorno;
	}

	/**
	 * Método que adiciona uma meta e salva no arquivo
	 * 
	 * @param m Meta a ser adicionada
	 */
	public void criar(Meta m) {
		metas.add(m);
		salvarLista();
	}

	/**
	 * Método que remove uma meta e salva o arquivo sem ela
	 * 
	 * @param m Meta a ser removida
	 */
	public void remover(Meta m) {
		int indice = metas.indexOf(m);
		if (indice != -1) {
			metas.remove(m);
			salvarLista();
		}
	}

	/**
	 * Método que consulta uma meta
	 * 
	 * @param id Id da meta
	 * @return A meta desejada ou null se ela não estiver cadastrada
	 */
	public Meta consultar(int id) {
		return metas.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
	}

	/**
	 * Método que retorna todas as metas
	 * 
	 * @return todas as metas cadastradas
	 */
	public List<Meta> getMetas() {
		return metas;
	}

	/**
	 * Método que atualiza uma meta e salva o arquivo com ela
	 * 
	 * @param m         Meta a ser atualizada
	 * @param descricao Nova descrição
	 * @param objetivo  Novo objetivo
	 * @param atual     Novo valor atual
	 * @param data      Nova data
	 */
	public void atualizar(Meta m, String descricao, double objetivo, double atual, LocalDate data) {
		int indice = metas.indexOf(m);
		if (indice != -1) {
			metas.get(indice).setDescricao(descricao);
			metas.get(indice).setValorObjetivo(objetivo);
			metas.get(indice).setValorAtual(atual);
			metas.get(indice).setDataPrazo(data);
			salvarLista();
		}
	}

	/**
	 * Método que checa se existe uma meta com o id informado
	 * @param id Id a ser checado
	 * @return true se existir, false se não
	 */
	public boolean existe(int id) {
		boolean resultado = false;
		for (Meta m : metas) {
			if (m.getId() == id) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}

	/** Salva todas as metas no arquivo */
	private void salvarLista() {
		if (metas.isEmpty()) {
			arquivoUtils.salvarEmArquivo(ARQUIVO, "", false);
			return;
		}
		arquivoUtils.salvarEmArquivo(ARQUIVO, metas.get(0).toArquivo(), false);
		for (int i = 1; i < metas.size(); i++) {
			arquivoUtils.salvarEmArquivo(ARQUIVO, metas.get(i).toArquivo(), true);
		}
	}

	/** Carrega todas as metas do arquivo */
	private List<Meta> carregarMetas() {
		List<String> linhas = arquivoUtils.lerDoArquivo(ARQUIVO);
		for (String ln : linhas) {
			try {
				metas.add(Meta.fromArquivo(ln));
			} catch (Exception e) {
				System.out.println("Falha ao carregar meta: " + e.getMessage());
			}
		}
		return metas;
	}
}