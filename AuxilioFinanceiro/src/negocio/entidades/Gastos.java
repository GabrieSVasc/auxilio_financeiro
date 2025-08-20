package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Gastos {
    private static int contadorId = 1;
    private int id;
    private String nome;
    private double valor;
    private LocalDate data;
    private Categoria categoria;

    public Gastos(String nome, double valor, String data, Categoria categoria){
        this.id = contadorId++;
        setNome(nome);
        setValor(valor);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.data = LocalDate.parse(data, formatter);
        setCategoria(categoria);
    }

    private Gastos(int id, String nome, double valor, String data, Categoria categoria) {
        this.id = id;
        setNome(nome);
        setValor(valor);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.data = LocalDate.parse(data, formatter); // Formato consistente
        setCategoria(categoria);
        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do gasto não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public double getValor() { return valor; }
    public void setValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do gasto deve ser maior que zero.");
        }
        this.valor = valor;
    }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria do gasto não pode ser nula.");
        }
        this.categoria = categoria;
    }

    public String toArquivo() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String dataStr = data.format(formatter); // Formata como "21-11-2022"
    
    return String.join(";",
        String.valueOf(id),
        nome.replace(";", ","),
        String.valueOf(valor),
        dataStr, // Data no formato dd-MM-yyyy
        categoria.getNome()
    );
}

public static Gastos fromArquivo(String linha) {
    linha = linha.trim();
    System.out.println("Linha sendo processada: '" + linha + "'");

    String[] partes = linha.split(";");
    if (partes.length != 5) {
        throw new IllegalArgumentException("Linha mal formatada. Esperado 5 campos. Recebido: " + linha);
    }

    try {
        int id = Integer.parseInt(partes[0].trim());
        String nome = partes[1].trim();
        double valor = Double.parseDouble(partes[2].trim());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate data = LocalDate.parse(partes[3].trim(), formatter);
        
        Categoria categoria = new Categoria(partes[4].trim());

        return new Gastos(id, nome, valor, partes[3].trim(), categoria); // Passe a string original da data
    } catch (Exception e) {
        throw new IllegalArgumentException("Falha ao processar linha: '" + linha + "'. Erro: " + e.getMessage());
    }
}

    public static void ajustarProximoId(int proximo) {
        if (proximo > contadorId) {
            contadorId = proximo;
        }
    }
}