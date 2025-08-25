package negocio.entidades.tiposdesconto;

import negocio.entidades.Desconto;
import negocio.entidades.Duracao;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

public class DescontoSimples extends Desconto {
    public DescontoSimples(double valorNominal, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(valorNominal, taxa, duracao);
    }
    public DescontoSimples(double valorNominal, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(valorNominal, taxa, tipo, tempo);
    }

    @Override
    public double calcularValorPresente(){
        this.valorPresente = this.valorNominal*(1 - taxa*this.duracao.getTempo());
        return this.valorPresente;
    }
    
    @Override
    public double calcularDesconto(){
        calcularValorPresente();
        this.desconto = this.valorNominal - this.valorPresente;
        return this.desconto;
    }
}