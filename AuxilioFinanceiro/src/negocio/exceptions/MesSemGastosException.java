package negocio.exceptions;

/**
 * Exceção lançada ao tentar gerar um gráfico de setores de um mês sem gastos
 * 
 * @author Maria Gabriela
 */

@SuppressWarnings("serial")
public class MesSemGastosException extends Exception {

	public MesSemGastosException() {
		super("Mês desejado não possui gastos cadastrados");
	}
}