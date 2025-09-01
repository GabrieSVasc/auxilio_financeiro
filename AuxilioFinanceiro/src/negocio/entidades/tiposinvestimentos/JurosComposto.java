package negocio.entidades.tiposinvestimentos;

import negocio.entidades.Duracao;
import negocio.entidades.Investimento;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Subclasse da classe abstrata Investimento
 * 
 * JurosComposto Calcula o montante que será recebido após determinado período, baseando-se em uma taxa de juros
 * 
 * Utiliza a técnica de juros compostos, que é o investimento de um valor,
 * o qual a cada mês irá crescer baseando-se no montante do mês anterior,
 * ao invés de ser no capital, que é o caso do juros simples.
 * 
 * @author Divancy Bruno
 */
public class JurosComposto extends Investimento {
    public JurosComposto(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(capital, taxa, duracao);
    }
    public JurosComposto(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(capital, taxa, tipo, tempo);
    }

    /**
     * Realiza o cálculo do montante com a técnica de juros compostos, isto é, um valor que será acrescido de uma taxa de juros por um período de forma exponencial.
     */
    @Override
    public double calcularMontante(){
        double tempo = duracao.getTempo();
        double montante = capital * Math.pow(1+taxa, tempo); 
        return montante;
    }
}
