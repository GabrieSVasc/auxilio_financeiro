package fachada;

public class TransferindoListas {
	private String stringLista;
	private int id;
	public TransferindoListas(String stringLista, int id) {
		this.stringLista = stringLista;
		this.id = id;
	}
	public String getStringLista() {
		return stringLista;
	}
	public int getId() {
		return id;
	}
}