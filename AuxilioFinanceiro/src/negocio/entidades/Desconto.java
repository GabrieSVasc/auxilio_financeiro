package negocio.entidades;

import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

public abstract class Desconto {
    protected double desconto, valorNominal, valorPresente, taxa;
    protected Duracao duracao;

    public Desconto(double valorNominal, double taxa, Duracao duracao) throws ValorInvalidoException{
        if (valorNominal <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.valorNominal = valorNominal;
            this.taxa = taxa/100;
            this.duracao = duracao;
            this.desconto = 0;
            this.valorPresente = 0;
        }
    }
    public Desconto(double valorNominal, double taxa, int tipo, double tempo) throws  ValorInvalidoException, OpcaoInvalidaException{
        if (valorNominal <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.valorNominal = valorNominal;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
            this.desconto = 0;
            this.valorPresente = 0;
        }
    }

    
    // Getters
    public double getValorNominal() { return valorNominal; }
    public double getValorPresente(){ return valorPresente; }
    public double getTaxa(){ return taxa; }
    public double getDesconto(){ return desconto; }
    public Duracao getDuracao(){ return duracao; }

    // Setters
    public void setValorNominal(double novoValorNominal) throws ValorInvalidoException{
        if (novoValorNominal <= 0) throw new ValorInvalidoException();
        else this.valorNominal = novoValorNominal; 
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
    
    
    public abstract double calcularValorPresente();
    
    public abstract double calcularDesconto();
}