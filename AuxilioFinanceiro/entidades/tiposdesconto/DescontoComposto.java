package entidades.tiposdesconto;

import entidades.Desconto;
import entidades.Duracao;

public class DescontoComposto extends Desconto {
    public DescontoComposto(double valorNominal, double taxa, Duracao duracao){
        super(valorNominal, taxa, duracao);
    }
    public DescontoComposto(double valorNominal, double taxa, int tipo, double tempo){
        super(valorNominal, taxa, tipo, tempo);
    }
    public DescontoComposto(){
        super();
    }

    @Override
    public void calcularValorAtual(){
        double a = this.getTaxa();
        if (a >= 1) a = a/100;
        this.valorAtual = this.valorNominal*(1 - a*this.duracao.getTempo());
    }

    @Override
    public void calcularDesconto(){
        calcularValorAtual();
        this.desconto = this.valorNominal - this.valorAtual;
    }
}
