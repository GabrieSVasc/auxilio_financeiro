package model;
import exceptions.ObjetoNuloException;
import exceptions.ValorNegativoException;
/** Representa um limite de gasto associado a uma Categoria. */
public class Limite extends ObjetivoFinanceiro {
    private Categoria categoria;
    public Limite(Categoria categoria, double valor) throws ObjetoNuloException, ValorNegativoException {
        super(0); setCategoria(categoria); setValor(valor);
    }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) throws ObjetoNuloException {
        if (categoria == null) throw new ObjetoNuloException("Categoria");
        this.categoria = categoria;
    }
    @Override public void setValor(double valor) throws ValorNegativoException {
        if (valor < 0) throw new ValorNegativoException("Valor do limite");
        this.valor = valor;
    }
    @Override public String exibir() {
        return "Limite #" + id + " - " + categoria.getNome() + ": R$" + String.format("%.2f", valor);
    }
}
