package negocio.entidades.entidades.tiposinvestimentos;

import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.entidades.Duracao;
import negocio.entidades.entidades.Investimento;

public class AportesPeriodicos extends Investimento {
    public AportesPeriodicos(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(capital, taxa, duracao);
    }
    public AportesPeriodicos(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(capital, taxa, tipo, tempo);
    }

    @Override
    public double calcularMontante(){
        double tempo = duracao.getTempo();
        double montante = (1 + taxa)*capital * (Math.pow(1+taxa, tempo) - 1)/taxa;

        return montante;
    }
}

