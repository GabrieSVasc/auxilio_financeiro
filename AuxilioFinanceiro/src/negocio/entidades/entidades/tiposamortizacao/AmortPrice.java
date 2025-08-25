package negocio.entidades.entidades.tiposamortizacao;

import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.entidades.Amortizacao;

public class AmortPrice extends Amortizacao {
    public AmortPrice(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }

    @Override
    public void calcularTudo(){
        this.saldoDevedor.add(montante);
        for (int i = 0; i < numParcelas; i++) {
            double novaPar = montante * (Math.pow(1+taxa, numParcelas)*taxa) / (Math.pow(1+taxa, numParcelas) - 1);
            this.parcela.add(novaPar);

            double novoJur = saldoDevedor.get(i)*taxa;
            this.juros.add(novoJur);

            double novaAmo = parcela.get(i) - juros.get(i);
            this.amortizacao.add(novaAmo);

            this.saldoDevedor.add(Math.abs(saldoDevedor.get(i) - amortizacao.get(i)));
        }
    }
}
