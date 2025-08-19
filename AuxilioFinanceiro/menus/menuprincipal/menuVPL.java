package menus.menuprincipal;

import Excecoes.OpcaoInvalida;

public class menuVPL {
    public void inputMenuVPL(int input2) throws OpcaoInvalida{
        int input3 = 0;
        switch (input2){
            case 1:
                inputPadrao(input3);
            case 2:
                inputTIR(input3);
            default:
                throw new OpcaoInvalida();
        }
    }
}
