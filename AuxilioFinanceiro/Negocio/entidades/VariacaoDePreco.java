package entidades;

import Excecoes.ValorInvalidoException;

public class VariacaoDePreco {
    private double valor, taxa;

    public VariacaoDePreco(double valor, double taxa) throws ValorInvalidoException {
        if (valor <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.valor = valor;
            this.taxa = taxa/100;
        }
    }

    
    // Getters
    public double getValor() {
        return valor;
    }
    public double getTaxa() {
        return taxa;
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
        double resultado = valor + (valor*taxa);
        return resultado;
    }
    public double calcularInflacao(){
        double resultado = valor - (valor*taxa);
        return resultado;
    }
}
