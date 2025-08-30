package negocio;

import negocio.entidades.RetornoInvestimento;
import negocio.entidades.VariacaoDePreco;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

public class NegocioVariacaoPreco {
    public RetornoInvestimento inputMenuVariacaoPreco(int input2, double valor, double taxa) throws OpcaoInvalidaException, ValorInvalidoException{
        VariacaoDePreco preco = new VariacaoDePreco(valor, taxa);
        switch (input2){
            case 1:
                return new RetornoInvestimento(preco.calcularInflacao());
            case 2:
                return new RetornoInvestimento(preco.calcularDeflacao());
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
