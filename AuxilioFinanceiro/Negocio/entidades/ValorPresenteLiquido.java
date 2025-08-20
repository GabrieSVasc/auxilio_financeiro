package entidades;
import Excecoes.OpcaoInvalidaException;
import Excecoes.TIRImpossivelException;
import Excecoes.ValorInvalidoException;
import utils.ValidarValor;

import java.util.ArrayList;
import java.util.Scanner;

public class ValorPresenteLiquido {
    protected double custoInicial, vpl, taxa, tir;
    protected ArrayList<Double> arrecadacao; // not ok
    protected Duracao duracao;

    // Constructors
    public ValorPresenteLiquido(double custoInicial, double taxa, Duracao duracao) throws ValorInvalidoException {
        // Validando as opções
        if (!ValidarValor.ehPositivo(custoInicial) || !ValidarValor.ehPositivo(taxa)){
            throw new ValorInvalidoException();
        } 
        // Ta safe padrin
        else{
            this.arrecadacao = new ArrayList<>();
            Scanner input = new Scanner(System.in);
            this.custoInicial = custoInicial;
            this.vpl = 0;
            this.taxa = taxa/100;
            this.duracao = duracao;
            for (int i = 0; i < duracao.getTempo(); i++){
                System.out.printf("Adicione o valor de entrada n° %d: ", i+1);
                arrecadacao.add(input.nextDouble());
            }
            this.tir = 0;
        }
    }
    public ValorPresenteLiquido(double custoInicial, double taxa, int tipo, double tempo) throws ValorInvalidoException, OpcaoInvalidaException {
        // Validando as opções
        if (!ValidarValor.ehPositivo(custoInicial) || !ValidarValor.ehPositivo(taxa)){
            throw new ValorInvalidoException();
        }
        // Safe
        else{
            this.arrecadacao = new ArrayList<>();
            Scanner input = new Scanner(System.in);
            this.custoInicial = custoInicial;
            this.vpl = 0;
            this.taxa = taxa/100;
            this.duracao = new Duracao(tipo, tempo);
            for (int i = 0; i < duracao.getTempo(); i++){
            System.out.printf("Adicione o valor de entrada n° %d: ", i+1);
            arrecadacao.add(input.nextDouble());
        }
            this.tir = 0;
        }
    }

    // Getters
    public ArrayList<Double> getArrecadacao() { return arrecadacao; }
    public Duracao getDuracao(){ return duracao; }
    public double getCustoInicial(){ return custoInicial; }
    public double getVpl(){ return vpl; }
    public double getTaxa(){ return taxa; }
    public double getTir(){ return tir; }

    // Setters
    public void setArrecadacao(ArrayList<Double> novaArrecadacao){ this.arrecadacao = novaArrecadacao; } // not ok

    public void setCustoInicial(double novoCustoInicial) throws ValorInvalidoException {
        if (!ValidarValor.ehPositivo(novoCustoInicial)) throw new ValorInvalidoException();
        else this.custoInicial = novoCustoInicial; 
    }
    public void setTaxa(double novaTaxa) throws ValorInvalidoException { 
        if (!ValidarValor.ehPositivo(novaTaxa)) throw new ValorInvalidoException();
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
