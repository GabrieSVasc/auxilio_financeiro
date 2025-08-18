package entidades.tiposamortizacao;

import entidades.Amortizacao;

public class amortMisto extends Amortizacao {
    public amortMisto(double montante, double taxa, int numParcelas) {
        super(montante, taxa, numParcelas);
    }

    public amortMisto() {
        super();
    }
    
    @Override
    public void calcularParcelas(){
        amortConstante amort1 = new amortConstante(montante, taxa, numParcelas);
        amortPrice amort2 = new amortPrice(montante, taxa, numParcelas);
        amort1.calcularResto();
        amort1.calcularParcelas();
        amort2.calcularParcelas();

        this.parcela = (amort1.getParcela()+amort2.getParcela())/2;
    }

    @Override
    public void calcularResto(){
        amortConstante amort1 = new amortConstante(montante, taxa, numParcelas);
        amortPrice amort2 = new amortPrice(montante, taxa, numParcelas);
        amort1.calcularResto();
        amort2.calcularResto();

        this.amortizacao = (amort1.getAmortizacao()+amort2.getAmortizacao())/2;
        this.juros = (amort1.getJuros()+amort2.getJuros())/2;
    }
}
