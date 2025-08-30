package negocio.exceptions;
/**
 * Exceção lançada quando um campo obrigatório é deixado em branco.
 * 
 * @author Pedro Farias
 */
@SuppressWarnings("serial")
public class CampoVazioException extends Exception {
    public CampoVazioException(String campo) {
        super("O campo '" + campo + "' não pode ser vazio.");
    }
}