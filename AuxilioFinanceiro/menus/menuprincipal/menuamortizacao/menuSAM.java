package menus.menuprincipal.menuamortizacao;

import entidades.Amortizacao;
import entidades.tiposamortizacao.AmortMisto;

public class menuSAM {
    public Amortizacao inputMenuSAM(double montante, double taxa, int numParcelas){
        Amortizacao teste = new AmortMisto(montante, taxa, numParcelas);
        teste.calcularTudo();
        return teste;
    }
}
