package negocio.entidades;

import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

public abstract class Investimento {
    protected double capital, taxa; 
    protected Duracao duracao;

    public Investimento(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        if (capital <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.capital = capital;
            this.taxa = taxa/100;
            this.duracao = duracao;
        }
    }
    public Investimento(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        if (capital <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.capital = capital;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
        }
    }

    // Getters
    public double getCapital() {     return capital; }
    public double getTaxa(){ return taxa; }
    public Duracao getDuracao(){ return duracao; }

    // Setters
    public void setCapital(double novoCapital) throws ValorInvalidoException{
        if (novoCapital <= 0) throw new ValorInvalidoException();
        else this.capital = novoCapital;
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException { 
        if (novaTaxa <= 0) throw new ValorInvalidoException();
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


    public abstract double calcularMontante();
}
