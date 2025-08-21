package model.gastos;

import exceptions.CampoVazioException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Categoria;

/**
 * Representa um gasto.
 * Contém informações como nome, valor, data e categoria.
 */
public class Gasto {
    private static int contadorId = 1;
    protected int id;
    protected String nome;
    protected double valor;
    protected LocalDate dataCriacao;
    protected final Categoria categoria;
    
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Gasto(String nome, double valor, Categoria categoria, LocalDate dataCriacao) throws CampoVazioException {
        if (categoria == null) {
            throw new CampoVazioException("Categoria do gasto");
        }
        this.id = contadorId++;
        setNome(nome);
        setValor(valor);
        this.dataCriacao = dataCriacao;
        this.categoria = categoria;
    }
    
    // Construtor para ser usado pelo Repositório ao carregar dados do arquivo
    public Gasto(int id, String nome, double valor, Categoria categoria, LocalDate dataCriacao) throws CampoVazioException {
        if (categoria == null) {
            throw new CampoVazioException("Categoria do gasto");
        }
        this.id = id;
        if (id >= contadorId) {
            contadorId = id + 1;
        }
        setNome(nome);
        setValor(valor);
        this.dataCriacao = dataCriacao;
        this.categoria = categoria;
    }

    // --- Getters e Setters ---
    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public Categoria getCategoria() { return categoria; }
    
    public void setNome(String nome) throws CampoVazioException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new CampoVazioException("Nome do gasto");
        }
        this.nome = nome.trim();
    }
    
    public void setValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do gasto deve ser maior que zero");
        }
        this.valor = valor;
    }

    public void setDataCriacao(LocalDate data) {
        this.dataCriacao = data;
    }
    
    // --- Métodos de Arquivo, Exibição e utilitários ---

    public String toFileString() {
        return String.join(";",
            String.valueOf(id),
            nome,
            String.valueOf(valor),
            dataCriacao.format(DATE_FORMATTER),
            String.valueOf(categoria.getId())
        );
    }
    
    public static void resetarContador() {
        contadorId = 1;
    }
    
    public String exibir() {
        return String.format("Gasto #%d - %s (R$ %.2f em %s) - %s",
            id, nome, valor, dataCriacao.format(DATE_FORMATTER), categoria.getNome());
    }
    
    @Override
    public String toString() {
        return exibir();
    }
}