package model;
import exceptions.CampoVazioException;
/** Representa uma categoria para classificação de gastos ou metas. */
public class Categoria implements Exibivel {
    private static int contador = 1;
    private final int id;
    private String nome;
    public Categoria(String nome) throws CampoVazioException { setNome(nome); this.id = contador++; }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) throws CampoVazioException {
        if (nome == null || nome.trim().isEmpty()) throw new CampoVazioException("Nome da categoria");
        this.nome = nome.trim();
    }
    @Override public String exibir() { return "Categoria #" + id + " - " + nome; }
    @Override public String toString() { return exibir(); }
}
