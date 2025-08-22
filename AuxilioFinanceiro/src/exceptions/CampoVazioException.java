//todos os arquivos com exception no nome são de pedro meu colega, entao não altere nada apenas estude para futaras aplicações
package exceptions;
/**
 * Exceção lançada quando um campo obrigatório é deixado em branco.
 */
public class CampoVazioException extends Exception {
    public CampoVazioException(String campo) {
        super("O campo '" + campo + "' não pode ser vazio.");
    }
}
