package negocio.entidades.tiposamortizacao;

import negocio.entidades.Amortizacao;
import negocio.exceptions.ValorInvalidoException;

/**
 * Subclasse de amortização, ou seja, é um:
 * 
 * Tipo de amortização, usando o sistema de amortização francesa (PRICE)
 * 
 * @author Divancy Bruno
*/
public class AmortPrice extends Amortizacao {
    public AmortPrice(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }

    /**
     * Sobrescreve o método abstrato calcularTudo da classe abstrata Amortizacao;
     * 
     * Sua função é:
     * Calcular todos os valores importantes através do sistema PRICE
     * 
     * Nele as parcelas são constantes;
     * Enquanto os juros decrescem;
     * E a amortização cresce.
     */
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
