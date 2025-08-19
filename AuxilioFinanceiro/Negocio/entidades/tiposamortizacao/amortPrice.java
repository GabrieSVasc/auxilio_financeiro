package entidades.tiposamortizacao;

import entidades.Amortizacao;

public class AmortPrice extends Amortizacao {
    public AmortPrice(double montante, double taxa, int numParcelas) {
        super(montante, taxa, numParcelas);
    }

    public AmortPrice(){
        super();
    }


    @Override
    public void calcularTudo(){
        this.parcela = montante * (Math.pow(1+taxa, numParcelas)*taxa) / (Math.pow(1+taxa, numParcelas) - 1);
        this.juros = saldoDevedor*taxa;
        this.amortizacao = parcela - juros;
    }
}
