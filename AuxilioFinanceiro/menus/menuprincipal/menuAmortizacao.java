package menus.menuprincipal;

import Excecoes.OpcaoInvalidaException;
import entidades.Amortizacao;
import entidades.tiposamortizacao.AmortConstante;
import entidades.tiposamortizacao.AmortMisto;
import entidades.tiposamortizacao.AmortPrice;

public class menuAmortizacao {
    public Amortizacao inputMenuAmortizacao(int input2, double montante, double taxa, int numParcelas) throws OpcaoInvalidaException {
        Amortizacao amort;
        switch (input2){
            case 1: {
                amort = new AmortPrice(montante, taxa, numParcelas);
                break;
            }
            case 2: {
                amort = new AmortConstante(montante, taxa, numParcelas);
                break;
            }
            case 3: {
                amort = new AmortMisto(montante, taxa, numParcelas);
                break;
            }
            default:
                throw new OpcaoInvalidaException();
        }
        amort.calcularTudo();
        return amort;
    }
}
