package model;
import exceptions.CampoVazioException;
import java.util.ArrayList;
import java.util.List;
import util.arquivoUtils;
/**
 * Representa uma categoria de gastos ou metas no sistema financeiro.
 * Uma categoria pode agrupar gastos e limites específicos.
 * * @author Pedro Farias
 */
public class Categoria implements Exibivel {
    private static int contador = 1;
    private final int id;
    private String nome;

    public Categoria(String nome) throws CampoVazioException {
        setNome(nome);
        this.id = contador++;
        arquivoUtils.salvarEmArquivo("categorias.txt", this.id + ";" + this.nome);
    }

    // Construtor para recarga do arquivo
    public Categoria(int id, String nome) throws CampoVazioException {
        setNome(nome);
        this.id = id;
        if (id >= contador) contador = id + 1;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }

    public void setNome(String nome) throws CampoVazioException {
        if (nome == null || nome.trim().isEmpty())
            throw new CampoVazioException("Nome da categoria");
        this.nome = nome.trim();
    }

    @Override
    public String exibir() { return "Categoria #" + id + " - " + nome; }

    public static List<Categoria> carregarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        List<String> linhas = arquivoUtils.lerDoArquivo("categorias.txt");

         if (linhas.isEmpty()) {
            // Criar categorias padrão
            String[] padrao = {"Comida", "Transporte", "Lazer", "Aluguel", "Saúde", "Educação"};
            for (String nome : padrao) {
                try {
                    categorias.add(new Categoria(nome));
                } catch (CampoVazioException e) {
                    System.out.println("Erro ao criar categoria padrão: " + e.getMessage());
                }
            }
            salvarTodas(categorias);
            return categorias;
        }
        
        for (String linha : linhas) {
            try {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                categorias.add(new Categoria(id, nome));
            } catch (Exception e) {
                System.out.println("Erro ao carregar categoria: " + e.getMessage());
            }
        }
        return categorias;
    }

    public static void salvarTodas(List<Categoria> categorias) {
    List<String> linhas = new ArrayList<>();
    for (Categoria c : categorias) {
        linhas.add(c.getId() + ";" + c.getNome());
    }
    arquivoUtils.salvarListaEmArquivo("categorias.txt", linhas);
}
}