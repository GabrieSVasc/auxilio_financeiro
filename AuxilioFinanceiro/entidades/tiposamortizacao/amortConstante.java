package entidades.tiposamortizacao;

import entidades.Amortizacao;

public class amortConstante extends Amortizacao {
    public amortConstante(double montante, double taxa, int numParcelas) {
        super(montante, taxa, numParcelas);
    }
    public amortConstante(){
        super();
    }
    
}
