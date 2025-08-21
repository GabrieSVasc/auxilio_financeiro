package entidades;

import Excecoes.ValorInvalidoException;

public class VariacaoDePreco {
    private double valor, taxa, taxaReal, taxaInflacao;

    public VariacaoDePreco(double valor, double taxa) throws ValorInvalidoException {
        if (valor <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.valor = valor;
            this.taxa = taxa;
            this.taxaReal = 0;
        }
    }

    // Getters
    public double getValor() {
        return valor;
    }
    public double getTaxa() {
        return taxa;
    }
    public double getTaxaReal(){
        return taxaReal;
    }
    public double getTaxaInflacao(){
        return taxaInflacao;
    }

    // Setters
    public void setValor(double novoValor) throws ValorInvalidoException {
        if (novoValor <= 0) throw new ValorInvalidoException(); 
        else this.valor = novoValor;
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException {
        if (novaTaxa <= 0) throw new ValorInvalidoException(); 
        else this.valor = novaTaxa;
    }
     

    public double calcularDeflacao(){
        double resultado = valor + (valor*taxaInflacao);
        return resultado;
    }
    public double calcularInflacao(){
        double resultado = valor - (valor*taxaInflacao);
        return resultado;
    }
    public void calcularTaxaReal(){
        this.taxaReal = ((1+this.taxa)/(1+this.taxaInflacao)) - 1;
    }
}
