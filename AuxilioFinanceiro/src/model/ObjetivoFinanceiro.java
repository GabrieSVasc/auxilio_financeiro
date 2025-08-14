package model;
/** Classe base para objetivos financeiros (herança). */
public abstract class ObjetivoFinanceiro implements Exibivel {
    protected static int contadorGlobal = 1;
    protected final int id;
    protected double valor;
    protected ObjetivoFinanceiro(double valor) { this.id = contadorGlobal++; this.valor = valor; }
    public int getId() { return id; }
    public double getValor() { return valor; }
    public abstract void setValor(double valor) throws exceptions.ValorNegativoException;
    @Override public String toString() { return exibir(); }
}
