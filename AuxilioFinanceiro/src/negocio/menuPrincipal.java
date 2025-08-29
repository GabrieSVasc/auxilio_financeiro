package negocio;

import negocio.entidades.RetornoInvestimento;
import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.TIRImpossivelException;
import negocio.exceptions.ValorInvalidoException;

public class menuPrincipal {
    public RetornoInvestimento inputMenu(int input1, int input2, double valor, double taxa, int numParcelas, int tipo, double tempo, String arrecadacao) throws OpcaoInvalidaException, ValorInvalidoException, FormatacaoInvalidaException, TIRImpossivelException{
        switch(input1){
            case 1: {
                menuInvestimento menu = new menuInvestimento();
                return menu.inputMenuInvestimento(input2, valor, taxa, tipo, tempo);
            }
            case 2: {
                menuDesconto menu = new menuDesconto();
                return menu.inputMenuDesconto(input2, valor, taxa, tipo, tempo);
            }
            case 3: {
                menuVariacaoPreco menu = new menuVariacaoPreco();
                return menu.inputMenuVariacaoPreco(input2, valor, taxa);
            }
            case 4: {
                menuAmortizacao menu = new menuAmortizacao();
                return menu.inputMenuAmortizacao(input2, valor, taxa, numParcelas);
            }
            case 5: {
                menuVPL menu = new menuVPL();
                return menu.inputMenuVPL(input2, valor, taxa, tipo, tempo, arrecadacao);
            }
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
