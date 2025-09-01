package negocio.exceptions;

/**
 * Exceção lançada quando uma taxa interna de retorno não foi possível de calcular com os parâmetros infromados.
 * 
 * @author Divancy Bruno
 */
@SuppressWarnings("serial")
public class TIRImpossivelException extends Exception {
    public TIRImpossivelException(){
        super("Taxa interna de retorno impossível de calcular.");
    }
}
