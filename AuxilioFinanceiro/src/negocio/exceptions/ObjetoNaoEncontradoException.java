package negocio.exceptions;
/**
 * Exceção lançada quando um objeto não é encontrado por seu identificador.
 * 
 * @author Pedro Farias
 */
@SuppressWarnings("serial")
public class ObjetoNaoEncontradoException extends Exception {
	private String tipoObjeto;
    public ObjetoNaoEncontradoException(String tipo, int id) {
        super(tipo + " com ID " + id + " não encontrado.");
        tipoObjeto = tipo;
    }
    
    public String getTipoObjeto() {
    	return tipoObjeto;
    }
}