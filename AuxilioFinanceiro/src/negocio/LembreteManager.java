package negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dados.LembreteRepository;
import negocio.entidades.Lembrete;
import negocio.entidades.LembreteLimite;
import negocio.entidades.MensalidadeLembrete;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;

/**
 * Classe responsável por gerenciar os lembretes do sistema.
 * 
 * Utiliza conceitos de Orientação a Objetos para encapsular a lista de
 * lembretes, além de gerenciar a integração com mensalidades e limites através
 * dos managers correspondentes.
 * 
 * A classe demonstra o uso de composição para resolver dependências entre
 * managers, além de aplicar encapsulamento, tratamento de exceções e
 * programação funcional.
 * 
 * @author Halina Mochel
 */
public class LembreteManager {
	LembreteRepository lembreteRepository;

	/**
	 * Construtor que recebe o gerenciador de mensalidades e carrega os lembretes
	 * persistidos. Demonstra a composição e a necessidade de resolver dependências
	 * circulares via setters.
	 * 
	 * @param mensalidadeManager Gerenciador de mensalidades para associação
	 * @param limiteManager      Gerenciador de limites para associação
	 */
	public LembreteManager(MensalidadeManager mensalidadeManager, LimiteManager limiteManager, MetaManager metaManager) {
		lembreteRepository = new LembreteRepository(mensalidadeManager, limiteManager, metaManager);
	}

	/**
	 * Cria um novo lembrete e persiste a alteração. Aplica validação para evitar
	 * inserção de objetos nulos.
	 * 
	 * @param lembrete Objeto lembrete a ser criado
	 * @throws ObjetoNuloException Caso o objeto lembrete seja nulo
	 */
	public void criarLembrete(Lembrete lembrete) throws ObjetoNuloException {
		if (lembrete == null) {
			throw new ObjetoNuloException("Lembrete");
		}
		lembreteRepository.criar(lembrete);
	}

	/**
	 * Atualiza os dados de um lembrete existente identificado pelo id. Aplica
	 * validações para campos não nulos e não vazios.
	 * 
	 * @param id            Identificador do lembrete a ser atualizado
	 * @param novoTitulo    Novo título (pode ser null para não alterar)
	 * @param novaDescricao Nova descrição (pode ser null para não alterar)
	 * @param novaData      Nova data de alerta (pode ser null para não alterar)
	 * @param ativo         Novo estado do lembrete
	 * @throws ObjetoNaoEncontradoException Caso o lembrete não seja encontrado
	 * @throws CampoVazioException          Caso algum campo obrigatório esteja
	 *                                      vazio
	 */
	public void atualizarLembrete(int id, String novoTitulo, String novaDescricao, LocalDate novaData, boolean ativo)
			throws ObjetoNaoEncontradoException, CampoVazioException {
		Lembrete lembrete = lembreteRepository.consultar(id);
		if (lembrete == null) {
			throw new ObjetoNaoEncontradoException("Lembrete", id);
		}
		lembreteRepository.atualizar(lembrete, novoTitulo, novaDescricao, novaData, ativo);
	}

	/**
	 * Retorna os lembretes ativos cujo alerta está para hoje ou dentro dos próximos
	 * 7 dias. Demonstra uso de datas e filtragem para funcionalidades específicas.
	 * 
	 * @return Lista de lembretes para o dia ou próximos dias
	 */
	public ArrayList<Lembrete> getLembreteDia() {
		ArrayList<Lembrete> l = new ArrayList<>();
		LocalDate agora = LocalDate.now();
		for (Lembrete lembrete : listarTodos()) {
			LocalDate dataAlerta = lembrete.getDataAlerta();
			// Verifica se o lembrete está ativo e se a data de alerta está dentro do
			// intervalo desejado
			if (lembrete.isAtivo() && (dataAlerta.isBefore(agora) || dataAlerta.isEqual(agora)
					|| dataAlerta.isBefore(agora.plusDays(7)))) {
				l.add(lembrete);
			}
		}
		return l;
	}

	/**
	 * Remove um lembrete pelo seu identificador. Lança exceção caso o lembrete não
	 * seja encontrado.
	 * 
	 * @param id Identificador do lembrete a ser removido
	 * @throws ObjetoNaoEncontradoException Caso o lembrete não exista
	 */
	public void removerLembrete(int id) throws ObjetoNaoEncontradoException {
		Lembrete encontrado = lembreteRepository.consultar(id);
		if (encontrado == null) {
			throw new ObjetoNaoEncontradoException("Lembrete", id);
		}
		lembreteRepository.remover(encontrado);
	}

	/**
	 * Retorna uma cópia da lista de todos os lembretes. Demonstra encapsulamento
	 * para proteger a lista interna.
	 * 
	 * @return Lista de lembretes
	 */
	public List<Lembrete> listarTodos() {
		return lembreteRepository.getLembretes();
	}

	/**
	 * Retorna a lista de lembretes do tipo MensalidadeLembrete. Demonstra uso de
	 * programação funcional para filtragem e casting.
	 * 
	 * @return Lista de lembretes relacionados a mensalidades
	 */
	public List<MensalidadeLembrete> listarLembretesMensalidade() {
		return listarTodos().stream().filter(l -> l instanceof MensalidadeLembrete).map(l -> (MensalidadeLembrete) l)
				.collect(Collectors.toList());
	}

	/**
	 * Retorna a lista de lembretes do tipo LembreteLimite. Demonstra uso de
	 * programação funcional para filtragem e casting.
	 * 
	 * @return Lista de lembretes relacionados a limites
	 */
	public List<LembreteLimite> listarLembretesLimite() {
		return listarTodos().stream().filter(l -> l instanceof LembreteLimite).map(l -> (LembreteLimite) l)
				.collect(Collectors.toList());
	}

	/**
	 * Busca um lembrete pelo seu identificador. Demonstra encapsulamento.
	 * 
	 * @param id Identificador do lembrete
	 * @return Objeto lembrete encontrado ou null se não existir
	 */
	public Lembrete buscarPorId(int id) {
		return lembreteRepository.consultar(id);
	}
}