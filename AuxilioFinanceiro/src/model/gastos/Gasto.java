package model.gastos;

import exceptions.CampoVazioException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Categoria;

public class Gasto {
    private static int contadorId = 1;
    protected int id;
    private String nome;
    private double valor;
    private LocalDate data;
    private final Categoria categoria;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Gasto(String nome, double valor, String data, Categoria categoria) throws CampoVazioException {
        if (categoria == null) {
            throw new CampoVazioException("Categoria do gasto");
        }
        this.id = contadorId++;
        setNome(nome);
        setValor(valor);
        setDataFromString(data);
        this.categoria = categoria;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public Categoria getCategoria() { return categoria; }
    public static DateTimeFormatter getDateFormatter() { return DATE_FORMATTER; }

    // Setters com validação
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

    public void setData(LocalDate data) {
        this.data = data;
    }

    private void setDataFromString(String data) {
        try {
            this.data = LocalDate.parse(data, DATE_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de data inválido. Use dd-MM-aaaa");
        }
    }

    public String toFileString() {
        return String.join(";",
            String.valueOf(id),
            nome.replace(";", ","),
            String.format("%.2f", valor),
            data.format(DATE_FORMATTER),
            categoria.getNome().replace(";", ",")
        );
    }

    public static Gasto fromFileString(String linha) {
        String[] partes = linha.split(";", 5);
        if (partes.length != 5) {
            throw new IllegalArgumentException("Formato de linha inválido");
        }

        try {
            int id = Integer.parseInt(partes[0].trim());
            String nome = partes[1].trim();
            double valor = Double.parseDouble(partes[2].trim());
            String dataStr = partes[3].trim();
            
            Categoria categoria = new Categoria(partes[4].trim());
            
            Gasto gasto = new Gasto(nome, valor, dataStr, categoria);
            gasto.id = id;
            if (id >= contadorId) {
                contadorId = id + 1;
            }
            return gasto;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor numérico inválido: " + e.getMessage());
        } catch (CampoVazioException e) {
            throw new IllegalArgumentException("Dados inválidos: " + e.getMessage());
        }
    }

    public static void resetarContador() {
        contadorId = 1;
    }

    @Override
    public String toString() {
        return String.format("Gasto #%d - %s (R$ %.2f em %s) - %s",
            id, nome, valor, data.format(DATE_FORMATTER), categoria.getNome());
    }
}