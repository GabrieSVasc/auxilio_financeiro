package negocio.menuprincipal;

import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.entidades.VariacaoDePreco;

public class menuVariacaoPreco {
    public double inputMenuVariacaoPreco(int input2, double valor, double taxa) throws OpcaoInvalidaException, ValorInvalidoException{
        VariacaoDePreco preco = new VariacaoDePreco(valor, taxa);
        switch (input2){
            case 1:
                return preco.calcularInflacao();
            case 2:
                return preco.calcularDeflacao();
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
