package entidades;
public class VariacaoDePreco {
    private double valor, taxa, taxaReal, taxaInflacao;

    public VariacaoDePreco(double valor, double taxa) {
        this.valor = valor;
        this.taxa = taxa;
        this.taxaReal = 0;
    }
    public VariacaoDePreco() {
        this.valor = 0;
        this.taxa = 0;
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
    public void setValor(double novoValor) {
        this.valor = novoValor;
    }
    public void setTaxa(double novaTaxa) {
        this.taxa = novaTaxa;
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
        // Pegar o IPCA no momento do uso do programa?
        this.taxaReal = ((1+this.taxa)/(1+this.taxaInflacao)) - 1;
    }
}
