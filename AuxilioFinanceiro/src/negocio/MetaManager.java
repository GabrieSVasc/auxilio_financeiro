package negocio;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dados.MetaRepository;
import negocio.entidades.LembreteMeta;
import negocio.entidades.Meta;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;
import util.ConsoleIO;

/**
 * Serviço para CRUD de Meta via console.
 * 
 * @author Pedro Farias
 */
public class MetaManager implements CrudMenu {
	private MetaRepository metaRepository;
	private final LembreteManager lembreteManager; // Injeção do LembreteManager
	private final Scanner sc = new Scanner(System.in);

	public MetaManager(LembreteManager lembreteManager) {
		metaRepository = new MetaRepository();
		this.lembreteManager = lembreteManager; // Inicializa o LembreteManager
	}

	@Override
	public void menu() throws CampoVazioException, ObjetoNuloException, ObjetoNaoEncontradoException {
		String op;
		do {
			System.out.println("\n--- Menu Metas ---");
			System.out.println("1 - Criar");
			System.out.println("2 - Listar");
			System.out.println("3 - Editar");
			System.out.println("4 - Excluir");
			System.out.println("0 - Sair");
			op = ConsoleIO.readOption(sc, "Escolha: ", "[0-4]");

			switch (op) {
			case "1" -> criar("", 0, 0, LocalDate.now());
			case "2" -> listar();
			case "3" -> editar(0, "", 0, 0, LocalDate.now());
			case "4" -> deletar(0);
			case "0" -> System.out.println("Saindo do menu de metas.");
			}
		} while (!"0".equals(op));
	}

	public void criar(String desc, double objetivo, double atual, LocalDate prazo)
			throws CampoVazioException, ObjetoNuloException {
		Meta m = new Meta(desc, objetivo, atual, prazo);
		metaRepository.criar(m);

		// Adiciona lembrete associado à nova meta
		LembreteMeta lembrete = new LembreteMeta(m, "Acompanhamento da meta " + desc);
		lembreteManager.criarLembrete(lembrete);
	}

	private void listar() {
		if (metaRepository.isEmpty()) {
			System.out.println("Nenhuma meta cadastrada.");
			return;
		}
		System.out.println(metaRepository.listar());
	}

	public void editar(int id, String desc, double objetivo, double atual, LocalDate data)
			throws ObjetoNaoEncontradoException, CampoVazioException {
		Meta m = metaRepository.consultar(id);
		if (m == null) {
			throw new ObjetoNaoEncontradoException("Meta", id);
		}
		metaRepository.atualizar(m, desc, objetivo, atual, data);

		// Atualiza lembrete associado à meta
		lembreteManager.atualizarLembrete(m.getId(), "Meta: " + m.getDescricao(),
				"Acompanhamento da meta " + m.getDescricao(), m.getDataPrazo(), true);
	}

	public void deletar(int id) throws ObjetoNaoEncontradoException{
		Meta meta = metaRepository.consultar(id);
		if (meta == null) {
			throw new ObjetoNaoEncontradoException("Meta", id);
		}
		lembreteManager.removerLembrete(id);
		metaRepository.remover(meta);
	}

	public List<Meta> getMetas() {
		return metaRepository.getMetas();
	}

	public Meta getMeta(int id) {
		return metaRepository.consultar(id);
	}
}