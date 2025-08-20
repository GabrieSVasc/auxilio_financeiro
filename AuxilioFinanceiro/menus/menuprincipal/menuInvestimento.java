package menus.menuprincipal;

import Excecoes.OpcaoInvalidaException;
import entidades.Investimento;
import entidades.tiposinvestimentos.AportesPeriodicos;
import entidades.tiposinvestimentos.JurosComposto;
import entidades.tiposinvestimentos.JurosSimples;

public class menuInvestimento {
    public Investimento inputMenuInvestimento(int input2, double montante, double taxa, int tipo, double tempo) throws OpcaoInvalidaException{
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
