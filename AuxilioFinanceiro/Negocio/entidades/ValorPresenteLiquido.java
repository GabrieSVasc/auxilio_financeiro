package entidades;
import Excecoes.OpcaoInvalidaException;
import Excecoes.TIRImpossivelException;
import Excecoes.ValorInvalidoException;
import java.util.ArrayList;

public class ValorPresenteLiquido {
    protected double custoInicial, vpl, taxa, tir;
    protected ArrayList<Double> arrecadacao;
    protected Duracao duracao;

    // Constructors
    public ValorPresenteLiquido(double custoInicial, double taxa, Duracao duracao, String arrecadacao) throws ValorInvalidoException {
        // Validando as opções
        if (custoInicial <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        } 
        // safe
        else{
            this.custoInicial = custoInicial;
            this.vpl = 0;
            this.taxa = taxa/100;
            this.duracao = duracao;
            this.tir = 0;
            this.arrecadacao = stringParaArrecadacao(arrecadacao);
        }
    }
    public ValorPresenteLiquido(double custoInicial, double taxa, int tipo, double tempo, String arrecadacao) throws ValorInvalidoException, OpcaoInvalidaException {
        // Validando as opções
        if (custoInicial <= 0 || taxa <= 0){
            throw new ValorInvalidoException();
        }
        // Safe
        else{
            this.custoInicial = custoInicial;
            this.vpl = 0;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
            this.tir = 0;
            this.arrecadacao = stringParaArrecadacao(arrecadacao);
        }
    }

    private ArrayList<Double> stringParaArrecadacao(String arrecadacoes) throws ValorInvalidoException {
        ArrayList<Double> lista = new ArrayList<>();

        String[] partes = arrecadacoes.split(",");

        if (partes.length != this.duracao.getTempo()) {
            throw new ValorInvalidoException();
        }

        for (String p : partes) {
            p = p.trim();
            try {
                double valor = Double.parseDouble(p);
                if (valor < 0) {
                    throw new ValorInvalidoException();
                }
                lista.add(valor);
            } catch (NumberFormatException e) {
                throw new ValorInvalidoException();
            }
        }
        return lista;
    }


    // Getters
    public ArrayList<Double> getArrecadacao() { return arrecadacao; }
    public Duracao getDuracao(){ return duracao; }
    public double getCustoInicial(){ return custoInicial; }
    public double getVpl(){ return vpl; }
    public double getTaxa(){ return taxa; }
    public double getTir(){ return tir; }

    // Setters
    public void setArrecadacao(String novaArrecadacao) throws ValorInvalidoException{
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
        this.vpl = this.custoInicial*-1;
        for (int i = 0; i < this.duracao.getTempo(); i++){
            this.vpl += arrecadacao.get(i)/Math.pow(1+this.taxa, i+1);
        }
        return this.vpl;
    }

    public void calcularTIR() throws TIRImpossivelException {
        this.tir = 0.0;
        double tolerancia = 0.0001;
        int numRepeticoes = 1000;

        for (int iter = 0; iter < numRepeticoes; iter++) {
            double vplAtual = -1*custoInicial;
            double derivada = 0;

            for (int t = 0; t < arrecadacao.size(); t++) {
                double ft = arrecadacao.get(t);
                vplAtual += ft / Math.pow(1 + this.tir, t + 1);
                derivada += - (t + 1) * ft / Math.pow(1 + this.tir, t + 2);
            }

            if (Math.abs(vplAtual) < tolerancia) {
                return;
            }
            this.tir = this.tir - vplAtual / derivada;
        }
        throw new TIRImpossivelException();
    }
}
