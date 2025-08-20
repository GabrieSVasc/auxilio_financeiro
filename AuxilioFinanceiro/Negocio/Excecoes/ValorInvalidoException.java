package Excecoes;

public class ValorInvalidoException extends Exception {
    // Negativo, zero, vazio ou letra/palavra
    public ValorInvalidoException() {
        super("Valor menor ou igual a zero.");
    }
    
}
