package Excecoes;

public class TIRImpossivelException extends Exception {
    public TIRImpossivelException(){
        super("Taxa interna de retorno impossível de calcular.");
    }
}
