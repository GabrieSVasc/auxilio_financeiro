package menus.menuprincipal;

import Excecoes.OpcaoInvalida;

public class menuDesconto {
    public void inputMenuDesconto(int input2, double montante, double taxa, int tipo, double tempo,) throws OpcaoInvalida {
        switch (input2){
            case 1:
                
                inputDescontoSimples(input3);
                break;
            case 2:
                inputDescontoComposto(input3);
                break;
            default:
                throw new OpcaoInvalida();
        }
    }
}
