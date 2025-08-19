package menus.menuprincipal;

import Excecoes.OpcaoInvalida;

public class menuInvestimento {
    public void inputMenuInvestimento(int input2) throws OpcaoInvalida{
        int input3 = 0;
        switch (input2){
            case 1:
                inputJurosSimples(input3);
                break;
            case 2:
                inputJurosComposto(input3);
                break;
            case 3:
                inputAportesPeriodicos(input3);
                break;
            default:
                throw new OpcaoInvalida();
        }
    }
}
