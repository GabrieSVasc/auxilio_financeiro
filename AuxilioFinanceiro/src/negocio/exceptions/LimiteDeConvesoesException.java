package negocio.exceptions;

/**
 * Exceção lançada quando o usuário tenta realizar o câmbio e a key da API
 * estiver inválida pois o limite de requisições foi excedido
 * 
 * @author Maria Gabriela
 */

@SuppressWarnings("serial")
public class LimiteDeConvesoesException extends Exception {

	public LimiteDeConvesoesException(String v) {
		super(v);
	}
}