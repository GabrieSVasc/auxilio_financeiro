package entidades.tiposamortizacao;

import entidades.Amortizacao;

public class AmortMisto extends Amortizacao {
    public AmortMisto(double montante, double taxa, int numParcelas) {
        super(montante, taxa, numParcelas);
    }

    public AmortMisto() {
        super();
    }
    
    @Override
    public void calcularTudo(){
        AmortConstante amort1 = new AmortConstante(montante, taxa, numParcelas);
        AmortPrice amort2 = new AmortPrice(montante, taxa, numParcelas);
        amort1.calcularTudo();
        amort2.calcularTudo();

        this.parcela = (amort1.getParcela()+amort2.getParcela())/2;
        this.amortizacao = (amort1.getAmortizacao()+amort2.getAmortizacao())/2;
        this.juros = (amort1.getJuros()+amort2.getJuros())/2;
    }
}
