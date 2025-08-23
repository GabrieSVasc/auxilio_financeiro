package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.exceptions.CampoVazioException;

/**
 * Classe de repositório para gerenciar a persistência de objetos Gasto.
 */
public class GastoRepository {

    private static final String CAMINHO_ARQUIVO = "files/gastos.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static void garantirArquivo() throws IOException {
        Path path = Paths.get(CAMINHO_ARQUIVO);
        Path dir = path.getParent();
        if (dir != null && !Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    public static void salvar(List<Gasto> gastos) throws IOException {
        garantirArquivo();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Gasto gasto : gastos) {
                writer.write(gasto.toFileString());
                writer.newLine();
            }
        }
    }

    // Corrigido para carregar os gastos do arquivo, usando a lista de categorias
    // para reconstruir os objetos Gasto.
    public static List<Gasto> carregar(List<Categoria> categorias) throws IOException, CampoVazioException {
        garantirArquivo();
        List<Gasto> gastos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                try {
                    String[] partes = linha.split(";");
                    if (partes.length >= 5) {
                        int id = Integer.parseInt(partes[0].trim());
                        String nome = partes[1].trim();
                        double valor = Double.parseDouble(partes[2].trim());
                        LocalDate dataCriacao = LocalDate.parse(partes[3].trim(), DATE_FORMATTER);
                        int idCategoria = Integer.parseInt(partes[4].trim());

                        Categoria categoria = categorias.stream()
                            .filter(c -> c.getId() == idCategoria)
                            .findFirst()
                            .orElse(null);

                        if (categoria != null) {
                            Gasto gasto = new Gasto(id, nome, valor, categoria, dataCriacao);
                            gastos.add(gasto);
                        } else {
                            System.err.println("Aviso: Categoria com ID " + idCategoria + " não encontrada para o gasto '" + nome + "'.");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar linha do arquivo: " + linha + " - " + e.getMessage());
                }
            }
        }
        return gastos;
    }
}