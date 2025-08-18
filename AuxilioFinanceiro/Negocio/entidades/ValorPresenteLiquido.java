package entidades;
import java.util.ArrayList;
import java.util.Scanner;

public class ValorPresenteLiquido {
    protected double custoInicial, vpl, taxa;
    protected ArrayList<Double> arrecadacao;
    protected Duracao duracao;

    public ValorPresenteLiquido(double custoInicial, double taxa, Duracao duracao) {
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
    }
    public ValorPresenteLiquido(double custoInicial, double taxa, int tipo, double tempo) {
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
    }
    public ValorPresenteLiquido(){
        this.custoInicial = 0;
        this.vpl = 0;
        this.taxa = 0;
        this.arrecadacao = new ArrayList<>();
        this.duracao = null;
    }


    // Getters
    public ArrayList<Double> getArrecadacao() { return arrecadacao; }
    public Duracao getDuracao(){ return duracao; }
    public double getCustoInicial(){ return custoInicial; }
    public double getVpl(){ return vpl; }
    public double getTaxa(){ return taxa; }
    
    // Setters
    public void setArrecadacao(ArrayList<Double> novaArrecadacao){ this.arrecadacao = novaArrecadacao; }
    public void setCustoInicial(double novoCustoInicial){ this.custoInicial = novoCustoInicial; }
    public void setTaxa(double novaTaxa){ this.taxa = novaTaxa; }
    public void setDuracao(Duracao novaDuracao){ this.duracao = novaDuracao; }
    public void setDuracao(int novoTipo, double novoTempo){ this.duracao = new Duracao(novoTipo, novoTempo); }

    
    public double calcularVPL(){
        this.vpl = this.custoInicial*-1;
        for (int i = 0; i < this.duracao.getTempo(); i++){
            this.vpl += arrecadacao.get(i)/Math.pow(1+this.taxa, i+1);
        }
        return this.vpl;
    }
}
