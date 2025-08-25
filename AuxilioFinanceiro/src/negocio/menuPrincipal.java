package negocio;

import negocio.Excecoes.FormatacaoInvalidaException;
import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.TIRImpossivelException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.menuprincipal.menuAmortizacao;
import negocio.menuprincipal.menuDesconto;
import negocio.menuprincipal.menuInvestimento;
import negocio.menuprincipal.menuVPL;
import negocio.menuprincipal.menuVariacaoPreco;

public class menuPrincipal {
    public void inputMenu(int input1, int input2, double valor, double taxa, int numParcelas, int tipo, double tempo, String arrecadacao) throws OpcaoInvalidaException, ValorInvalidoException, FormatacaoInvalidaException, TIRImpossivelException{
        switch(input1){
            case 1: {
                menuInvestimento menu = new menuInvestimento();
                menu.inputMenuInvestimento(input2, valor, taxa, tipo, tempo);
                break;
            }
            case 2: {
                menuDesconto menu = new menuDesconto();
                menu.inputMenuDesconto(input2, valor, taxa, tipo, tempo);
                break;
            }
            case 3: {
                menuVariacaoPreco menu = new menuVariacaoPreco();
                menu.inputMenuVariacaoPreco(input2, valor, taxa);
                break;
            }
            case 4: {
                menuAmortizacao menu = new menuAmortizacao();
                menu.inputMenuAmortizacao(input2, valor, taxa, numParcelas);
                break;
            }
            case 5: {
                menuVPL menu = new menuVPL();
                menu.inputMenuVPL(input2, valor, taxa, tipo, tempo, arrecadacao);
                break;
            }
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
