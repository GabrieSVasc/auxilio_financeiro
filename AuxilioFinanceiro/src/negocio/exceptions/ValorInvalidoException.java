package negocio.exceptions;
/**
 * Exceção lançada quando um valor numérico informado é zero ou negativo.
 * 
 * @author Divancy Bruno
 */
@SuppressWarnings("serial")
public class ValorInvalidoException extends Exception {
    public ValorInvalidoException() {
        super("Valor menor ou igual a zero.");
    }
    
}
