package entidades;

import Excecoes.ValorInvalidoException;
import utils.ValidarValor;

public abstract  class Amortizacao {
    protected double amortizacao, parcela, juros, saldoDevedor, montante, taxa;
    protected int numParcelas;

    public Amortizacao(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        if (!ValidarValor.ehPositivo(montante) || !ValidarValor.ehPositivo(taxa) || !ValidarValor.ehPositivo(numParcelas)){
            throw new ValorInvalidoException();
        }
        else{
            this.montante = montante;
            this.saldoDevedor = montante;
            this.taxa = taxa/100;
            this.numParcelas = numParcelas;
            this.amortizacao = 0;
            this.parcela = 0;
            this.juros = 0;
        }
    }

    
    // Getters
    public double getAmortizacao(){ return this.amortizacao; }
    public double getParcela(){ return this.parcela; }
    public double getJuros(){ return this.juros; }
    public double getSaldoDevedor(){ return this.saldoDevedor; }
    public double getMontante(){ return this.montante; }
    public double getTaxa(){ return this.taxa; }
    public int getNumParcela(){ return this.numParcelas; }

    // Setters
    public void setMontante(double novoMontante) throws ValorInvalidoException{
        if (!ValidarValor.ehPositivo(novoMontante)) throw new ValorInvalidoException();
        else this.montante = novoMontante; 
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException{
        if (!ValidarValor.ehPositivo(novaTaxa)) throw new ValorInvalidoException();
        else this.taxa = novaTaxa; 
    }
    public void setNumParcela(int novoNumParcela) throws ValorInvalidoException {
        if (!ValidarValor.ehPositivo(novoNumParcela)) throw new ValorInvalidoException();
        else this.numParcelas = novoNumParcela; 
    }

    
    public abstract void calcularTudo();
}
