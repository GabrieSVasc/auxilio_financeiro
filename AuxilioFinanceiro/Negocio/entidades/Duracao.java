package entidades;

public class Duracao {
    private int tipo;
    private double tempo;
    
    public Duracao(int tipo, double tempo){
        this.tipo = tipo;
        if (tipo == 0) this.tempo = tempo;
        else if (tipo == 1) this.tempo = tempo*12;

    }

    
    // Getters
    public int getTipo(){ return tipo; }
    public double getTempo(){ return tempo; }

    // Setters
    public void setTipo(int novoTipo){ this.tipo = novoTipo; }
    public void setTempo(double novoTempo){ this.tempo = novoTempo; }
}
