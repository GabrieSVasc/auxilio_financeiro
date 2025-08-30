package negocio.exceptions;

@SuppressWarnings("serial")
public class ValorInvalidoException extends Exception {
    public ValorInvalidoException() {
        super("Valor menor ou igual a zero.");
    }
    
}
