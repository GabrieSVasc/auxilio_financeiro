package model;
import exceptions.ObjetoNuloException;
import exceptions.ValorNegativoException;
import java.util.ArrayList;
import java.util.List;
import util.arquivoUtils;

/** Representa um limite de gasto associado a uma Categoria. */
public class Limite extends ObjetivoFinanceiro {
    private Categoria categoria;
    private static int contadorLimite = 1; // contador próprio da classe

    public Limite(Categoria categoria, double valor) throws ObjetoNuloException, ValorNegativoException {
        super(valor, contadorLimite++);
        setCategoria(categoria);
        setValor(valor);
        // Salva no arquivo
        arquivoUtils.salvarEmArquivo("limites.txt", categoria.getNome() + ";" + valor, true);
    }

    public Categoria getCategoria() { return categoria; }

    public void setCategoria(Categoria categoria) throws ObjetoNuloException {
        if (categoria == null) throw new ObjetoNuloException("Categoria");
        this.categoria = categoria;
    }

    @Override
    public void setValor(double valor) throws ValorNegativoException {
        if (valor < 0) throw new ValorNegativoException("Valor do limite");
        this.valor = valor;
    }

    @Override
    public String exibir() {
        return "Limite #" + id + " - " + categoria.getNome() + ": R$" + String.format("%.2f", valor);
    }

    // Recarrega limites do arquivo
    public static List<Limite> carregarLimites(List<Categoria> categorias) {
        List<Limite> limites = new ArrayList<>();
        List<String> linhas = arquivoUtils.lerDoArquivo("limites.txt");

        for (String linha : linhas) {
            try {
                String[] partes = linha.split(";");
                String nomeCategoria = partes[0];
                double valor = Double.parseDouble(partes[1]);

                Categoria cat = categorias.stream()
                                          .filter(c -> c.getNome().equals(nomeCategoria))
                                          .findFirst().orElse(null);

                if (cat != null) {
                    limites.add(new Limite(cat, valor));
                }
            } catch (Exception e) {
                System.out.println("Erro ao carregar limite: " + e.getMessage());
            }
        }
        return limites;
    }
}
