package negocio.exceptions;

/**
 * Exceção lançada ao tentar gerar um gráfico de barras de uma categoria sem
 * gastos cadastrados
 * 
 * @author Maria Gabriela
 */

@SuppressWarnings("serial")
public class CategoriaSemGastosException extends Exception {

	public CategoriaSemGastosException() {
		super("Categoria desejada sem gastos cadastrados");
	}
}