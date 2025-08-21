package entidades.tiposamortizacao;

import Excecoes.ValorInvalidoException;
import entidades.Amortizacao;

public class AmortPrice extends Amortizacao {
    public AmortPrice(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }

    @Override
    public void calcularTudo(){
        for (int i = 0; i < numParcelas; i++) {
            double novaPar = montante * (Math.pow(1+taxa, numParcelas)*taxa) / (Math.pow(1+taxa, numParcelas) - 1);
            this.parcela.add(novaPar);

            double novoJur = saldoDevedor*taxa;
            this.juros.add(novoJur);
            
            double novaAmo = parcela.get(i) - juros.get(i);
            this.amortizacao.add(novaAmo);

            this.saldoDevedor -= amortizacao.get(i);
        }
    }
}
