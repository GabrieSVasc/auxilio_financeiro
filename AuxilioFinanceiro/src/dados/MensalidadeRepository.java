package dados;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Categoria;
import negocio.entidades.Mensalidade;
import util.arquivoUtils;

public class MensalidadeRepository {

    private static final String ARQUIVO_MENSALIDADES = "mensalidades.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void salvar(List<Mensalidade> mensalidades) throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Mensalidade m : mensalidades) {
            linhas.add(m.toFileString());
        }
        arquivoUtils.salvarListaEmArquivo(ARQUIVO_MENSALIDADES, linhas);
    }

    // Método corrigido para receber a lista de categorias e o CategoriaManager
    public static List<Mensalidade> carregar(List<Categoria> categorias) throws IOException {
        List<Mensalidade> mensalidades = new ArrayList<>();
        List<String> linhas = arquivoUtils.lerDoArquivo(ARQUIVO_MENSALIDADES);

        if (linhas.isEmpty()) {
            return mensalidades;
        }

        for (String linha : linhas) {
            try {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                double valor = Double.parseDouble(partes[2]);
                LocalDate dataCriacao = LocalDate.parse(partes[3], DATE_FORMATTER);
                int idCategoria = Integer.parseInt(partes[4]);
                LocalDate dataVencimento = LocalDate.parse(partes[5], DATE_FORMATTER);
                String recorrencia = partes[6];
                boolean isPago = Boolean.parseBoolean(partes[7]);

                Categoria categoria = categorias.stream()
                    .filter(c -> c.getId() == idCategoria)
                    .findFirst()
                    .orElse(null);

                if (categoria != null) {
                    mensalidades.add(new Mensalidade(id, nome, valor, dataCriacao, categoria, dataVencimento, recorrencia, isPago));
                } else {
                    System.err.println("Aviso: Categoria com ID " + idCategoria + " não encontrada para a mensalidade '" + nome + "'.");
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar mensalidade da linha: " + linha + " - Erro: " + e.getMessage());
            }
        }
        return mensalidades;
    }
}