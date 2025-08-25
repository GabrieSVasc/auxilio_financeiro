package negocio.entidades.entidades;
import java.util.ArrayList;

import negocio.Excecoes.FormatacaoInvalidaException;
import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.TIRImpossivelException;
import negocio.Excecoes.ValorInvalidoException;

public class ValorPresenteLiquido {
    protected double custoInicial, taxa;
    protected ArrayList<Double> arrecadacao;
    protected Duracao duracao;

    // Constructors
    public ValorPresenteLiquido(double custoInicial, double taxa, Duracao duracao, String arrecadacao) throws ValorInvalidoException, FormatacaoInvalidaException {
        // Validando as opções
        if (custoInicial <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        } 
        // safe
        else{
            this.custoInicial = custoInicial;
            this.taxa = taxa/100;
            this.duracao = duracao;
            this.arrecadacao = stringParaArrecadacao(arrecadacao);
        }
    }
    public ValorPresenteLiquido(double custoInicial, double taxa, int tipo, double tempo, String arrecadacao) throws ValorInvalidoException, OpcaoInvalidaException, FormatacaoInvalidaException {
        // Validando as opções
        if (custoInicial <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        // Safe
        else{
            this.custoInicial = custoInicial;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
            this.arrecadacao = stringParaArrecadacao(arrecadacao);
        }
    }

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


    public double calcularVPL(){
        double vpl = this.custoInicial*-1;
        for (int i = 0; i < this.duracao.getTempo(); i++){
            vpl += arrecadacao.get(i)/Math.pow(1+this.taxa, i+1);
        }
        return vpl;
    }

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
