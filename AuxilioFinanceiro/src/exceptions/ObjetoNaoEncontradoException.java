package exceptions;
/*Exceção lançada quando um objeto não é encontrado por seu identificador.*/
public class ObjetoNaoEncontradoException extends Exception {
    public ObjetoNaoEncontradoException(String tipo, int id) {
        super(tipo + " com ID " + id + " não encontrado.");
    }
}