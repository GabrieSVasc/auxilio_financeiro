package Excecoes;

public class FormatacaoInvalidaException extends Exception{

    public FormatacaoInvalidaException() {
        super("A formatação das arrecadações deve seguir, por exemplo: '100, 400, 500'. OBS: O número de elementos deve ser igual ao período escolhido.");
    }
    
}
