package negocio.entidades.tiposamortizacao;

import negocio.entidades.Amortizacao;
import negocio.exceptions.ValorInvalidoException;

public class AmortConstante extends Amortizacao {
    public AmortConstante(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }

    @Override
    public void calcularTudo(){
        this.saldoDevedor.add(montante);
        double novaAmo = saldoDevedor.get(0)/numParcelas;
        
        for (int i = 0; i < numParcelas; i++) {
            this.amortizacao.add(novaAmo);

            double novoJur = saldoDevedor.get(i)*taxa;
            this.juros.add(novoJur);

            double novaPar = this.amortizacao.get(0) + this.juros.get(i);
            this.parcela.add(novaPar);

            this.saldoDevedor.add(Math.abs(saldoDevedor.get(i) - amortizacao.get(i)));
        }
    }
}
