package negocio.exceptions;

public class MesSemGastosException extends Exception {

	public MesSemGastosException() {
		super("Mês desejado não possui gastos cadastrados");
	}
}