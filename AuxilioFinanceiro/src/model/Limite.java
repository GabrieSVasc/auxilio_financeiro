package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import util.arquivoUtils;

/**
 * Representa um limite de gastos para uma categoria específica.
 */
public class Limite implements Exibivel {

    private static int contador = 1;
    private final int id;
    private Categoria categoria;
    private double valor;
    private double totalGastos; // Gasto acumulado na categoria

    // Construtor para criação de novo limite
    public Limite(Categoria categoria, double valor) {
        this.id = contador++;
        this.categoria = categoria;
        this.valor = valor;
        this.totalGastos = 0;
    }
    
    // Construtor para carregar limite do arquivo
    public Limite(int id, Categoria categoria, double valor, double totalGastos) {
        this.id = id;
        if (id >= contador) {
            contador = id + 1;
        }
        this.categoria = categoria;
        this.valor = valor;
        this.totalGastos = totalGastos;
    }

    public int getId() { return id; }
    public Categoria getCategoria() { return categoria; }
    public double getValor() { return valor; }
    public double getTotalGastos() { return totalGastos; }

    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setValor(double valor) { this.valor = valor; }
    public void setTotalGastos(double totalGastos) { this.totalGastos = totalGastos; }

    @Override
    public String exibir() {
        return String.format("Limite #%d - Categoria: %s - Valor: R$ %.2f - Gasto Atual: R$ %.2f",
            id, categoria.getNome(), valor, totalGastos);
    }
    
    public String toFileString() {
        return String.join(";",
            String.valueOf(id),
            String.valueOf(categoria.getId()),
            String.valueOf(valor),
            String.valueOf(totalGastos)
        );
    }
    
    public static List<Limite> carregarTodos(List<Categoria> categorias) throws IOException {
        List<Limite> limites = new ArrayList<>();
        List<String> linhas = arquivoUtils.lerDoArquivo("limites.txt");
        
        for (String linha : linhas) {
            try {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                int idCategoria = Integer.parseInt(partes[1]);
                double valor = Double.parseDouble(partes[2]);
                double totalGastos = Double.parseDouble(partes[3]);
                
                // Encontra a categoria correspondente
                Categoria cat = categorias.stream()
                        .filter(c -> c.getId() == idCategoria)
                        .findFirst()
                        .orElse(null);
                
                if (cat != null) {
                    limites.add(new Limite(id, cat, valor, totalGastos));
                } else {
                    System.err.println("Categoria com ID " + idCategoria + " não encontrada ao carregar limite.");
                }
            } catch (Exception e) {
                System.err.println("Erro ao ler linha do arquivo de limites: " + linha + " - Erro: " + e.getMessage());
            }
        }
        return limites;
    }
    
    public static void salvarTodos(List<Limite> limites) {
        List<String> linhas = new ArrayList<>();
        for (Limite l : limites) {
            linhas.add(l.toFileString());
        }
        arquivoUtils.salvarListaEmArquivo("limites.txt", linhas);
    }
}