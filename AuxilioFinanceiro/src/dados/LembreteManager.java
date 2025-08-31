package dados;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import negocio.entidades.Lembrete;
import negocio.entidades.LembreteLimite;
import negocio.entidades.Limite;
import negocio.entidades.Mensalidade;
import negocio.entidades.MensalidadeLembrete;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;

/**
 * Classe responsável por gerenciar os lembretes do sistema.
 * 
 * Utiliza conceitos de Orientação a Objetos para encapsular a lista de lembretes,
 * além de gerenciar a integração com mensalidades e limites através dos managers correspondentes.
 * 
 * A classe demonstra o uso de composição para resolver dependências entre managers,
 * além de aplicar encapsulamento, tratamento de exceções e programação funcional.
 * 
 * @author Halina Mochel
 */
public class LembreteManager {
	// Lista interna que armazena os lembretes em memória
	private final List<Lembrete> lembretes = new ArrayList<>();
	// Referência para o gerenciador de mensalidades, para integração e carregamento
	private MensalidadeManager mensalidadeManager;
	// Referência para o gerenciador de limites, para integração e carregamento
	private LimiteManager limiteManager;

	/**
	 * Construtor que recebe o gerenciador de mensalidades e carrega os lembretes persistidos.
	 * Demonstra a composição e a necessidade de resolver dependências circulares via setters.
	 * 
	 * @param mensalidadeManager Gerenciador de mensalidades para associação
	 */
	public LembreteManager(MensalidadeManager mensalidadeManager) {
		this.mensalidadeManager = mensalidadeManager;
		carregarLembretes();
	}

	/**
	 * Setter para o gerenciador de mensalidades, permitindo resolver dependência circular.
	 * Demonstra flexibilidade no design para evitar acoplamento rígido.
	 * 
	 * @param mensalidadeManager Gerenciador de mensalidades
	 */
	public void setMensalidadeManager(MensalidadeManager mensalidadeManager) {
		this.mensalidadeManager = mensalidadeManager;
	}

	/**
	 * Setter para o gerenciador de limites, permitindo injeção de dependência após criação.
	 * Facilita a modularidade e testabilidade do código.
	 * 
	 * @param limiteManager Gerenciador de limites
	 */
	public void setLimiteManager(LimiteManager limiteManager) {
		this.limiteManager = limiteManager;
	}

	/**
	 * Método privado que carrega os lembretes do repositório, associando mensalidades e limites.
	 * Trata exceções para garantir que falhas na carga não quebrem o sistema.
	 */
	private void carregarLembretes() {
		try {
			// Obtém as listas atuais de mensalidades e limites, se disponíveis
			List<Mensalidade> mensalidades = (mensalidadeManager != null) ? mensalidadeManager.listarMensalidades()
					: new ArrayList<>();
			List<Limite> limites = (limiteManager != null) ? limiteManager.getLimites() : new ArrayList<>();
			// Carrega os lembretes do repositório, associando as listas obtidas
			lembretes.addAll(LembreteRepository.carregar(mensalidades, limites));
		} catch (IOException e) {
			System.err.println("Erro ao carregar lembretes: " + e.getMessage());
		}
	}

	/**
	 * Cria um novo lembrete e persiste a alteração.
	 * Aplica validação para evitar inserção de objetos nulos.
	 * 
	 * @param lembrete Objeto lembrete a ser criado
	 * @throws IOException Caso ocorra erro na persistência
	 * @throws ObjetoNuloException Caso o objeto lembrete seja nulo
	 */
	public void criarLembrete(Lembrete lembrete) throws IOException, ObjetoNuloException {
		if (lembrete == null) {
			throw new ObjetoNuloException("Lembrete");
		}
		lembretes.add(lembrete);
		LembreteRepository.salvar(lembretes);
	}

	/**
	 * Atualiza os dados de um lembrete existente identificado pelo id.
	 * Aplica validações para campos não nulos e não vazios.
	 * 
	 * @param id Identificador do lembrete a ser atualizado
	 * @param novoTitulo Novo título (pode ser null para não alterar)
	 * @param novaDescricao Nova descrição (pode ser null para não alterar)
	 * @param novaData Nova data de alerta (pode ser null para não alterar)
	 * @throws ObjetoNaoEncontradoException Caso o lembrete não seja encontrado
	 * @throws IOException Caso ocorra erro na persistência
	 * @throws CampoVazioException Caso algum campo obrigatório esteja vazio
	 */
	public void atualizarLembrete(int id, String novoTitulo, String novaDescricao, LocalDate novaData)
			throws ObjetoNaoEncontradoException, IOException, CampoVazioException {
		Lembrete lembrete = buscarPorId(id);
		if (lembrete == null) {
			throw new ObjetoNaoEncontradoException("Lembrete", id);
		}
		if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
			lembrete.setTitulo(novoTitulo);
		}
		if (novaDescricao != null && !novaDescricao.trim().isEmpty()) {
			lembrete.setDescricao(novaDescricao);
		}
		if (novaData != null) {
			lembrete.setDataAlerta(novaData);
		}
		LembreteRepository.salvar(lembretes);
	}

	/**
	 * Ativa ou desativa um lembrete pelo seu identificador.
	 * Demonstra encapsulamento e controle do estado dos objetos.
	 * 
	 * @param id Identificador do lembrete
	 * @param mudanca Novo estado (true para ativo, false para inativo)
	 * @throws IOException Caso ocorra erro na persistência
	 */
	public void ativarDesativarLembrete(int id, boolean mudanca) throws IOException {
		Lembrete l = buscarPorId(id);
		l.setAtivo(mudanca);
		LembreteRepository.salvar(lembretes);
	}

	/**
	 * Retorna os lembretes ativos cujo alerta está para hoje ou dentro dos próximos 7 dias.
	 * Demonstra uso de datas e filtragem para funcionalidades específicas.
	 * 
	 * @return Lista de lembretes para o dia ou próximos dias
	 * @throws IOException Caso ocorra erro na leitura dos dados
	 */
	public ArrayList<Lembrete> getLembreteDia() throws IOException {
		ArrayList<Lembrete> l = new ArrayList<>();
		LocalDate agora = LocalDate.now();
		for (Lembrete lembrete : listarTodos()) {
			LocalDate dataAlerta = lembrete.getDataAlerta();
			// Verifica se o lembrete está ativo e se a data de alerta está dentro do intervalo desejado
			if (lembrete.isAtivo() && (dataAlerta.isBefore(agora) || dataAlerta.isEqual(agora) || dataAlerta.isBefore(agora.plusDays(7)))) {
				l.add(lembrete);
			}
		}
		return l;
	}

	/**
	 * Remove um lembrete pelo seu identificador.
	 * Lança exceção caso o lembrete não seja encontrado.
	 * 
	 * @param id Identificador do lembrete a ser removido
	 * @throws ObjetoNaoEncontradoException Caso o lembrete não exista
	 * @throws IOException Caso ocorra erro na persistência
	 */
	public void removerLembrete(int id) throws ObjetoNaoEncontradoException, IOException {
		boolean removido = lembretes.removeIf(l -> l.getId() == id);
		if (!removido) {
			throw new ObjetoNaoEncontradoException("Lembrete", id);
		}
		LembreteRepository.salvar(lembretes);
	}

	/**
	 * Retorna uma cópia da lista de todos os lembretes.
	 * Demonstra encapsulamento para proteger a lista interna.
	 * 
	 * @return Lista de lembretes
	 */
	public List<Lembrete> listarTodos() {
		return new ArrayList<>(lembretes);
	}

	/**
	 * Retorna a lista de lembretes do tipo MensalidadeLembrete.
	 * Demonstra uso de programação funcional para filtragem e casting.
	 * 
	 * @return Lista de lembretes relacionados a mensalidades
	 */
	public List<MensalidadeLembrete> listarLembretesMensalidade() {
		return lembretes.stream()
				.filter(l -> l instanceof MensalidadeLembrete)
				.map(l -> (MensalidadeLembrete) l)
				.collect(Collectors.toList());
	}

	/**
	 * Retorna a lista de lembretes do tipo LembreteLimite.
	 * Demonstra uso de programação funcional para filtragem e casting.
	 * 
	 * @return Lista de lembretes relacionados a limites
	 */
	public List<LembreteLimite> listarLembretesLimite() {
		return lembretes.stream()
				.filter(l -> l instanceof LembreteLimite)
				.map(l -> (LembreteLimite) l)
				.collect(Collectors.toList());
	}

	/**
	 * Busca um lembrete pelo seu identificador.
	 * Demonstra encapsulamento e uso de streams para busca eficiente.
	 * 
	 * @param id Identificador do lembrete
	 * @return Objeto lembrete encontrado ou null se não existir
	 */
	public Lembrete buscarPorId(int id) {
		return lembretes.stream()
				.filter(l -> l.getId() == id)
				.findFirst()
				.orElse(null);
	}
}