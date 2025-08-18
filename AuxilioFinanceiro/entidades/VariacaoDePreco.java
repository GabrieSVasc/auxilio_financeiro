package entidades;
public class VariacaoDePreco {
    private double valor, taxa;

    public VariacaoDePreco(double valor, double taxa) {
        this.valor = valor;
        this.taxa = taxa;
    }
    public VariacaoDePreco() {
        this.valor = 0;
        this.taxa = 0;
    }

    public double getValor() {
        return valor;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setValor(double novoValor) {
        this.valor = novoValor;
    }

    public void setTaxa(double novaTaxa) {
        this.taxa = novaTaxa;
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
