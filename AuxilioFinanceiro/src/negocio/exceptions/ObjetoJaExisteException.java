package negocio.exceptions;


@SuppressWarnings("serial")
public class ObjetoJaExisteException extends Exception {

	public ObjetoJaExisteException(String texto) {
		super(texto);
	}
}