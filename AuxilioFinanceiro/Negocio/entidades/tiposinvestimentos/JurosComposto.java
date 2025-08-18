package entidades.tiposinvestimentos;

import entidades.Duracao;
import entidades.Investimento;

public class JurosComposto extends Investimento {
    public JurosComposto(double capital, double taxa, Duracao duracao){
        super(capital, taxa, duracao);
    }
    public JurosComposto(double capital, double taxa, int tipo, double tempo){
        super(capital, taxa, tipo, tempo);
    }
    public JurosComposto(){
        super();
    }

    @Override
    public void calcularMontante(){
        double tempo = duracao.getTempo();
        this.montante = capital * Math.pow(1+taxa, tempo); 
    }
}
