package negocio.entidades.tiposdesconto;

import negocio.entidades.Desconto;
import negocio.entidades.Duracao;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Classe que herda a classe abstrata Desconto
 * 
 * Calcula o desconto de um investimento utilizando juros simples
 * 
 * @author Divancy Bruno
 */
public class DescontoSimples extends Desconto {
    public DescontoSimples(double valorNominal, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(valorNominal, taxa, duracao);
    }
    public DescontoSimples(double valorNominal, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(valorNominal, taxa, tipo, tempo);
    }

    @Override
     /**
     * Calcula o valor presente usando juros simples
     */
    public double calcularValorPresente(){
        this.valorPresente = this.valorNominal*(1 - taxa*this.duracao.getTempo());
        return this.valorPresente;
    }
    
     /**
     * Calcula a diferença entre o valor esperado (nominal) e o valor do momento do desconto (presente)
     */
    @Override
    public double calcularDesconto(){
        calcularValorPresente();
        this.desconto = this.valorNominal - this.valorPresente;
        return this.desconto;
    }
}