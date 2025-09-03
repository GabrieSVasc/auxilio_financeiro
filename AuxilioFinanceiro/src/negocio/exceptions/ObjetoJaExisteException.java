package negocio.exceptions;

/**
 * Exceção lançada ao tentar cadastrar novamente um objeto que já existe
 * 
 * @author Maria Gabriela
 */

@SuppressWarnings("serial")
public class ObjetoJaExisteException extends Exception {

	public ObjetoJaExisteException(String texto) {
		super(texto);
	}
}