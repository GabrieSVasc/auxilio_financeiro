package negocio.entidades;

import java.util.ArrayList;

import negocio.exceptions.ValorInvalidoException;
/**
 * Classe abstrata para calcular uma amortização.
 * 
 * Calcula a amortização de uma dívida, ou seja,
 * Calcula quanto será o custo de cada parcela
 * Quanto será amortizado por cada parcela
 * E quanto é pago de juros
 * 
 * Pode ser do sistema de amortização constante (SAC), francesa (PRICE) ou misto (SAM).
 * 
 * @author Divancy Bruno
 */
public abstract  class Amortizacao {
    protected double montante, taxa;
    protected ArrayList<Double> parcela, amortizacao, juros, saldoDevedor;
    protected int numParcelas;

    /** 
     * Construtor de Amortizacao
     * 
     * Construtor padrão, necessita dos parâmetros:
     * @param montante, valor total do empréstimo
     * @param taxa, taxa de juros por período do empréstimo
     * @param numParcelas, quantidade de parcelas do empréstimo
     * 
     * @throws ValorInvalidoException, se algum parâmetro for menor ou igual a zero
     */
    public Amortizacao(double montante, double taxa, int numParcelas) throws ValorInvalidoException{
        if (montante <= 0 || taxa <= 0 || numParcelas <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.montante = montante;
            this.taxa = taxa/100;
            this.numParcelas = numParcelas;
            this.saldoDevedor = new ArrayList<>();
            this.amortizacao = new ArrayList<>();
            this.parcela = new ArrayList<>();
            this.juros = new ArrayList<>();
        }
    }


    // Getters
    public ArrayList<Double> getAmortizacao(){ return this.amortizacao; }
    public ArrayList<Double> getParcela(){ return this.parcela; }
    public ArrayList<Double> getJuros(){ return this.juros; }
    public ArrayList<Double> getSaldoDevedor(){ return this.saldoDevedor; }
    public double getMontante(){ return this.montante; }
    public double getTaxa(){ return this.taxa; }
    public int getNumParcela(){ return this.numParcelas; }

    // Setters
    public void setMontante(double novoMontante) throws ValorInvalidoException{
        if (novoMontante <= 0) throw new ValorInvalidoException();
        else this.montante = novoMontante; 
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException{
        if (novaTaxa <= 0) throw new ValorInvalidoException();
        else this.taxa = novaTaxa; 
    }
    public void setNumParcela(int novoNumParcela) throws ValorInvalidoException {
        if (novoNumParcela <= 0) throw new ValorInvalidoException();
        else this.numParcelas = novoNumParcela; 
    }

    /** 
     * Calcula tudo que é importante em uma amortização, ou seja:
     * Calcula as parcelas, os juros, a amortização e o saldo devedor (Quanto falta para quitar a dívida).
     * 
     * @throws ValorInvalidoException, se algum parâmetro for menor ou igual a zero
    */
    public abstract void calcularTudo() throws ValorInvalidoException;
}
