package negocio.entidades;

import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;
/**
 * Classe Duracao
 * A qual tem como objetivo controlar o período que sejá usado nos cálculos
 * 
 * Controlando se ele é meses ou anos e fazendo as adaptações necessárias.
 * 
 * @author Divancy Bruno
 */
public class Duracao {
    private int tipo;
    private double tempo;
    
    /** construtor da classe Duracao
     * 
     * @param tipo, define se será em meses (0) ou anos (1).
     * @param tempo, define o tempo, seja em meses ou anos.
     * 
     * @throws ValorInvalidoException, caso o tempo seja menor ou igual a 0
     * @throws OpcaoInvalidaException, caso o tipo passado seja diferente de 0 ou 1.
    */
    public Duracao(int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        if (tempo <= 0) throw new ValorInvalidoException();
        else if (tipo > 1 || tipo < 0) throw new OpcaoInvalidaException();
        else{
            this.tipo = tipo;
            if (tipo == 0) this.tempo = tempo;
            else if (tipo == 1) this.tempo = tempo*12;
        }
    }

    
    // Getters
    public int getTipo(){ return tipo; }
    public double getTempo(){ return tempo; }

    // Setters
    public void setTipo(int novoTipo) throws OpcaoInvalidaException{
        if (tipo > 1 || tipo < 0) throw new OpcaoInvalidaException();
        else this.tipo = novoTipo;
    }
    public void setTempo(double novoTempo) throws ValorInvalidoException{
        if (novoTempo <= 0) throw new ValorInvalidoException();
        else this.tempo = novoTempo;
    }
}
