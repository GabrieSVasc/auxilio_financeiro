package negocio.entidades.entidades;

import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.ValorInvalidoException;

public class Duracao {
    private int tipo;
    private double tempo;
    
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
