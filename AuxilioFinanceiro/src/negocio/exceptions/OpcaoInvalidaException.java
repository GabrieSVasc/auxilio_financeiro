package negocio.exceptions;

/**
 * Exceção lançada quando uma opção inválida é escolhida
 * 
 * @author Divancy Bruno
 */

@SuppressWarnings("serial")
public class OpcaoInvalidaException extends Exception {
    public OpcaoInvalidaException() {
        super("Opção inválida.");
    }
}
