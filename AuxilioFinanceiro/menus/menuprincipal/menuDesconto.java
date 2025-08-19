package menus.menuprincipal;

import Excecoes.OpcaoInvalida;
import entidades.Desconto;
import entidades.tiposdesconto.DescontoComposto;
import entidades.tiposdesconto.DescontoSimples;

public class menuDesconto {
    public Desconto inputMenuDesconto(int input2, double montante, double taxa, int tipo, double tempo) throws OpcaoInvalida {
        Desconto descon;
        switch (input2){
            case 1:
                descon = new DescontoSimples(montante, taxa, tipo, tempo);
                break;
            case 2:
                descon = new DescontoComposto(montante, taxa, tipo, tempo);
                break;
            default:
                throw new OpcaoInvalida();
        }
        descon.calcularValorPresente();
        descon.calcularDesconto();
        return descon;
    }
}
