package negocio.entidades.tiposinvestimentos;

import negocio.entidades.Duracao;
import negocio.entidades.Investimento;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

public class JurosComposto extends Investimento {
    public JurosComposto(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(capital, taxa, duracao);
    }
    public JurosComposto(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(capital, taxa, tipo, tempo);
    }

    @Override
    public double calcularMontante(){
        double tempo = duracao.getTempo();
        double montante = capital * Math.pow(1+taxa, tempo); 
        return montante;
    }
}
