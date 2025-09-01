package negocio.entidades;

import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Classe abstrata para investimentos mais comuns, os quais:
 * 
 * Calculam o montante que será recebido após determinado período, baseando-se em uma taxa de juros
 * 
 * Pode ser feito em juros simples, compostos ou aportes períodicos, isto é, investir uma determinada quantia fixa por período
 * 
 * @author Divancy Bruno
 */
public abstract class Investimento {
    protected double capital, taxa; 
    protected Duracao duracao;

    /** 
     * Construtor de Investimento
     * 
     * Construtor padrão, necessita dos parâmetros:
     * @param capital, o valor inicial a ser investido
     * @param taxa, taxa de juros por período da operação
     * @param duracao, objeto da classe duração, o qual possui um tempo e um tipo, que define se é em meses ou anos.
     * 
     * @throws ValorInvalidoException, se algum parâmetro for menor ou igual a zero
     */
    public Investimento(double capital, double taxa, Duracao duracao) throws ValorInvalidoException{
        if (capital <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.capital = capital;
            this.taxa = taxa/100;
            this.duracao = duracao;
        }
    }
    
    /** 
     * Construtor de Investimento
     * 
     * Construtor alternativo, necessita dos parâmetros:
     * @param capital, o valor inicial a ser investido
     * @param taxa, taxa de juros por período da operação
     * @param tipo, define se é em meses ou anos, sendo respectivamente 0 ou 1.
     * @param tempo, define o tempo que o investimento irá durar.
     * 
     * @throws ValorInvalidoException, se algum parâmetro, exceto tipo, for menor ou igual a 0
     * @throws OPcaoInvalidaException, se o parâmetro tipo for diferente de 0 ou 1
     */
    public Investimento(double capital, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException{
        if (capital <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.capital = capital;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
        }
    }

    // Getters
    public double getCapital() {     return capital; }
    public double getTaxa(){ return taxa; }
    public Duracao getDuracao(){ return duracao; }

    // Setters
    public void setCapital(double novoCapital) throws ValorInvalidoException{
        if (novoCapital <= 0) throw new ValorInvalidoException();
        else this.capital = novoCapital;
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException { 
        if (novaTaxa <= 0) throw new ValorInvalidoException();
        else this.taxa = novaTaxa; 
    }
    public void setDuracao(Duracao novaDuracao) {
        this.duracao = novaDuracao; 
    }
    public void setTipo(int novoTipo) throws OpcaoInvalidaException {
        this.duracao.setTipo(novoTipo);
    }
    public void setTempo(double novoTempo) throws ValorInvalidoException{
        this.duracao.setTempo(novoTempo);
    }


    /**
     * Método abstrato para calcular o valor a ser recebido em um investimento
     */
    public abstract double calcularMontante();
}
