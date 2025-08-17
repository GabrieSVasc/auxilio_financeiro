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
    public void calcularParcelas(){
        parcela = montante * (Math.pow(1+taxa, numParcelas)*taxa) / (Math.pow(1+taxa, numParcelas) - 1);
    }

    @Override
    public void calcularResto(){
        juros = saldoDevedor*taxa;
        amortizacao = parcela - juros;
    }
}
