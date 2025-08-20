package menus.menuprincipal;

import Excecoes.OpcaoInvalidaException;
import entidades.Desconto;
import entidades.tiposdesconto.DescontoComposto;
import entidades.tiposdesconto.DescontoSimples;

public class menuDesconto {
    public Desconto inputMenuDesconto(int input2, double montante, double taxa, int tipo, double tempo) throws OpcaoInvalidaException {
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
