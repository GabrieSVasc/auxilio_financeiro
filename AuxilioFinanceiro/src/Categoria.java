public class Categoria {
    private static int contador = 1;
    private int id;
    private String nome;

    public Categoria(String nome) {
        this.id = contador++;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Categoria #" + id + " - " + nome;
    }
}