package entidades.tiposamortizacao;

import entidades.Amortizacao;

public class AmortConstante extends Amortizacao {
    public AmortConstante(double montante, double taxa, int numParcelas) {
        super(montante, taxa, numParcelas);
    }
    
    public AmortConstante(){
        super();
    }

    @Override
    public void calcularTudo(){
        this.parcela = this.amortizacao + this.juros;
        this.amortizacao = saldoDevedor/numParcelas;
        this.juros = saldoDevedor*taxa;
    }
}
