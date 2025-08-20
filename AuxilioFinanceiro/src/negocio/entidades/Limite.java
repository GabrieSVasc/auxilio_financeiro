package negocio.entidades;

import java.util.ArrayList;
import java.util.List;

import negocio.exceptions.ObjetoNuloException;
import negocio.exceptions.ValorNegativoException;
import util.arquivoUtils;

/** Representa um limite de gasto associado a uma Categoria. */
public class Limite extends ObjetivoFinanceiro {
    private Categoria categoria;
    private static int contadorLimite = 1; // contador próprio da classe

    // Construtor para criação normal (salva no arquivo)
    public Limite(Categoria categoria, double valor) throws ObjetoNuloException, ValorNegativoException {
        super(valor, contadorLimite++);
        setCategoria(categoria);
        setValor(valor);
        arquivoUtils.salvarEmArquivo("limites.txt", categoria.getNome() + ";" + valor, true);
    }

    // Construtor silencioso para recarga (não salva no arquivo)
    public Limite(int id, Categoria categoria, double valor, boolean salvarNoArquivo) throws ObjetoNuloException, ValorNegativoException {
        super(valor, id);
        setCategoria(categoria);
        setValor(valor);
        if (id >= contadorLimite) contadorLimite = id + 1;
        if (salvarNoArquivo) {
            arquivoUtils.salvarEmArquivo("limites.txt", categoria.getNome() + ";" + valor, true);
        }
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
                    limites.add(new Limite(contadorLimite++, cat, valor, false));
                }
            } catch (Exception e) {
                System.out.println("Erro ao carregar limite: " + e.getMessage());
            }
        }
        return limites;
    }

    // Para salvar toda a lista ao editar/deletar
    public static void salvarTodos(List<Limite> limites) {
        List<String> linhas = new ArrayList<>();
        for (Limite l : limites) {
            linhas.add(l.getCategoria().getNome() + ";" + l.getValorLimite());
        }
        arquivoUtils.salvarListaEmArquivo("limites.txt", linhas);
    }
}
