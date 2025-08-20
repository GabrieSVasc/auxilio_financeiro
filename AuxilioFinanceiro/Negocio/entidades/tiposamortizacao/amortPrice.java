package entidades.tiposamortizacao;

import Excecoes.ValorInvalidoException;
import entidades.Amortizacao;

public class AmortPrice extends Amortizacao {
    public AmortPrice(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }

    @Override
    public void calcularTudo(){
        this.parcela = montante * (Math.pow(1+taxa, numParcelas)*taxa) / (Math.pow(1+taxa, numParcelas) - 1);
        this.juros = saldoDevedor*taxa;
        this.amortizacao = parcela - juros;
    }
}
