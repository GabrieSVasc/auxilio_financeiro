package negocio.entidades;
import negocio.exceptions.ObjetoNuloException;
import negocio.exceptions.ValorNegativoException;
/**
 * Representa um limite de gasto associado a uma categoria.
 * Permite acompanhar o total gasto e definir alertas via lembretes.
 * 
 * @author Pedro Farias
 */
public class Limite extends ObjetivoFinanceiro {
    private Categoria categoria;
    private static int contadorLimite = 1; // contador próprio da classe
    private double totalGasto = 0; // total de gastos atuais da categoria

    // Construtor para criação normal (salva no arquivo)o
    public Limite(Categoria categoria, double valor) throws ObjetoNuloException, ValorNegativoException {
        super(valor, contadorLimite++);
        setCategoria(categoria);
        setValor(valor);
    }

    public Categoria getCategoria() { return categoria; }
    public double getValorLimite() { return this.valor; }

    public void setCategoria(Categoria categoria) throws ObjetoNuloException {
        if (categoria == null) throw new ObjetoNuloException("Categoria");
        this.categoria = categoria;
    }

    @Override
    public void setValor(double valor) throws ValorNegativoException {
        if (valor < 0) throw new ValorNegativoException("Valor do limite");
        this.valor = valor;
    }

    public double getTotalGastos() { return totalGasto; }
     /**
     * Atualiza o valor total de gastos registrado neste limite.
     * 
     * @param totalGastos Novo total de gastos
     */
    public void setTotalGastos(double totalGasto) { this.totalGasto = totalGasto; }

 /**
     * Verifica se o limite foi atingido.
     * 
     * @return true se total de gastos >= valor do limite, false caso contrário
     */
    public boolean estaAtingido() { return totalGasto >= valor; }

    @Override
    public String exibir() {
        return "Limite #" + id + " - " + categoria.getNome() + ": R$" + String.format("%.2f", valor)
                + " | Gasto Atual: R$" + String.format("%.2f", totalGasto)
                + (estaAtingido() ? " [ATINGIDO]" : "");
    }
}