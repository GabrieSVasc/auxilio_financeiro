package negocio.menuprincipal;

import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.entidades.Investimento;
import negocio.entidades.entidades.tiposinvestimentos.AportesPeriodicos;
import negocio.entidades.entidades.tiposinvestimentos.JurosComposto;
import negocio.entidades.entidades.tiposinvestimentos.JurosSimples;

public class menuInvestimento {
    public Investimento inputMenuInvestimento(int input2, double montante, double taxa, int tipo, double tempo) throws OpcaoInvalidaException, ValorInvalidoException{
        Investimento invest;
        switch (input2){
            case 1:
                invest = new JurosSimples(taxa, taxa, tipo, tempo);
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
        invest.calcularMontante();
        return invest;
    }
}
