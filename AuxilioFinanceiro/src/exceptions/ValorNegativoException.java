package exceptions;
/**
 * Exceção lançada quando um valor numérico negativo é informado onde não deveria.
 * 
 * @author Pedro Farias
 */
public class ValorNegativoException extends Exception {
    public ValorNegativoException(String campo) {
        super("O campo '" + campo + "' não pode ter valor negativo.");
    }
}
