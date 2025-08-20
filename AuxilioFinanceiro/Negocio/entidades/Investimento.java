package entidades;

import Excecoes.OpcaoInvalidaException;
import Excecoes.ValorInvalidoException;
import utils.ValidarValor;

public abstract class Investimento {
    protected double capital, taxa, montante; 
    protected Duracao duracao;

    public Investimento(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        if (!ValidarValor.ehPositivo(capital) || !ValidarValor.ehPositivo(taxa)){
            throw new ValorInvalidoException();
        }
        else{
            this.capital = capital;
            this.taxa = taxa/100;
            this.duracao = duracao;
            this.montante = 0;
        }
    }
    public Investimento(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        if (!ValidarValor.ehPositivo(capital) || !ValidarValor.ehPositivo(taxa)){
            throw new ValorInvalidoException();
        }
        else{
            this.capital = capital;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
            this.montante = 0;
        }
    }

    // Getters
    public double getCapital() {     return capital; }
    public double getTaxa(){ return taxa; }
    public double getMontante(){ return montante; }
    public Duracao getDuracao(){ return duracao; }

    // Setters
    public void setCapital(double novoCapital) throws ValorInvalidoException{
        if (!ValidarValor.ehPositivo(novoCapital)) throw new ValorInvalidoException();
        else this.capital = novoCapital;
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException { 
        if (!ValidarValor.ehPositivo(novaTaxa)) throw new ValorInvalidoException();
        else this.taxa = novaTaxa; 
    }
    public void setDuracao(Duracao novaDuracao) {
        this.duracao = novaDuracao; 
    }
    public void setTipo(int novoTipo) throws OpcaoInvalidaException {
        this.duracao.setTipo(novoTipo);
    }
    public void setTempo(double novoTempo) throws ValorInvalidoException{
        this.duracao.setTempo(novoTempo);
    }


    public abstract void calcularMontante();
}
