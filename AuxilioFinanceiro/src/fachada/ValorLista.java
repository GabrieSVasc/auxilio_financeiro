package fachada;

public class ValorLista {
	private String stringLista;
	private int id;
	public ValorLista(String stringLista, int id) {
		this.stringLista = stringLista;
		this.id = id;
	}
	public String getStringLista() {
		return stringLista;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return this.stringLista;
	}
}