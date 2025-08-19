package menus.menuprincipal;

import Excecoes.OpcaoInvalida;

public class menuVariacaoPreco {
    public void inputMenuVariacaoPreco(int input2) throws OpcaoInvalida{
        int input3 = 0;
        switch (input2){
            case 1:
                inputMenuInflacao(input3);
            case 2:
                inputMenuDeflacao(input3);
            case 3:
                throw new OpcaoInvalida();
        }
    }
}
