package menus.menuprincipal;

import Excecoes.OpcaoInvalida;
import entidades.Amortizacao;
import entidades.tiposamortizacao.AmortPrice;
import menus.menuprincipal.menuamortizacao.menuPRICE;
import menus.menuprincipal.menuamortizacao.menuSAC;
import menus.menuprincipal.menuamortizacao.menuSAM;

public class menuAmortizacao {
    public void inputMenuAmortizacao(int input2, double montante, double taxa, int numParcelas) throws OpcaoInvalida {
        Amortizacao teste;
        switch (input2){
            case 1: {
                teste = new AmortPrice(montante, taxa, numParcelas);
                teste.calcularTudo();
                break;
            }
            case 2: {
                menuSAC menu = new menuSAC();
                menu.inputMenuSAC(montante, taxa, numParcelas);
                break;
            }
            case 3: {
                menuSAM menu = new menuSAM();
                menu.inputMenuSAM(montante, taxa, numParcelas);
                break;
            }
            default:
                throw new OpcaoInvalida();
        }
        teste.calcularTudo();
        return teste;
    }
}
