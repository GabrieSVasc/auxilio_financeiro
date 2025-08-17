package entidades.tiposamortizacao;

import entidades.Amortizacao;

public class amortMisto extends Amortizacao {
    public amortMisto(double montante, double taxa, int numParcelas) {
        super(montante, taxa, numParcelas);
    }

    public amortMisto() {
        super();
    }
    
}
