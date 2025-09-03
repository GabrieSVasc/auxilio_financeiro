package negocio.exceptions;

/**
 * Exceção lançada ao haver problemas externos ao sistema ao tentar realizar o
 * câmbio
 * 
 * @author Maria Gabriela
 */

@SuppressWarnings("serial")
public class ErroAoReceberConversaoException extends Exception {

	public ErroAoReceberConversaoException(String v) {
		super(v);
	}
}