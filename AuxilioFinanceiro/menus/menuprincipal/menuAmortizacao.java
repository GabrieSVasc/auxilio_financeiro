package menus.menuprincipal;

import Excecoes.OpcaoInvalida;
import entidades.Amortizacao;
import entidades.tiposamortizacao.AmortConstante;
import entidades.tiposamortizacao.AmortMisto;
import entidades.tiposamortizacao.AmortPrice;

public class menuAmortizacao {
    public Amortizacao inputMenuAmortizacao(int input2, double montante, double taxa, int numParcelas) throws OpcaoInvalida {
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
                throw new OpcaoInvalida();
        }
        amort.calcularTudo();
        return amort;
    }
}
