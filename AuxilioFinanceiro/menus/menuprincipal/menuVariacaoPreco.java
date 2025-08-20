package menus.menuprincipal;

import Excecoes.OpcaoInvalidaException;
import entidades.VariacaoDePreco;

public class menuVariacaoPreco {
    public double inputMenuVariacaoPreco(int input2, double valor, double taxa) throws OpcaoInvalidaException{
        VariacaoDePreco preco = new VariacaoDePreco(valor, taxa);
        switch (input2){
            case 1:
                return preco.calcularInflacao();
            case 2:
                return preco.calcularDeflacao();
            case 3: 
                preco.calcularTaxaReal();
                return preco.getTaxaReal();
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
