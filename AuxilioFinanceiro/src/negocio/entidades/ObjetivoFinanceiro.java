package negocio.entidades;
import negocio.exceptions.ValorNegativoException;

/** Classe base para objetivos financeiros (herança). */
public abstract class ObjetivoFinanceiro implements Exibivel {
    protected final int id;
    protected double valor;

    // Recebe o valor e o ID já definido na subclasse
    protected ObjetivoFinanceiro(double valor, int id) {
        this.id = id;
        this.valor = valor;
    }

    public int getId() { return id; }
    public double getValor() { return valor; }
    public abstract void setValor(double valor) throws ValorNegativoException;

    @Override
    public String toString() { return exibir(); }
}
