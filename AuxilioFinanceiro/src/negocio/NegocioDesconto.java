package negocio;

import negocio.entidades.Desconto;
import negocio.entidades.RetornoInvestimento;
import negocio.entidades.tiposdesconto.DescontoComposto;
import negocio.entidades.tiposdesconto.DescontoSimples;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Menu que lida com a parte de desconto
 * 
 * @author Divancy Bruno
 */

public class NegocioDesconto {
    public RetornoInvestimento inputMenuDesconto(int input2, double montante, double taxa, int tipo, double tempo) throws OpcaoInvalidaException, ValorInvalidoException {
        Desconto descon;
        switch (input2){
            case 1:
                descon = new DescontoSimples(montante, taxa, tipo, tempo);
                break;
            case 2:
                descon = new DescontoComposto(montante, taxa, tipo, tempo);
                break;
            default:
                throw new OpcaoInvalidaException();
        }
        RetornoInvestimento r = new RetornoInvestimento(descon.calcularValorPresente(), descon.calcularDesconto());
        return r;
    }
}
