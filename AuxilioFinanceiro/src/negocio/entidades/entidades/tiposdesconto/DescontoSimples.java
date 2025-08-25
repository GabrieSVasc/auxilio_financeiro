package negocio.entidades.entidades.tiposdesconto;

import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.entidades.Desconto;
import negocio.entidades.entidades.Duracao;

public class DescontoSimples extends Desconto {
    public DescontoSimples(double valorNominal, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(valorNominal, taxa, duracao);
    }
    public DescontoSimples(double valorNominal, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(valorNominal, taxa, tipo, tempo);
    }

    @Override
    public void calcularValorPresente(){
        this.valorPresente = this.valorNominal*(1 - taxa*this.duracao.getTempo());
    }
    
    @Override
    public void calcularDesconto(){
        calcularValorPresente();
        this.desconto = this.valorNominal - this.valorPresente;
    }
}