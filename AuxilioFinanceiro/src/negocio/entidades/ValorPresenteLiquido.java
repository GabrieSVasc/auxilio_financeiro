package negocio.entidades;
import java.util.ArrayList;

import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.TIRImpossivelException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Classe para calcula o valor presente líquido de um investimento, ou seja:
 * 
 * Utilizando técnicas de desconto, irá calcular o valor em determinado período, baseando-se em uma taxa de desconto.
 * Após isso, o investidor irá receber um valor presente líquido VPL, que se for positivo, define-se como um investimento que vale a pena.
 * Negativo, define-se como que não vale a pena.
 * Nulo, define-se como indiferente.
 * Isso baseando-se em parâmetros definidos pelo usuário.
 * 
 * Ademais calcula a TIR, que seria a taxa mínima necessária, baseando-se em determinados parâmetros, para um investimento ser pelo menos nulo.
 * 
 * @author Divancy Bruno
 */
public class ValorPresenteLiquido {
    protected double custoInicial, taxa;
    protected ArrayList<Double> arrecadacao;
    protected Duracao duracao;

    /** 
     * Construtor de Desconto
     * 
     * Construtor padrão, necessita dos parâmetros:
     * @param custoInicial, o valor que será inicialmente investido.
     * @param taxa, taxa de juros do desconto.
     * @param duracao, objeto da classe duração, o qual possui um tempo e um tipo, que define se é em meses ou anos.
     * @param arrecadacao, é a arrecadação de cada período de tempo do usuário, sendo formatada com a seguinte notação: (100, 300, 400, 500, ...). 
     * O número de arrecadações deve bater com o tempo informado. Após isso, a formatação é transformada em um ArrayList de valores a serem usados nos cálculos.
     * 
     * @throws ValorInvalidoException, se algum parâmetro for menor ou igual a zero
     * @throws FormatacaoInvalidaException, caso a formatação de arrecadação esteja diferente da informada: (100, 300, 400, 500), formatação válida para tempo = 4.
     */
    public ValorPresenteLiquido(double custoInicial, double taxa, Duracao duracao, String arrecadacao) throws ValorInvalidoException, FormatacaoInvalidaException {
        if (custoInicial <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        } 
        else{
            this.custoInicial = custoInicial;
            this.taxa = taxa/100;
            this.duracao = duracao;
            this.arrecadacao = stringParaArrecadacao(arrecadacao);
        }
    }

    /** 
     * Construtor de Desconto
     * 
     * Construtor alternativo, necessita dos parâmetros:
     * @param custoInicial, o valor que será inicialmente investido.
     * @param taxa, taxa de juros por período da operação
     * @param tipo, define se é em meses ou anos, sendo respectivamente 0 ou 1.
     * @param tempo, define o tempo do desconto e o número de arrecadações esperadas para a validação.
     * @param arrecadacao, é a arrecadação de cada período de tempo do usuário, sendo formatada com a seguinte notação: (100, 300, 400, 500, ...). 
     * 
     * @throws ValorInvalidoException, se algum parâmetro, exceto tipo, for menor ou igual a 0
     * @throws OPcaoInvalidaException, se o parâmetro tipo for diferente de 0 ou 1
     * @throws FormatacaoInvalidaException, caso a formatação de arrecadação esteja diferente da informada: (100, 300, 400, 500), formatação válida para tempo = 4.
     */
    public ValorPresenteLiquido(double custoInicial, double taxa, int tipo, double tempo, String arrecadacao) throws ValorInvalidoException, OpcaoInvalidaException, FormatacaoInvalidaException {
        if (custoInicial <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        else{
            this.custoInicial = custoInicial;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
            this.arrecadacao = stringParaArrecadacao(arrecadacao);
        }
    }

    /**
     * Método para validar se a String arrecadação foi válida para ser convertida em um arrayList de doubles.
     */
    private ArrayList<Double> stringParaArrecadacao(String arrecadacoes) throws ValorInvalidoException, FormatacaoInvalidaException {
        ArrayList<Double> lista = new ArrayList<>();

        String[] partes = arrecadacoes.split(",");

        if (partes.length != this.duracao.getTempo()) {
            throw new FormatacaoInvalidaException();
        }

        for (String p : partes) {
            p = p.trim();
            double valor = Double.parseDouble(p);
            if (valor <= 0) {
                throw new ValorInvalidoException();
            }
                lista.add(valor);
        }
        return lista;
    }


    // Getters
    public ArrayList<Double> getArrecadacao() { return arrecadacao; }
    public Duracao getDuracao(){ return duracao; }
    public double getCustoInicial(){ return custoInicial; }
    public double getTaxa(){ return taxa; }

    // Setters
    public void setArrecadacao(String novaArrecadacao) throws ValorInvalidoException, FormatacaoInvalidaException{
        this.arrecadacao = stringParaArrecadacao(novaArrecadacao); 
    }
    public void setCustoInicial(double novoCustoInicial) throws ValorInvalidoException {
        if (novoCustoInicial <= 0) throw new ValorInvalidoException();
        else this.custoInicial = novoCustoInicial; 
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException { 
        if (novaTaxa <= 0) throw new ValorInvalidoException();
        else this.taxa = novaTaxa; 
    }
    public void setDuracao(Duracao novaDuracao) {
        this.duracao = novaDuracao; 
    }
    public void setTipo(int novoTipo) throws OpcaoInvalidaException{
        this.duracao.setTipo(novoTipo);
    }
    public void setTempo(double novoTempo) throws ValorInvalidoException {
        this.duracao.setTempo(novoTempo);
    }


    /**
     * Método para calcular o VPL de uma operação.
     */
    public double calcularVPL(){
        double vpl = this.custoInicial*-1;
        for (int i = 0; i < this.duracao.getTempo(); i++){
            vpl += arrecadacao.get(i)/Math.pow(1+this.taxa, i+1);
        }
        return vpl;
    }

    /**
     * Método para calcular a taxa de juros para uma operação financeira ser, no mínimo, 0. A chamada taxa interna de retorno (TIR).
     * 
     * @throws TIRImpossivelException, caso os valores investidos e as arrecadações esperadas não fiquem, no mínimo, dentro do intervalo de tolerância.
     */
    public double calcularTIR() throws TIRImpossivelException {
        double tir = 0.0;
        double tolerancia = 0.0001;
        int numRepeticoes = 1000;

        for (int iter = 0; iter < numRepeticoes; iter++) {
            double vplAtual = -1*custoInicial;
            double derivada = 0;

            for (int t = 0; t < arrecadacao.size(); t++) {
                double ft = arrecadacao.get(t);
                vplAtual += ft / Math.pow(1 + tir, t + 1);
                derivada += - (t + 1) * ft / Math.pow(1 + tir, t + 2);
            }

            if (Math.abs(vplAtual) < tolerancia) {
                return tir;
            }
            tir = tir - vplAtual / derivada;
        }
        throw new TIRImpossivelException();
    }
}
