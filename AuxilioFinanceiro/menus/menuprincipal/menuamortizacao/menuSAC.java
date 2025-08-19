package menus.menuprincipal.menuamortizacao;

import entidades.Amortizacao;
import entidades.tiposamortizacao.AmortConstante;

public class menuSAC {
    public Amortizacao inputMenuSAC(double montante, double taxa, int numParcelas){
        Amortizacao teste = new AmortConstante(montante, taxa, numParcelas);
        teste.calcularTudo();
        return teste;
    }
}
