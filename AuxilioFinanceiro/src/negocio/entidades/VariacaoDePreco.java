package negocio.entidades;

import negocio.exceptions.ValorInvalidoException;

/**
 * Método para calcular a variação de um preço, ou seja, calcular a deflação ou inflação de algo.
 * 
 * Inflação seria um produto se tornar mais caro com o passar do tempo, normalmente sinalizando uma desvalorização da moeda local.
 * Deflação seria um produto se tornar mais barato com o passar do tempo, normalmente sinalizando uma valorização da moeda local.
 * 
 * @author Divancy Bruno
 */
public class VariacaoDePreco {
    private double valor, taxa;

    /**
     * Construtor da classe VariacaoDePreco.
     * 
     * @param valor, o valor a sofrer a variação de preço
     * @param taxa, a taxa de variação desse valor.
     * 
     * @throws ValorInvalidoException, caso os valores informados sejam iguais ou menores que 0.
     */
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
     

    /** Calcula a deflação de um valor, baseado na taxa informada 
    */
    public double calcularDeflacao(){
        double resultado = valor + (valor*taxa);
        return resultado;
    }
    /** Calcula a inflação de um valor, baseado na taxa informada 
    */
    public double calcularInflacao(){
        double resultado = valor - (valor*taxa);
        return resultado;
    }
}
