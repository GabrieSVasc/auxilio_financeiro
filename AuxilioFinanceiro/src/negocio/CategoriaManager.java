package negocio;

import java.util.List;
import java.util.Scanner;

import dados.CategoriaRepository;
import negocio.entidades.Categoria;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoJaExisteException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ValorNegativoException;
import util.ConsoleIO;

/**
 * Classe de negócio de categorias.
 * 
 * @author Pedro Farias
 */
public class CategoriaManager implements CrudMenu {
	private CategoriaRepository categoriaRepository;
	private final Scanner scanner = new Scanner(System.in);

	/**
	 * Construtor que inicializa o repositório de categorias
	 */
	public CategoriaManager() {
		categoriaRepository = new CategoriaRepository();
	}

	/**
	 * Método criado para o caso de ser usado o console com IU
	 */
	@Override
	public void menu() {
		String opcao;
		do {
			System.out.println("\n--- Menu Categoria ---");
			System.out.println("1 - Criar");
			System.out.println("2 - Listar");
			System.out.println("3 - Editar");
			System.out.println("4 - Excluir");
			System.out.println("0 - Sair");
			opcao = ConsoleIO.readOption(scanner, "Escolha: ", "[0-4]");
			try {
				switch (opcao) {
				case "1" -> criar("");
				case "2" -> listar();
				case "3" -> editar(0, "");
				case "4" -> deletar(0);
				case "0" -> System.out.println("Saindo do menu de categorias.");
				}
			} catch (CampoVazioException | ValorNegativoException | ObjetoNaoEncontradoException e) {
				System.out.println("[ERRO] " + e.getMessage());
			} catch (Exception e) {
				System.out.println("[ERRO] Ocorreu um erro inesperado: " + e.getMessage());
			}
		} while (!"0".equals(opcao));
	}

	/**
	 * Método que estabelece as regras de negócio da criação de categorias
	 * 
	 * @param nome
	 * @throws CampoVazioException
	 * @throws ObjetoJaExisteException
	 */
	public void criar(String nome) throws CampoVazioException, ObjetoJaExisteException {
		if (!categoriaRepository.existe(nome)) {
			categoriaRepository.criar(new Categoria(nome));
		} else {
			throw new ObjetoJaExisteException("Categoria com este nome já existe");
		}
	}

	/**
	 * Método criado para o caso de ser usado console com IU, lista todas as
	 * categorias
	 */
	private void listar() {
		if (categoriaRepository.isEmpty()) {
			System.out.println("Nenhuma categoria.");
			return;
		}
		System.out.println(categoriaRepository.listar());
	}

	/**
	 * Método que estabelece as regras de negócio de edição de categorias
	 * 
	 * @param id
	 * @param novoNome
	 * @throws ValorNegativoException
	 * @throws ObjetoNaoEncontradoException
	 * @throws CampoVazioException
	 */
	public void editar(int id, String novoNome)
			throws ValorNegativoException, ObjetoNaoEncontradoException, CampoVazioException {
		if (id <= 0)
			throw new ValorNegativoException("ID");
		Categoria encontrada = categoriaRepository.consultar(id);
		if (encontrada == null)
			throw new ObjetoNaoEncontradoException("Categoria", id);
		categoriaRepository.atualizar(encontrada, novoNome);
	}

	/**
	 * Método que estabelece as regras de negócio da remoção de categorias
	 * 
	 * @param id
	 * @throws ValorNegativoException
	 * @throws ObjetoNaoEncontradoException
	 */
	public void deletar(int id) throws ValorNegativoException, ObjetoNaoEncontradoException {
		if (id <= 0)
			throw new ValorNegativoException("ID");
		Categoria encontrada = categoriaRepository.consultar(id);
		if (encontrada == null)
			throw new ObjetoNaoEncontradoException("Categoria", id);
		categoriaRepository.remover(encontrada);
	}

	/**
	 * Método que retorna todas as categorias cadastradas
	 * 
	 * @return Lista com todas as categorias cadastradas
	 */
	public List<Categoria> getCategorias() {
		return categoriaRepository.getCategorias();
	}

	/**
	 * Método que busca uma categoria pelo id
	 * 
	 * @param id
	 * @return A categoria encontrada ou null
	 */
	public Categoria getCategoria(int id) {
		return categoriaRepository.consultar(id);
	}
}