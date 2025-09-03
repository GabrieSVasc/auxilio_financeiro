package negocio;

import java.util.List;
import java.util.Scanner;

import dados.LimiteRepository;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.entidades.LembreteLimite;
import negocio.entidades.Limite;
import negocio.exceptions.ObjetoJaExisteException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;
import negocio.exceptions.ValorNegativoException;
import util.ConsoleIO;

/**
 * Classe que cria as regras de negócio associadas aos limites
 * 
 * @author Pedro Farias
 */

public class LimiteManager implements CrudMenu {
	private LimiteRepository limiteRepository;
	private CategoriaManager categoriaManager;
	private final GastoManager gastoManager;
	private LembreteManager lembreteManager;
	private final Scanner scanner = new Scanner(System.in);

	/**
	 * Construtor que inicializa o repositório de limites
	 * 
	 * @param cm Categoria Manager
	 * @param gm Gasto Manager
	 */
	public LimiteManager(CategoriaManager cm, GastoManager gm) {
		limiteRepository = new LimiteRepository(cm);
		categoriaManager = cm;
		this.gastoManager = gm;
	}

	public void setLembreteManager(LembreteManager lm) {
		lembreteManager = lm;
	}

	/**
	 * Método que atualiza os totais dos limites
	 * 
	 * @param cat
	 */
	public void atualizarTotais(Categoria cat) {
		if (cat == null)
			return;
		try {
			List<Gasto> todosGastos = gastoManager.listarGastos();
			double total = todosGastos.stream().filter(g -> g.getCategoria().getId() == cat.getId())
					.mapToDouble(Gasto::getValor).sum();

			Limite lim = limiteRepository.getLimites().stream().filter(l -> l.getCategoria().getId() == cat.getId())
					.findFirst().orElse(null);

			if (lim != null) {
				lim.setTotalGastos(total);
			}
		} catch (Exception e) {
			System.err.println("Erro ao atualizar totais da categoria " + cat.getNome() + ": " + e.getMessage());
		}
	}

	/**
	 * Método que atualiza os lembretes de limites
	 * 
	 * @param limite
	 */
	public void atualizarLembretesLimite(Limite limite) {
		try {
			List<LembreteLimite> lembretes = lembreteManager.listarLembretesLimite().stream()
					.filter(l -> l.getLimite() != null && l.getLimite().getId() == limite.getId()).toList();

			for (LembreteLimite lembrete : lembretes) {
				lembrete.setGastoAtual(limite.getTotalGastos());
				lembreteManager.atualizarLembrete(lembrete.getId(), lembrete.getTitulo(), lembrete.getDescricao(),
						lembrete.getDataAlerta(), lembrete.isAtivo());
			}
		} catch (Exception e) {
			System.err.println("Erro ao atualizar lembretes: " + e.getMessage());
		}
	}

	/**
	 * Método criado para o caso do uso do console como IU
	 */
	@Override
	public void menu() {
		String opcao;
		do {
			System.out.println("\n--- Menu Limite ---");
			System.out.println("1 - Criar");
			System.out.println("2 - Listar");
			System.out.println("3 - Editar");
			System.out.println("4 - Excluir");
			System.out.println("0 - Sair");
			opcao = ConsoleIO.readOption(scanner, "Escolha: ", "[0-4]");
			try {
				switch (opcao) {
				case "1" -> criar(0, 0);
				case "2" -> listar();
				case "3" -> editar(0, 0);
				case "4" -> deletar(0);
				case "0" -> System.out.println("Saindo do menu de limites.");
				}
			} catch (Exception e) {
				System.out.println("[ERRO] " + e.getMessage());
			}
		} while (!"0".equals(opcao));
	}

	/**
	 * Método que estabelece as regras de negócio da criação de limites
	 * 
	 * @param idCat
	 * @param valor
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 * @throws ObjetoNuloException
	 * @throws ObjetoJaExisteException
	 */
	public void criar(int idCat, double valor)
			throws ObjetoNaoEncontradoException, ValorNegativoException, ObjetoNuloException, ObjetoJaExisteException {
		if (categoriaManager.getCategorias().isEmpty()) {
			System.out.println("Crie categorias primeiro.");
			return;
		}
		if (idCat <= 0)
			throw new ValorNegativoException("ID da categoria");
		Categoria categoriaSelecionada = categoriaManager.getCategoria(idCat);
		if (categoriaSelecionada == null)
			throw new ObjetoNaoEncontradoException("Categoria", idCat);
		if (limiteRepository.existe(categoriaSelecionada.getNome())) {
			throw new ObjetoJaExisteException("Já foi definido um limite para esta categoria");
		}
		limiteRepository.criar(new Limite(categoriaSelecionada, valor));
	}

	/**
	 * Método criado para o caso do uso do console como IU, lista no console todos
	 * os limites
	 */
	private void listar() {
		if (limiteRepository.isEmpty()) {
			System.out.println("Nenhum limite cadastrado.");
			return;
		}
		System.out.println(limiteRepository.listar());
	}

	/**
	 * Método que estabelesce as regras de negócio da edição de limites
	 * 
	 * @param id
	 * @param novoValor
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 */
	public void editar(int id, double novoValor) throws ObjetoNaoEncontradoException, ValorNegativoException {
		if (id <= 0)
			throw new ValorNegativoException("ID");
		Limite limite = limiteRepository.consultar(id);
		if (limite == null)
			throw new ObjetoNaoEncontradoException("Limite", id);
		limiteRepository.atualizar(limite, novoValor);
	}

	/**
	 * Método que estabelesce as regras de negócios da remoção de limites
	 * 
	 * @param id
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 */
	public void deletar(int id) throws ObjetoNaoEncontradoException, ValorNegativoException {
		if (id <= 0)
			throw new ValorNegativoException("ID");
		Limite encontrado = limiteRepository.consultar(id);
		if (encontrado == null)
			throw new ObjetoNaoEncontradoException("Limite", id);
		limiteRepository.remover(encontrado);
	}

	/**
	 * Método que retorna todos os limites cadastrados
	 * 
	 * @return Uma lista com todos os limites
	 */
	public List<Limite> getLimites() {
		for (Categoria c : categoriaManager.getCategorias()) {
			this.atualizarTotais(c);
		}
		for(Limite l: limiteRepository.getLimites()) {
			this.atualizarLembretesLimite(l);
		}
		return limiteRepository.getLimites();
	}

	/**
	 * Método que busca um limite específico pelo id
	 * 
	 * @param id
	 * @return
	 */
	public Limite getLimite(int id) {
		return limiteRepository.consultar(id);
	}
}