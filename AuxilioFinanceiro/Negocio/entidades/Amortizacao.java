package entidades;

public abstract  class Amortizacao {
    protected double amortizacao, parcela, juros, saldoDevedor, montante, taxa;
    protected int numParcelas;

    public Amortizacao(double montante, double taxa, int numParcelas) {
        this.montante = montante;
        this.saldoDevedor = montante;
        this.taxa = taxa/100;
        this.numParcelas = numParcelas;
        this.amortizacao = 0;
        this.parcela = 0;
        this.juros = 0;
    }
    public Amortizacao(){
        this.montante = 0;
        this.saldoDevedor = 0;
        this.taxa = 0;
        this.numParcelas = 0;
        this.amortizacao = 0;
        this.parcela = 0;
        this.juros = 0;
    }

    
    // Getters
    public double getAmortizacao(){ return this.amortizacao; }
    public double getParcela(){ return this.parcela; }
    public double getJuros(){ return this.juros; }
    public double getSaldoDevedor(){ return this.saldoDevedor; }
    public double getMontante(){ return this.montante; }
    public double getTaxa(){ return this.taxa; }
    public int getNumParcela(){ return this.numParcelas; }

    // Setters
    public void setMontante(double novoMontante){ this.montante = novoMontante; }
    public void setTaxa(double novaTaxa){ this.taxa = novaTaxa; }
    public void setNumParcela(int novoNumParcela){ this.numParcelas = novoNumParcela; }

    
    public abstract void calcularTudo();
}
