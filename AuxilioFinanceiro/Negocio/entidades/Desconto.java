package entidades;

public abstract class Desconto {
    protected double desconto, valorNominal, valorPresente, taxa;
    protected Duracao duracao;

    public Desconto(double valorNominal, double taxa, Duracao duracao){
        this.valorNominal = valorNominal;
        this.taxa = taxa/100;
        this.duracao = duracao;
        this.desconto = 0;
        this.valorPresente = 0;
    }
    public Desconto(double valorNominal, double taxa, int tipo, double tempo){
        this.valorNominal = valorNominal;
        this.taxa = taxa;
        this.duracao = new Duracao(tipo, tempo);
        this.desconto = 0;
        this.valorPresente = 0;
    }
    public Desconto(){
        this.valorNominal = 0;
        this.taxa = 0;
        this.duracao = null;
        this.desconto = 0;
        this.valorPresente = 0;
    }

    
    // Getters
    public double getValorNominal() { return valorNominal; }
    public double getValorPresente(){ return valorPresente; }
    public double getTaxa(){ return taxa; }
    public double getDesconto(){ return desconto; }
    public Duracao getDuracao(){ return duracao; }

    // Setters
    public void setValorNominal(double novoValorNominal){ this.valorNominal = novoValorNominal; }
    public void setTaxa(double novaTaxa){ this.taxa = novaTaxa; }
    public void setDuracao(Duracao novaDuracao){ this.duracao = novaDuracao; }
    public void setDuracao(int tipo, double tempo){ 
        this.duracao.setTipo(tipo);
        this.duracao.setTempo(tempo); 
    }


    public abstract void calcularValorPresente();

    public abstract void calcularDesconto();
}