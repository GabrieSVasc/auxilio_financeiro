package entidades.tiposinvestimentos;

import Excecoes.OpcaoInvalidaException;
import Excecoes.ValorInvalidoException;
import entidades.Duracao;
import entidades.Investimento;

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
