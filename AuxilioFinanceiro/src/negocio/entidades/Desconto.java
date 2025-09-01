package negocio.entidades;

import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Classe abstrata para calcular um desconto
 * 
 * Calcula o desconto de um investimento, ou seja:
 * Calcula o quanto que o investidor deixa de ganhar ao antecipar um título para determinado período.
 * 
 * Pode ser um desconto simples ou composto.
 * 
 * @author Divancy Bruno
 */
public abstract class Desconto {
    protected double desconto, valorNominal, valorPresente, taxa;
    protected Duracao duracao;

    /** 
     * Construtor de Desconto
     * 
     * Construtor padrão, necessita dos parâmetros:
     * @param valorNominal, o valor que é esperado receber ao fim da operação.
     * @param taxa, taxa de juros por período da operação
     * @param duracao, objeto da classe duração, o qual possui um tempo e um tipo, que define se é em meses ou anos.
     * 
     * @throws ValorInvalidoException, se algum parâmetro for menor ou igual a zero
     */
    public Desconto(double valorNominal, double taxa, Duracao duracao) throws ValorInvalidoException{
        if (valorNominal <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.valorNominal = valorNominal;
            this.taxa = taxa/100;
            this.duracao = duracao;
            this.desconto = 0;
            this.valorPresente = 0;
        }
    }
    
    /** 
     * Construtor de Desconto
     * 
     * Construtor alternativo, necessita dos parâmetros:
     * @param valorNominal, o valor que é esperado receber ao fim da operação.
     * @param taxa, taxa de juros por período da operação
     * @param tipo, define se é em meses ou anos, sendo respectivamente 0 ou 1.
     * @param tempo, define o tempo que será reduzido da operação inicial (Supondo que era 5 e o tempo passado foi 3, o calculo será feito como se fosse no período 2)
     * 
     * @throws ValorInvalidoException, se algum parâmetro, exceto tipo, for menor ou igual a 0
     * @throws OPcaoInvalidaException, se o parâmetro tipo for diferente de 0 ou 1
     */
    public Desconto(double valorNominal, double taxa, int tipo, double tempo) throws  ValorInvalidoException, OpcaoInvalidaException{
        if (valorNominal <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.valorNominal = valorNominal;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
            this.desconto = 0;
            this.valorPresente = 0;
        }
    }

    
    // Getters
    public double getValorNominal() { return valorNominal; }
    public double getValorPresente(){ return valorPresente; }
    public double getTaxa(){ return taxa; }
    public double getDesconto(){ return desconto; }
    public Duracao getDuracao(){ return duracao; }

    // Setters
    public void setValorNominal(double novoValorNominal) throws ValorInvalidoException{
        if (novoValorNominal <= 0) throw new ValorInvalidoException();
        else this.valorNominal = novoValorNominal; 
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
     * Método abstrato que calcula o valor de um título no momento do desconto
     */
    public abstract double calcularValorPresente();
    /**
     * Método abstrato que calcula o quanto que o investidor deixará de receber se descontar nos parâmetros passados
     */
    public abstract double calcularDesconto();
}