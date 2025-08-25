package negocio.entidades;

import java.util.ArrayList;

import negocio.exceptions.ValorInvalidoException;

public abstract  class Amortizacao {
    protected double montante, taxa;
    protected ArrayList<Double> parcela, amortizacao, juros, saldoDevedor;
    protected int numParcelas;

    public Amortizacao(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        if (montante <= 0 || taxa <= 0 || numParcelas <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.montante = montante;
            this.taxa = taxa/100;
            this.numParcelas = numParcelas;
            this.saldoDevedor = new ArrayList<>();
            this.amortizacao = new ArrayList<>();
            this.parcela = new ArrayList<>();
            this.juros = new ArrayList<>();
        }
    }


    // Getters
    public ArrayList<Double> getAmortizacao(){ return this.amortizacao; }
    public ArrayList<Double> getParcela(){ return this.parcela; }
    public ArrayList<Double> getJuros(){ return this.juros; }
    public ArrayList<Double> getSaldoDevedor(){ return this.saldoDevedor; }
    public double getMontante(){ return this.montante; }
    public double getTaxa(){ return this.taxa; }
    public int getNumParcela(){ return this.numParcelas; }

    // Setters
    public void setMontante(double novoMontante) throws ValorInvalidoException{
        if (novoMontante <= 0) throw new ValorInvalidoException();
        else this.montante = novoMontante; 
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException{
        if (novaTaxa <= 0) throw new ValorInvalidoException();
        else this.taxa = novaTaxa; 
    }
    public void setNumParcela(int novoNumParcela) throws ValorInvalidoException {
        if (novoNumParcela <= 0) throw new ValorInvalidoException();
        else this.numParcelas = novoNumParcela; 
    }

    
    public abstract void calcularTudo() throws ValorInvalidoException;
}
