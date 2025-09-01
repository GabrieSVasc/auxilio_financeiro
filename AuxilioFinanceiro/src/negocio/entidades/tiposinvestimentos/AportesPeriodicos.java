package negocio.entidades.tiposinvestimentos;

import negocio.entidades.Duracao;
import negocio.entidades.Investimento;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Subclasse da classe abstrata Investimento
 * 
 * AportesPeriodicos Calcula o montante que será recebido após determinado período, baseando-se em uma taxa de juros
 * 
 * Utiliza a técnica de capitalização, que é o investimento de um valor fixo a cada inicio de período.
 * 
 * @author Divancy Bruno
 */
public class AportesPeriodicos extends Investimento {
    public AportesPeriodicos(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        super(capital, taxa, duracao);
    }
    public AportesPeriodicos(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        super(capital, taxa, tipo, tempo);
    }

    /**
     * Realiza o cálculo do montante com a técnica de capitalização, isto é, o investimento de um valor fixo a cada inicio de período.
     */
    @Override
    public double calcularMontante(){
        double tempo = duracao.getTempo();
        double montante = (1 + taxa)*capital * (Math.pow(1+taxa, tempo) - 1)/taxa;

        return montante;
    }
}

