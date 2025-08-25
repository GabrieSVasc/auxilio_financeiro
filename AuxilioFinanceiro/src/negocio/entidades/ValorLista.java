package negocio.entidades;

public class ValorLista {
	private String stringLista;
	private int id;
	private boolean ativo;
	public ValorLista(String stringLista, int id) {
		this.stringLista = stringLista;
		this.id = id;
	}
	
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