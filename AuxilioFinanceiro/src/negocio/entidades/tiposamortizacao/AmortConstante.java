package negocio.entidades.tiposamortizacao;

import negocio.entidades.Amortizacao;
import negocio.exceptions.ValorInvalidoException;

/**
 * Subclasse de amortização, ou seja, é um:
 * 
 * Tipo de amortização, usando o sistema de amortização constante (SAC)
 * 
 * @author Divancy Bruno
*/
public class AmortConstante extends Amortizacao {
    public AmortConstante(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }

    /**
     * Sobrescreve o método abstrato calcularTudo da classe abstrata Amortizacao;
     * 
     * Sua função é:
     * Calcular todos os valores importantes através do sistema de amortização constante
     * 
     * Nele a amortização é constante, ou seja, não muda.
     * Enquanto a parcela e os juros decrescem
     * 
     */
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
