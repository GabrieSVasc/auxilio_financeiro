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

public class LembreteManager {
	private final List<Lembrete> lembretes = new ArrayList<>();
	private MensalidadeManager mensalidadeManager;
	private LimiteManager limiteManager;
	private final CategoriaManager categoriaManager;

	public LembreteManager(MensalidadeManager mensalidadeManager, CategoriaManager categoriaManager) {
		this.mensalidadeManager = mensalidadeManager;
		this.categoriaManager = categoriaManager;
		carregarLembretes();
	}

	// Novos métodos adicionados para resolver a dependência circular
	public void setMensalidadeManager(MensalidadeManager mensalidadeManager) {
		this.mensalidadeManager = mensalidadeManager;
	}

	public void setLimiteManager(LimiteManager limiteManager) {
		this.limiteManager = limiteManager;
	}

	private void carregarLembretes() {
		try {
			// Passa as listas de limites e mensalidades, se existirem
			List<Mensalidade> mensalidades = (mensalidadeManager != null) ? mensalidadeManager.listarMensalidades()
					: new ArrayList<>();
			List<Limite> limites = (limiteManager != null) ? limiteManager.getLimites() : new ArrayList<>();
			lembretes.addAll(LembreteRepository.carregar(mensalidades, limites));
		} catch (IOException e) {
			System.err.println("Erro ao carregar lembretes: " + e.getMessage());
		}
	}

	public void criarLembrete(Lembrete lembrete) throws IOException, ObjetoNuloException {
		if (lembrete == null) {
			throw new ObjetoNuloException("Lembrete");
		}
		lembretes.add(lembrete);
		LembreteRepository.salvar(lembretes);
	}

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

	public void ativarDesativarLembrete(int id, boolean mudanca) throws IOException {
		Lembrete l = buscarPorId(id);
		l.setAtivo(mudanca);
		LembreteRepository.salvar(lembretes);
	}

	public ArrayList<Lembrete> getLembreteDia() throws IOException {
		ArrayList<Lembrete> l = new ArrayList<>();
		LocalDate agora = LocalDate.now();
		for (Lembrete lembrete : listarTodos()) {
			LocalDate dataAlerta = lembrete.getDataAlerta();
			if (lembrete.isAtivo() && (dataAlerta.isBefore(agora) || dataAlerta.isEqual(agora)|| dataAlerta.isBefore(agora.plusDays(7)))) {
				l.add(lembrete);
			}
		}
		return l;
	}

	public void removerLembrete(int id) throws ObjetoNaoEncontradoException, IOException {
		boolean removido = lembretes.removeIf(l -> l.getId() == id);
		if (!removido) {
			throw new ObjetoNaoEncontradoException("Lembrete", id);
		}
		LembreteRepository.salvar(lembretes);
	}

	public List<Lembrete> listarTodos() {
		return new ArrayList<>(lembretes);
	}

	public List<MensalidadeLembrete> listarLembretesMensalidade() {
		return lembretes.stream().filter(l -> l instanceof MensalidadeLembrete).map(l -> (MensalidadeLembrete) l)
				.collect(Collectors.toList());
	}

	public List<LembreteLimite> listarLembretesLimite() {
		return lembretes.stream().filter(l -> l instanceof LembreteLimite).map(l -> (LembreteLimite) l)
				.collect(Collectors.toList());
	}

	public Lembrete buscarPorId(int id) {
		return lembretes.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
	}
}