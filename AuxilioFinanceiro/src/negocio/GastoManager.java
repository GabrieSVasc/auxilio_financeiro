package negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dados.GastoRepository;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;
import negocio.exceptions.ObjetoNaoEncontradoException;

/**
 * Classe responsável por gerenciar os gastos do sistema. Utiliza conceitos de
 * Orientação a Objetos para encapsular a lista de gastos e delegar operações
 * relacionadas a eles, como adicionar, editar, remover e listar.
 * 
 * O uso da composição com CategoriaManager permite a integração e validação das
 * categorias associadas.
 * 
 * @author Halina Mochel
 */
public class GastoManager {
	// Repositorio que lida com o armazenamento de gastos
	private GastoRepository gastoRepository;

	/**
	 * Construtor que recebe o gerenciador de categorias e carrega os gastos
	 * persistidos. Demonstra o princípio da responsabilidade única e a reutilização
	 * de componentes.
	 * 
	 * @param categoriaManager	O manager de categorias
	 */
	public GastoManager(CategoriaManager cm) {
		gastoRepository = new GastoRepository(cm);
	}

	/**
	 * Adiciona um novo gasto à lista e persiste a alteração.
	 * 
	 * @param gasto Objeto gasto a ser adicionado
	 */
	public void adicionarGasto(Gasto gasto) {
		gastoRepository.criar(gasto);
	}

	/**
	 * Edita um gasto existente identificado pelo id, atualizando os campos
	 * fornecidos. Demonstra encapsulamento e controle de acesso aos dados internos.
	 * 
	 * @param id        Identificador do gasto a ser editado
	 * @param novoNome  Novo nome para o gasto (pode ser null para não alterar)
	 * @param novoValor Novo valor para o gasto (pode ser null para não alterar)
	 * @param novaData  Nova data para o gasto (pode ser null para não alterar)
	 * @throws ObjetoNaoEncontradoException
	 * @throws CampoVazioException 
	 */
	public void editarGasto(int id, String novoNome, double novoValor, LocalDate novaData) throws ObjetoNaoEncontradoException, CampoVazioException {
		Gasto gasto = gastoRepository.consultar(id);
		if (gasto == null) {
			throw new ObjetoNaoEncontradoException("Gasto", id);
		}
		gastoRepository.atualizar(gasto, novoNome, novoValor, novaData);
	}

	/**
	 * Retorna uma cópia da lista de gastos para evitar exposição direta da lista
	 * interna. Demonstra encapsulamento e proteção dos dados internos.
	 * 
	 * @return Lista de gastos
	 */
	public List<Gasto> listarGastos() {
		return gastoRepository.getGastos();
	}

	/**
	 * Filtra e retorna os gastos que pertencem a uma categoria específica. Utiliza
	 * programação funcional com streams para maior clareza e concisão.
	 * 
	 * @param categoriaId Identificador da categoria
	 * @return Lista de gastos filtrados pela categoria
	 */
	public List<Gasto> listarGastosPorCategoria(int categoriaId) {
		return gastoRepository.getGastos().stream().filter(g -> g.getCategoria().getId() == categoriaId)
				.collect(Collectors.toList());
	}

	/**
	 * Remove um gasto pelo seu identificador e persiste a alteração. Demonstra
	 * encapsulamento e controle do ciclo de vida dos objetos.
	 * 
	 * @param id Identificador do gasto a ser removido
	 * @throws ObjetoNaoEncontradoException
	 */
	public void removerGasto(int id) throws ObjetoNaoEncontradoException {
		Gasto encontrado = gastoRepository.consultar(id);
		if (encontrado == null) {
			throw new ObjetoNaoEncontradoException("Gasto", id);
		}
		gastoRepository.remover(encontrado);
	}

	/**
	 * Retorna os gastos de um mês e ano específicos. Demonstra reutilização do
	 * repositório para garantir dados atualizados.
	 * 
	 * @param m Mês (1-12)
	 * @param a Ano
	 * @return Lista de gastos do mês e ano especificados
	 */
	public ArrayList<Gasto> getGastoByMes(int m, int a) {
		List<Gasto> gastos = gastoRepository.getGastos();
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
	 * Aplica validações para garantir que existam gastos para a categoria e mês
	 * informados, lançando exceções específicas para tratamento adequado. Demonstra
	 * encapsulamento, tratamento de exceções e uso de coleções.
	 * 
	 * @param c Categoria a ser filtrada
	 * @param m Mês (1-12)
	 * @param a Ano
	 * @return Lista de gastos filtrados
	 * @throws MesSemGastosException       Caso não existam gastos no mês informado
	 * @throws CategoriaSemGastosException Caso a categoria não possua gastos
	 */
	public ArrayList<Gasto> getGastosByCategoria(Categoria c, int m, int a)
			throws MesSemGastosException, CategoriaSemGastosException {
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
	 * Verifica se uma categoria possui gastos associados. Demonstra encapsulamento
	 * e uso de laços para busca.
	 * 
	 * @param c Categoria a ser verificada
	 * @return true se a categoria possui gastos, false caso contrário
	 */
	public boolean categoriaTemGastos(Categoria c) {
		List<Gasto> gastos = gastoRepository.getGastos();
		for (Gasto g : gastos) {
			if (g.getCategoria().getNome().equals(c.getNome())) {
				return true;
			}
		}
		return false;
	}

}