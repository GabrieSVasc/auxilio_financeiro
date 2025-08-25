package negocio.menuprincipal;

import negocio.Excecoes.FormatacaoInvalidaException;
import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.TIRImpossivelException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.entidades.ValorPresenteLiquido;

public class menuVPL {
    public double inputMenuVPL(int input2, double custoInicial, double taxa, int tipo, double tempo, String arrecadacao) throws OpcaoInvalidaException, ValorInvalidoException, FormatacaoInvalidaException, TIRImpossivelException{
        ValorPresenteLiquido objeto = new ValorPresenteLiquido(custoInicial, taxa, tipo, tempo, arrecadacao);
        switch (input2){
            case 1:
                return objeto.calcularVPL();
            case 2:
            	return objeto.calcularTIR();
            default:
                throw new OpcaoInvalidaException();
        }
    }
}
