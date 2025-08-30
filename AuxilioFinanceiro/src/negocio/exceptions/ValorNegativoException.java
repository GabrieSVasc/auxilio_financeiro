package negocio.exceptions;
/**
 * Exceção lançada quando um valor numérico negativo é informado onde não deveria.
 * 
 * @author Pedro Farias
 */
@SuppressWarnings("serial")
public class ValorNegativoException extends Exception {
    public ValorNegativoException(String campo) {
        super("O campo '" + campo + "' não pode ter valor negativo.");
    }
}
