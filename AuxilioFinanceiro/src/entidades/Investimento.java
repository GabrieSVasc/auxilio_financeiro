package entidades;

public class Investimento {
    private String nome; // Talvez? pode ser útil em organização, caso criemos uma interface de investimentos criados. No momento inútil
    private double montante, taxa, retorno; 
    private Duracao duracao;

    public Investimento(double montante, double taxa, Duracao duracao){
        this.montante = montante;
        this.taxa = taxa;
        this.duracao = duracao;
        this.retorno = 0;
    }
    public Investimento(double montante, double taxa, int tipo, double tempo){
        this.montante = montante;
        this.taxa = taxa;
        this.duracao = new Duracao(tipo, tempo);
        this.retorno = 0;
    }
    public Investimento(){
        this.montante = 0;
        this.taxa = 0;
        this.duracao = null;
        this.retorno = 0;
    }

    // Getters
    public double getMontante() { return montante; }
    public double getTaxa(){ return taxa; }
    public double getRetorno(){ return retorno; }
    public Duracao getDuracao(){ return duracao; }

    // Setters
    public void setMontante(double novoMontante){ this.montante = novoMontante; }
    public void setTaxa(double novaTaxa){ this.taxa = novaTaxa; }
    public void setDuracao(Duracao novaDuracao){ this.duracao = novaDuracao; }
    public void setDuracao(int tipo, double tempo){ 
        this.duracao.setTipo(tipo);
        this.duracao.setTempo(tempo); 
    }

    //public abstract double calcularRetorno();

}
