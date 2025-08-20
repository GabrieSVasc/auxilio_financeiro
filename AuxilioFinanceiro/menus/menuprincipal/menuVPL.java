package menus.menuprincipal;

import Excecoes.OpcaoInvalidaException;
import entidades.ValorPresenteLiquido;

public class menuVPL {
    public double inputMenuVPL(int input2, double custoInicial, double taxa, int tipo, double tempo) throws OpcaoInvalidaException{
        ValorPresenteLiquido objeto = new ValorPresenteLiquido(custoInicial, taxa, tipo, tempo);
        switch (input2){
            case 1:
                return objeto.calcularVPL();
            case 2:
                objeto.calcularTIR();
                return objeto.getTir();
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
