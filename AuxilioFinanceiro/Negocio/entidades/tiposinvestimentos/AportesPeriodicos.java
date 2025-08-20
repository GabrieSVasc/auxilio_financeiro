package entidades.tiposinvestimentos;

import Excecoes.OpcaoInvalidaException;
import Excecoes.ValorInvalidoException;
import entidades.Duracao;
import entidades.Investimento;

public class AportesPeriodicos extends Investimento {
    public AportesPeriodicos(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(capital, taxa, duracao);
    }
    public AportesPeriodicos(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(capital, taxa, tipo, tempo);
    }

    @Override
    public void calcularMontante(){
        double tempo = duracao.getTempo();
        this.montante = (1 + taxa)*capital * (Math.pow(1+taxa, tempo) - 1)/taxa;
    }
}

