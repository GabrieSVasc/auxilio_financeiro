package negocio;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dados.MetaRepository;
import negocio.entidades.CrudMenu;
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
	private LembreteManager lembreteManager; // Injeção do LembreteManager
	private final Scanner sc = new Scanner(System.in);

	/**
	 * Construtor que inicializa o repositório de metas
	 * 
	 * @param lembreteManager
	 */
	public MetaManager() {
		metaRepository = new MetaRepository(); // Inicializa o LembreteManager
	}
	
	public void setLembreteManager(LembreteManager lm) {
		this.lembreteManager = lm;
	}

	/**
	 * Método criado para o caso de uso do console como IU
	 */
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

	/**
	 * Método que estabelece a regra de negócio da criação de metas
	 * 
	 * @param desc     Descrição da meta
	 * @param objetivo Valor de objetivo da meta
	 * @param atual    Valor atual da meta
	 * @param prazo    Prazo estabelecido
	 * @throws ObjetoNuloException
	 */
	public void criar(String desc, double objetivo, double atual, LocalDate prazo) throws ObjetoNuloException {
		Meta m = new Meta(desc, objetivo, atual, prazo);
		metaRepository.criar(m);

		// Adiciona lembrete associado à nova meta
		LembreteMeta lembrete = new LembreteMeta(m, "Acompanhamento da meta " + desc);
		lembreteManager.criarLembrete(lembrete);
	}

	/**
	 * Método criado para o caso de uso do console como IU, lista todas as metas no
	 * console
	 */
	private void listar() {
		if (metaRepository.isEmpty()) {
			System.out.println("Nenhuma meta cadastrada.");
			return;
		}
		System.out.println(metaRepository.listar());
	}

	/**
	 * Método que lida com as regras de negócio da edição de metas
	 * 
	 * @param id       Id da meta
	 * @param desc     Nova descrição
	 * @param objetivo Novo valor objetivo
	 * @param atual    Novo valor atual
	 * @param data     Nova data de prazo
	 * @throws ObjetoNaoEncontradoException
	 * @throws CampoVazioException
	 */
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

	/**
	 * Método que lida com as regras de negócio da remoção de metas
	 * 
	 * @param id Id da meta
	 * @throws ObjetoNaoEncontradoException
	 */
	public void deletar(int id) throws ObjetoNaoEncontradoException {
		Meta meta = metaRepository.consultar(id);
		if (meta == null) {
			throw new ObjetoNaoEncontradoException("Meta", id);
		}
		lembreteManager.removerLembrete(id);
		metaRepository.remover(meta);
	}

	/**
	 * Método que retorna todas as metas cadastradas
	 * 
	 * @return Lista com as metas cadastradas
	 */
	public List<Meta> getMetas() {
		return metaRepository.getMetas();
	}

	/**
	 * Método que busca uma meta específica
	 * 
	 * @param id Id da meta
	 * @return Meta buscada ou null se não for encontrada
	 */
	public Meta getMeta(int id) {
		return metaRepository.consultar(id);
	}
}