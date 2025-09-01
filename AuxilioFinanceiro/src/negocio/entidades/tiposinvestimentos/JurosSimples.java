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
 * Utiliza a técnica de juros compostos, que é o investimento de um valor e que, de forma linear, irá crescer a cada mês.
 * Basea-se exclusivamente no capital inicial.
 * 
 * @author Divancy Bruno
 */
public class JurosSimples extends Investimento {
    public JurosSimples(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(capital, taxa, duracao);
    }
    public JurosSimples(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(capital, taxa, tipo, tempo);
    }

    /**
     * Realiza o cálculo do montante com a técnica de juros simples, isto é, um valor que será acrescido de uma taxa de juros por um período de forma linear.
     */
    @Override
    public double calcularMontante(){
        double tempo = duracao.getTempo();
        double montante = capital * (1 + taxa * tempo);

        return montante;
    }
}
