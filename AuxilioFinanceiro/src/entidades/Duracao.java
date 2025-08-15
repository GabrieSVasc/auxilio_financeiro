package entidades;

public class Duracao {
    private int tipo;
    private double tempo;
    
    Duracao(int tipo, double tempo){
        this.tipo = tipo;
        this.tempo = tempo;
    }

    // Getters
    public int getTipo(){ return tipo; }
    public double getTempo(){ return tempo; }

    // Setters
    public void setTipo(int novoTipo){ this.tipo = novoTipo; }
    public void setTempo(double novoTempo){ this.tempo = novoTempo; }
}
