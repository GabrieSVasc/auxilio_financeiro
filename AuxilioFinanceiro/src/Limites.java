public class Limites {
    private static int contador = 1;
    private int id;
    private Categoria categoria;
    private double valor;

    public Limites(Categoria categoria, double valor) {
        this.id = contador++;
        this.categoria = categoria;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Limite #" + id + " - " + categoria.getNome() + ": R$" + valor;
    }
}