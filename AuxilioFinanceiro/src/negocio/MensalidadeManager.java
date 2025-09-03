package negocio;

import java.time.LocalDate;
import java.util.List;

import dados.MensalidadeRepository;
import negocio.entidades.Mensalidade;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;

/**
 * Classe responsável por gerenciar as mensalidades do sistema.
 * 
 * Aplica os conceitos de Orientação a Objetos para encapsular a lista de
 * mensalidades, garantindo controle sobre as operações de adicionar, editar,
 * remover e listar mensalidades.
 * 
 * Utiliza composição ao depender do CategoriaManager para garantir a
 * integridade das categorias associadas.
 * 
 * @author Halina Mochel
 */
public class MensalidadeManager {
	private MensalidadeRepository mensalidadeRepository;

	/**
	 * Construtor que recebe o gerenciador de categorias e carrega as mensalidades
	 * persistidas. Demonstra o princípio da responsabilidade única e a reutilização
	 * de componentes.
	 * 
	 * @param categoriaManager Gerenciador de categorias para associação
	 */
	public MensalidadeManager(CategoriaManager categoriaManager) {
		mensalidadeRepository = new MensalidadeRepository(categoriaManager);
	}

	/**
	 * Adiciona uma nova mensalidade à lista e persiste a alteração. Aplica
	 * validação para evitar inserção de objetos nulos.
	 * 
	 * @param mensalidade Objeto mensalidade a ser adicionado
	 */
	public void adicionarMensalidade(Mensalidade mensalidade) {
		mensalidadeRepository.criar(mensalidade);
	}

	/**
	 * Edita uma mensalidade existente identificada pelo id, atualizando os campos
	 * fornecidos. Demonstra encapsulamento e controle de acesso aos dados internos.
	 * 
	 * Nota: A categoria não pode ser alterada pois é final na classe Gasto.
	 * 
	 * @param id                 Identificador da mensalidade a ser editada
	 * @param novoNome           Novo nome para a mensalidade (pode ser null para
	 *                           não alterar)
	 * @param novoValor          Novo valor para a mensalidade (pode ser null para
	 *                           não alterar)
	 * @param novaDataVencimento Nova data de vencimento (pode ser null para não
	 *                           alterar)
	 * @param novaRecorrencia    Nova recorrência (pode ser null para não alterar)
	 * @param novoStatusPago     Novo status de pagamento (pode ser null para não
	 *                           alterar)
	 * @throws ObjetoNaoEncontradoException Caso a mensalidade não seja encontrada
	 * @throws CampoVazioException 
	 */
	public void editarMensalidade(int id, String novoNome, double novoValor, LocalDate novaDataVencimento, String novaRecorrencia, boolean novoStatusPago)
			throws ObjetoNaoEncontradoException, CampoVazioException {
		Mensalidade mensalidade = mensalidadeRepository.consultar(id);
		if (mensalidade == null) {
			throw new ObjetoNaoEncontradoException("Mensalidade", id);
		}
		mensalidadeRepository.atualizar(mensalidade, novoNome, novoValor, novaDataVencimento, novaRecorrencia, novoStatusPago);
	}

	/**
	 * Remove uma mensalidade pelo seu identificador e persiste a alteração.
	 * Demonstra encapsulamento e controle do ciclo de vida dos objetos.
	 * 
	 * @param id Identificador da mensalidade a ser removida
	 * @throws ObjetoNaoEncontradoException Caso a mensalidade não seja encontrada
	 */
	public void removerMensalidade(int id) throws ObjetoNaoEncontradoException {
		Mensalidade encontrada = mensalidadeRepository.consultar(id);
		if (encontrada == null) {
			throw new ObjetoNaoEncontradoException("Mensalidade", id);
		}
		mensalidadeRepository.remover(encontrada);
	}

	/**
	 * Retorna uma cópia da lista de mensalidades para evitar exposição direta da
	 * lista interna. Demonstra encapsulamento e proteção dos dados internos.
	 * 
	 * @return Lista de mensalidades
	 */
	public List<Mensalidade> listarMensalidades() {
		return mensalidadeRepository.getMensalidades();
	}

	/**
	 * Busca uma mensalidade pelo seu identificador. Demonstra encapsulamento e uso
	 * de streams para busca eficiente.
	 * 
	 * @param id Identificador da mensalidade
	 * @return Objeto mensalidade encontrado ou null se não existir
	 */
	public Mensalidade buscarPorId(int id) {
		return listarMensalidades().stream().filter(m -> m.getId() == id).findFirst().orElse(null);
	}
}