package entidades.tiposamortizacao;

import Excecoes.ValorInvalidoException;
import entidades.Amortizacao;

public class AmortConstante extends Amortizacao {
    public AmortConstante(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }

    @Override
    public void calcularTudo(){
        this.parcela = this.amortizacao + this.juros;
        this.amortizacao = saldoDevedor/numParcelas;
        this.juros = saldoDevedor*taxa;
    }
}
