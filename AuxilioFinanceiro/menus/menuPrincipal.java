package menus;

import Excecoes.OpcaoInvalida;
import menus.menuprincipal.menuAmortizacao;
import menus.menuprincipal.menuDesconto;
import menus.menuprincipal.menuInvestimento;
import menus.menuprincipal.menuVPL;
import menus.menuprincipal.menuVariacaoPreco;

public class menuPrincipal {
    public void inputMenu(int input1, int input2, double montante, double taxa, int numParcelas) throws OpcaoInvalida{
        switch(input1){
            case 1: {
                menuInvestimento menu = new menuInvestimento();
                menu.inputMenuInvestimento(input2);
                break;
            }
            case 2: {
                menuDesconto menu = new menuDesconto();
                menu.inputMenuDesconto(input2);
                break;
            }
            case 3: {
                menuVariacaoPreco menu = new menuVariacaoPreco();
                menu.inputMenuVariacaoPreco(input2);
                break;
            }
            case 4: {
                menuAmortizacao menu = new menuAmortizacao();
                menu.inputMenuAmortizacao(input2, montante, taxa, numParcelas);
                break;
            }
            case 5: {
                menuVPL menu = new menuVPL();
                menu.inputMenuVPL(input2);
                break;
            }
            default:
                throw new OpcaoInvalida();
        }
    }
}
