package entidades.tiposdesconto;

import entidades.Duracao;
import entidades.Desconto;

public class DescontoSimples extends Desconto {
    public DescontoSimples(double valorNominal, double taxa, Duracao duracao){
        super(valorNominal, taxa, duracao);
    }
    public DescontoSimples(double valorNominal, double taxa, int tipo, double tempo){
        super(valorNominal, taxa, tipo, tempo);
    }
    public DescontoSimples(){
        super();
    }

    @Override
    public void calcularValorPresente(){
        double a = this.getTaxa();
        if (a >= 1) a = a/100;
        this.valorPresente = this.valorNominal*(1 - a*this.duracao.getTempo());
    }
    
    @Override
    public void calcularDesconto(){
        calcularValorPresente();
        this.desconto = this.valorNominal - this.valorPresente;
    }
}