package negocio.exceptions;

@SuppressWarnings("serial")
public class OpcaoInvalidaException extends Exception {
    public OpcaoInvalidaException() {
        super("Opção inválida.");
    }
}
