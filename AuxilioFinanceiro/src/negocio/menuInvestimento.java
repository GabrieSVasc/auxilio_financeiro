package negocio;

import negocio.entidades.Investimento;
import negocio.entidades.RetornoInvestimento;
import negocio.entidades.tiposinvestimentos.AportesPeriodicos;
import negocio.entidades.tiposinvestimentos.JurosComposto;
import negocio.entidades.tiposinvestimentos.JurosSimples;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

public class menuInvestimento {
    public RetornoInvestimento inputMenuInvestimento(int input2, double montante, double taxa, int tipo, double tempo) throws OpcaoInvalidaException, ValorInvalidoException{
        Investimento invest;
        switch (input2){
            case 1:
                invest = new JurosSimples(montante, taxa, tipo, tempo);
                break;
            case 2:
                invest = new JurosComposto(montante, taxa, tipo, tempo);
                break;
            case 3:
                invest = new AportesPeriodicos(montante, taxa, tipo, tempo);
                break;
            default:
                throw new OpcaoInvalidaException();
        }
        RetornoInvestimento r = new RetornoInvestimento(invest.calcularMontante());
        return r;
    }
}
