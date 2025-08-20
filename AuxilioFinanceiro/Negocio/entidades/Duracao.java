package entidades;

import Excecoes.OpcaoInvalidaException;
import Excecoes.ValorInvalidoException;
import utils.ValidarValor;

public class Duracao {
    private int tipo;
    private double tempo;
    
    public Duracao(int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        if (!ValidarValor.ehPositivo(tempo)) throw new ValorInvalidoException();
        else if (!ValidarValor.opcaoValida(0, 1, tipo)) throw new OpcaoInvalidaException();
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
        if (!ValidarValor.opcaoValida(0, 1, novoTipo)) throw new OpcaoInvalidaException();
        else this.tipo = novoTipo;
    }
    public void setTempo(double novoTempo) throws ValorInvalidoException{
        if (!ValidarValor.ehPositivo(novoTempo)) throw new ValorInvalidoException();
        else this.tempo = novoTempo;
    }
}
