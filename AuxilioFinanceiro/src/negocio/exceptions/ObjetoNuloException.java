package negocio.exceptions;
/**
 * Exceção lançada quando um objeto obrigatório foi fornecido como null.
 * 
 * @author Pedro Farias
 */
@SuppressWarnings("serial")
public class ObjetoNuloException extends Exception {
    public ObjetoNuloException(String objeto) {
        super("O objeto '" + objeto + "' não pode ser nulo.");
    }
}