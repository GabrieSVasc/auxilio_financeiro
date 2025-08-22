package service;

public class CategoriaSemGastosException extends Exception {

	public CategoriaSemGastosException() {
		super("Categoria desejada sem gastos cadastrados");
	}
}