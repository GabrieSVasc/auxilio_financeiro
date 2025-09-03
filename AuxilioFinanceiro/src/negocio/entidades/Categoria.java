package negocio.entidades;

import negocio.exceptions.CampoVazioException;

/**
 * Representa uma categoria de gastos ou metas no sistema financeiro. Uma
 * categoria pode agrupar gastos e limites específicos.
 * 
 * @author Pedro Farias
 */
public class Categoria implements Exibivel {
	private static int contador = 1;
	private final int id;
	private String nome;

	public Categoria(String nome) throws CampoVazioException {
		setNome(nome);
		this.id = contador++;
	}

	// Construtor para recarga do arquivo
	public Categoria(int id, String nome) throws CampoVazioException {
		setNome(nome);
		this.id = id;
		if (id >= contador)
			contador = id + 1;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws CampoVazioException {
		if (nome == null || nome.trim().isEmpty())
			throw new CampoVazioException("Nome da categoria");
		this.nome = nome.trim();
	}

	@Override
	public String exibir() {
		return "Categoria #" + id + " - " + nome;
	}

// Método adicionado para resetar o contador, conforme necessário no Main
	public static void resetarContador() {
		contador = 1;
	}
}