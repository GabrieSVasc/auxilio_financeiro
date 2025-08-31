package dados;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

/**
 * Classe responsável por gerenciar os gastos do sistema.
 * Utiliza conceitos de Orientação a Objetos para encapsular a lista de gastos
 * e delegar operações relacionadas a eles, como adicionar, editar, remover e listar.
 * 
 * O uso da composição com CategoriaManager permite a integração e validação das categorias associadas.
 * 
 * @author Halina Mochel
 */
public class GastoManager {
	// Lista interna que armazena os gastos em memória
	private final List<Gasto> gastos = new ArrayList<>();
	// Referência para o gerenciador de categorias, para garantir integridade dos dados
	private final CategoriaManager categoriaManager;

	/**
	 * Construtor que recebe o gerenciador de categorias e carrega os gastos persistidos.
	 * Demonstra o princípio da responsabilidade única e a reutilização de componentes.
	 */
	public GastoManager(CategoriaManager categoriaManager) {
		this.categoriaManager = categoriaManager;
		carregarGastos();
	}

	/**
	 * Método privado que carrega os gastos do repositório (arquivo).
	 * Trata exceções para garantir que falhas na carga não quebrem o sistema.
	 */
	private void carregarGastos() {
		try {
			// Carrega gastos do arquivo, associando categorias existentes
			gastos.addAll(GastoRepository.carregar(categoriaManager.getCategorias()));
		} catch (IOException | CampoVazioException e) {
			System.err.println("Erro ao carregar gastos: " + e.getMessage());
		}
	}

	/**
	 * Adiciona um novo gasto à lista e persiste a alteração.
	 * @param gasto Objeto gasto a ser adicionado
	 * @throws IOException Caso ocorra erro na persistência
	 */
	public void adicionarGasto(Gasto gasto) throws IOException {
		gastos.add(gasto);
		GastoRepository.salvar(gastos);
	}

	/**
	 * Edita um gasto existente identificado pelo id, atualizando os campos fornecidos.
	 * Demonstra encapsulamento e controle de acesso aos dados internos.
	 * @param id Identificador do gasto a ser editado
	 * @param novoNome Novo nome para o gasto (pode ser null para não alterar)
	 * @param novoValor Novo valor para o gasto (pode ser null para não alterar)
	 * @param novaData Nova data para o gasto (pode ser null para não alterar)
	 * @return true se o gasto foi encontrado e editado, false caso contrário
	 * @throws IOException Caso ocorra erro na persistência
	 * @throws CampoVazioException Caso algum campo obrigatório esteja vazio
	 */
	public boolean editarGasto(int id, String novoNome, Double novoValor, LocalDate novaData)
			throws IOException, CampoVazioException {
		Gasto gasto = buscarPorId(id);
		if (gasto == null) {
			return false;
		}
		if (novoNome != null) {
			gasto.setNome(novoNome);
		}
		if (novoValor != null && novoValor > 0) {
			gasto.setValor(novoValor);
		}
		if (novaData != null) {
			gasto.setDataCriacao(novaData);
		}

		// Persiste as alterações após edição
		GastoRepository.salvar(gastos);
		return true;
	}

	/**
	 * Retorna uma cópia da lista de gastos para evitar exposição direta da lista interna.
	 * Demonstra encapsulamento e proteção dos dados internos.
	 * @return Lista de gastos
	 */
	public List<Gasto> listarGastos() {
		return new ArrayList<>(gastos);
	}

	/**
	 * Filtra e retorna os gastos que pertencem a uma categoria específica.
	 * Utiliza programação funcional com streams para maior clareza e concisão.
	 * @param categoriaId Identificador da categoria
	 * @return Lista de gastos filtrados pela categoria
	 */
	public List<Gasto> listarGastosPorCategoria(int categoriaId) {
		return gastos.stream()
				.filter(g -> g.getCategoria().getId() == categoriaId)
				.collect(Collectors.toList());
	}

	/**
	 * Remove um gasto pelo seu identificador e persiste a alteração.
	 * Demonstra encapsulamento e controle do ciclo de vida dos objetos.
	 * @param id Identificador do gasto a ser removido
	 * @return true se o gasto foi removido, false caso contrário
	 * @throws IOException Caso ocorra erro na persistência
	 */
	public boolean removerGasto(int id) throws IOException {
		boolean removido = gastos.removeIf(gasto -> gasto.getId() == id);
		if (removido) {
			GastoRepository.salvar(gastos);
		}
		return removido;
	}

	/**
	 * Busca um gasto pelo seu identificador.
	 * Demonstra encapsulamento e uso de streams para busca eficiente.
	 * @param id Identificador do gasto
	 * @return Objeto gasto encontrado ou null se não existir
	 */
	private Gasto buscarPorId(int id) {
		return gastos.stream()
				.filter(gasto -> gasto.getId() == id)
				.findFirst()
				.orElse(null);
	}

	/**
	 * Retorna os gastos de um mês e ano específicos.
	 * Demonstra reutilização do repositório para garantir dados atualizados.
	 * @param m Mês (1-12)
	 * @param a Ano
	 * @return Lista de gastos do mês e ano especificados
	 * @throws IOException Caso ocorra erro na leitura dos dados
	 * @throws CampoVazioException Caso algum campo obrigatório esteja vazio
	 */
	public ArrayList<Gasto> getGastoByMes(int m, int a) throws IOException, CampoVazioException {
		List<Gasto> gastos = GastoRepository.carregar(categoriaManager.getCategorias());
		ArrayList<Gasto> gastosMes = new ArrayList<>();
		for (Gasto g : gastos) {
			if (g.getDataCriacao().getMonthValue() == m && g.getDataCriacao().getYear() == a) {
				gastosMes.add(g);
			}
		}
		return gastosMes;
	}

	/**
	 * Retorna os gastos de uma categoria específica em um mês e ano determinados.
	 * Aplica validações para garantir que existam gastos para a categoria e mês informados,
	 * lançando exceções específicas para tratamento adequado.
	 * Demonstra encapsulamento, tratamento de exceções e uso de coleções.
	 * @param c Categoria a ser filtrada
	 * @param m Mês (1-12)
	 * @param a Ano
	 * @return Lista de gastos filtrados
	 * @throws IOException Caso ocorra erro na leitura dos dados
	 * @throws MesSemGastosException Caso não existam gastos no mês informado
	 * @throws CategoriaSemGastosException Caso a categoria não possua gastos
	 * @throws CampoVazioException Caso algum campo obrigatório esteja vazio
	 */
	public ArrayList<Gasto> getGastosByCategoria(Categoria c, int m, int a)
			throws IOException, MesSemGastosException, CategoriaSemGastosException, CampoVazioException {
		List<Gasto> gastos = this.getGastoByMes(m, a);
		if (!categoriaTemGastos(c)) {
			throw new CategoriaSemGastosException();
		}
		if (gastos.size() == 0) {
			throw new MesSemGastosException();
		}
		ArrayList<Gasto> gastosCategoria = new ArrayList<>();
		for (Gasto g : gastos) {
			if (g.getCategoria().getNome().equals(c.getNome()) && g.getDataCriacao().getMonthValue() == m
					&& g.getDataCriacao().getYear() == a) {
				gastosCategoria.add(g);
			}
		}
		return gastosCategoria;
	}

	/**
	 * Verifica se uma categoria possui gastos associados.
	 * Demonstra encapsulamento e uso de laços para busca.
	 * @param c Categoria a ser verificada
	 * @return true se a categoria possui gastos, false caso contrário
	 * @throws IOException Caso ocorra erro na leitura dos dados
	 * @throws CampoVazioException Caso algum campo obrigatório esteja vazio
	 */
	public boolean categoriaTemGastos(Categoria c) throws IOException, CampoVazioException {
		List<Gasto> gastos = GastoRepository.carregar(categoriaManager.getCategorias());
		for (Gasto g : gastos) {
			if (g.getCategoria().getNome().equals(c.getNome())) {
				return true;
			}
		}
		return false;
	}

}