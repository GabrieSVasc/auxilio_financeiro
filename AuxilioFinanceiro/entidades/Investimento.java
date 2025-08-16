package entidades;

public abstract class Investimento {
    protected String nome; // Talvez? pode ser útil em organização, caso criemos uma interface de investimentos criados. No momento inútil
    protected double capital, taxa, montante; 
    protected Duracao duracao;

    public Investimento(double capital, double taxa, Duracao duracao){
        this.capital = capital;
        this.taxa = taxa;
        this.duracao = duracao;
        this.montante = 0;
    }
    public Investimento(double capital, double taxa, int tipo, double tempo){
        this.capital = capital;
        this.taxa = taxa;
        this.duracao = new Duracao(tipo, tempo);
        this.montante = 0;
    }
    public Investimento(){
        this.capital = 0;
        this.taxa = 0;
        this.duracao = null;
        this.montante = 0;
    }

    // Getters
    public double getCapital() { return capital; }
    public double getTaxa(){ return taxa; }
    public double getMontante(){ return montante; }
    public Duracao getDuracao(){ return duracao; }

    // Setters
    public void setCapital(double novoCapital){ this.capital = novoCapital; }
    public void setTaxa(double novaTaxa){ this.taxa = novaTaxa; }
    public void setDuracao(Duracao novaDuracao){ this.duracao = novaDuracao; }
    public void setDuracao(int tipo, double tempo){ 
        this.duracao.setTipo(tipo);
        this.duracao.setTempo(tempo); 
    }

    public abstract void calcularMontante();

}
