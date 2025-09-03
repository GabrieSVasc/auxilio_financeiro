package negocio;

import negocio.entidades.RetornoInvestimento;
import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.TIRImpossivelException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Menu que lida com toda a parte de investimentos
 * 
 * @author Divancy Bruno
 */

public class NegocioInvestimentosPrincipal {
    public RetornoInvestimento inputMenu(int input1, int input2, double valor, double taxa, int numParcelas, int tipo, double tempo, String arrecadacao) throws OpcaoInvalidaException, ValorInvalidoException, FormatacaoInvalidaException, TIRImpossivelException{
        switch(input1){
            case 1: {
                NegocioInvestimento menu = new NegocioInvestimento();
                return menu.inputMenuInvestimento(input2, valor, taxa, tipo, tempo);
            }
            case 2: {
                NegocioDesconto menu = new NegocioDesconto();
                return menu.inputMenuDesconto(input2, valor, taxa, tipo, tempo);
            }
            case 3: {
                NegocioVariacaoPreco menu = new NegocioVariacaoPreco();
                return menu.inputMenuVariacaoPreco(input2, valor, taxa);
            }
            case 4: {
                NegocioAmortizacao menu = new NegocioAmortizacao();
                return menu.inputMenuAmortizacao(input2, valor, taxa, numParcelas);
            }
            case 5: {
                NegocioVPL menu = new NegocioVPL();
                return menu.inputMenuVPL(input2, valor, taxa, tipo, tempo, arrecadacao);
            }
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
