package entidades.tiposamortizacao;

import Excecoes.ValorInvalidoException;
import entidades.Amortizacao;

public class AmortMisto extends Amortizacao {
    public AmortMisto(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }
 
    @Override
    public void calcularTudo() throws ValorInvalidoException{
            Amortizacao amort1 = new AmortConstante(montante, taxa, numParcelas);
            Amortizacao amort2 = new AmortPrice(montante, taxa, numParcelas);

            amort1.calcularTudo();
            amort2.calcularTudo();

            for (int i = 0; i < numParcelas; i++) {
                double novaPar = (amort1.getParcela().get(i) + amort2.getParcela().get(i))/2;
                this.parcela.add(novaPar);

                double novoJur = (amort1.getJuros().get(i)+amort2.getJuros().get(i))/2;
                this.juros.add(novoJur);

                double novaAmo = (amort1.getAmortizacao().get(i)+amort2.getAmortizacao().get(i))/2;
                this.amortizacao.add(novaAmo);

                this.saldoDevedor -= amortizacao.get(i);
            }
    }
}
