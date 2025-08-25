package negocio.menuprincipal;

import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.entidades.Desconto;
import negocio.entidades.entidades.tiposdesconto.DescontoComposto;
import negocio.entidades.entidades.tiposdesconto.DescontoSimples;

public class menuDesconto {
    public Desconto inputMenuDesconto(int input2, double montante, double taxa, int tipo, double tempo) throws OpcaoInvalidaException, ValorInvalidoException {
        Desconto descon;
        switch (input2){
            case 1:
                descon = new DescontoSimples(montante, taxa, tipo, tempo);
                break;
            case 2:
                descon = new DescontoComposto(montante, taxa, tipo, tempo);
                break;
            default:
                throw new OpcaoInvalidaException();
        }
        descon.calcularValorPresente();
        descon.calcularDesconto();
        return descon;
    }
}
