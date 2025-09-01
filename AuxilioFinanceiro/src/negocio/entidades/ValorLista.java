package negocio.entidades;
/**
 * Classe para facilitar vizualização e manipulação de listas na interface
 * @author Maria Gabriela
 */
public class ValorLista {
	private String stringLista;
	private int id;
	private boolean ativo;

	/**
	 * Construtor comum
	 * @param stringLista
	 * @param id
	 */
	public ValorLista(String stringLista, int id) {
		this.stringLista = stringLista;
		this.id = id;
	}
	
	/**
	 * Construtor especifico para lembretes
	 * @param stringLista
	 * @param id
	 * @param ativo
	 */
	public ValorLista(String stringLista, int id, boolean ativo) {
		this.stringLista = stringLista;
		this.id = id;
		this.ativo = ativo;
	}
	
	public String getStringLista() {
		return stringLista;
	}
	public int getId() {
		return id;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	@Override
	public String toString() {
		return this.stringLista;
	}
}