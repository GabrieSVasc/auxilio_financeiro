package entidades.tiposamortizacao;

import entidades.Amortizacao;

public class amortConstante extends Amortizacao {
    public amortConstante(double montante, double taxa, int numParcelas) {
        super(montante, taxa, numParcelas);
    }
    
    public amortConstante(){
        super();
    }

    @Override
    public void calcularTudo(){
        this.parcela = this.amortizacao + this.juros;
        this.amortizacao = saldoDevedor/numParcelas;
        this.juros = saldoDevedor*taxa;
    }
}
