package negocio;

import negocio.entidades.RetornoInvestimento;
import negocio.entidades.ValorPresenteLiquido;
import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.TIRImpossivelException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Menu que lida com o Valor Presente Liquido
 * 
 * @author Divancy Bruno
 */

public class NegocioVPL {
    public RetornoInvestimento inputMenuVPL(int input2, double custoInicial, double taxa, int tipo, double tempo, String arrecadacao) throws OpcaoInvalidaException, ValorInvalidoException, FormatacaoInvalidaException, TIRImpossivelException{
        ValorPresenteLiquido objeto = new ValorPresenteLiquido(custoInicial, taxa, tipo, tempo, arrecadacao);
        switch (input2){
            case 1:
                return new RetornoInvestimento(objeto.calcularVPL());
            case 2:
            	return new RetornoInvestimento(objeto.calcularTIR());
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
