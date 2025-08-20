package entidades.tiposamortizacao;

import Excecoes.ValorInvalidoException;
import entidades.Amortizacao;

public class AmortMisto extends Amortizacao {
    public AmortMisto(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        super(montante, taxa, numParcelas);
    }
 
    @Override
    public void calcularTudo(){
        AmortConstante amort1 = null;
        AmortPrice amort2 = null;

        try {
            amort1 = new AmortConstante(montante, taxa, numParcelas);
            amort2 = new AmortPrice(montante, taxa, numParcelas);
        } catch (ValorInvalidoException e) {
            e.printStackTrace();
        }
        if (amort1 != null && amort2 != null){
            amort1.calcularTudo();
            amort2.calcularTudo();

            this.parcela = (amort1.getParcela()+amort2.getParcela())/2;
            this.amortizacao = (amort1.getAmortizacao()+amort2.getAmortizacao())/2;
            this.juros = (amort1.getJuros()+amort2.getJuros())/2;
        }
        else System.out.println("NUM CRIOU");
    }
}
