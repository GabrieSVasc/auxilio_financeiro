package entidades.tiposinvestimentos;

import entidades.Duracao;
import entidades.Investimento;

public class JurosSimples extends Investimento {
    public JurosSimples(double capital, double taxa, Duracao duracao){
        super(capital, taxa, duracao);
    }
    public JurosSimples(double capital, double taxa, int tipo, double tempo){
        super(capital, taxa, tipo, tempo);
    }
    public JurosSimples(){
        super();
    }

    @Override
    public void calcularMontante(){
        double tempo = duracao.getTempo();
        this.montante = capital * (1 + taxa * tempo);
    }
}
