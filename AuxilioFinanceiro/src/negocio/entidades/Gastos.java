package negocio.entidades;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Gastos {
    private static int contadorId = 1;
    private int id;
    private String nome;
    private double valor;
    private LocalDate data;
    private Categoria categoria;

    public Gastos(String nome, double valor, String data, Categoria categoria) {
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
        this.data = LocalDate.parse(data, formatter);
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
    public void setData(LocalDate data) { this.data = data; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria do gasto não pode ser nula.");
        }
        this.categoria = categoria;
    }

    public String toArquivo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dataStr = data.format(formatter);
        return id + ";" + nome.replace(";", ",") + ";" + valor + ";" + dataStr + ";" + categoria.getNome();
    }

    public static Gastos fromArquivo(String linha, List<Categoria> categoriasExistentes) {
        String[] partes = linha.split(";");
        if (partes.length != 5) {
            throw new IllegalArgumentException("Linha mal formatada: " + linha);
        }

        int id = Integer.parseInt(partes[0].trim());
        String nome = partes[1].trim();
        double valor = Double.parseDouble(partes[2].trim());
        String dataStr = partes[3].trim();

        String nomeCategoria = partes[4].trim();
        Categoria categoria = categoriasExistentes.stream()
                .filter(c -> c.getNome().equals(nomeCategoria))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + nomeCategoria));

        return new Gastos(id, nome, valor, dataStr, categoria);
    }

    public static void ajustarProximoId(int proximo) {
        if (proximo > contadorId) contadorId = proximo;
    }
}