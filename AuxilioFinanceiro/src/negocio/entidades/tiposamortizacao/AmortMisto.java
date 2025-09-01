package negocio.entidades.tiposamortizacao;

import negocio.entidades.Amortizacao;
import negocio.exceptions.ValorInvalidoException;

/**
 * Subclasse de amortização, ou seja, é um:
 * 
 * Tipo de amortização, usando o sistema de amortização misto (SAM)
 * 
 * @author Divancy Bruno
*/
public class AmortMisto extends Amortizacao {
    public AmortMisto(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }
 
    /**
     * Sobrescreve o método abstrato calcularTudo da classe abstrata Amortizacao;
     * 
     * Sua função é:
     * Calcular todos os valores importantes através do sistema de amortização misto
     * 
     * Nele tudo é alterado, mais especificamente:
     * Parcela decresce; (Mais lentamente que o constante)
     * Juros decresce;
     * Amortização cresce. (Mais lentamente que o PRICE)
     * 
     * De forma simplória, ele age como uma média entre os outros dois sistemas: amortização francesa (PRICE) e amortização constante.
     * 
     * @throws ValorInvalidoException em caso de uso de valor menor ou igual a 0 na criação dos outros dois tipos de amortização.
     */
    @Override
    public void calcularTudo() throws ValorInvalidoException{
        saldoDevedor.add(montante);
        Amortizacao amort1 = new AmortConstante(montante, taxa*100, numParcelas);
        Amortizacao amort2 = new AmortPrice(montante, taxa*100, numParcelas);

        amort1.calcularTudo();
        amort2.calcularTudo();

        for (int i = 0; i < numParcelas; i++) {
            double novaPar = (amort1.getParcela().get(i) + amort2.getParcela().get(i))/2;
            this.parcela.add(novaPar);

            double novoJur = (amort1.getJuros().get(i)+amort2.getJuros().get(i))/2;
            this.juros.add(novoJur);

            double novaAmo = (amort1.getAmortizacao().get(i)+amort2.getAmortizacao().get(i))/2;
            this.amortizacao.add(novaAmo);

            this.saldoDevedor.add(Math.abs(saldoDevedor.get(i) - amortizacao.get(i)));
        }
    }
}
