package negocio.exceptions;

@SuppressWarnings("serial")
public class MesSemGastosException extends Exception {

	public MesSemGastosException() {
		super("Mês desejado não possui gastos cadastrados");
	}
}