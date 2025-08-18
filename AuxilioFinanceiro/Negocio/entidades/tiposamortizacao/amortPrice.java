package entidades.tiposamortizacao;

import entidades.Amortizacao;

public class amortPrice extends Amortizacao {
    public amortPrice(double montante, double taxa, int numParcelas) {
        super(montante, taxa, numParcelas);
    }

    public amortPrice(){
        super();
    }


    @Override
    public void calcularTudo(){
        this.parcela = montante * (Math.pow(1+taxa, numParcelas)*taxa) / (Math.pow(1+taxa, numParcelas) - 1);
        this.juros = saldoDevedor*taxa;
        this.amortizacao = parcela - juros;
    }
}
