package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Mensalidade;

/**
 * Classe de repositório para gerenciar a persistência de objetos Gasto e Mensalidade.
 * Lida com a leitura e escrita de dados em um arquivo de texto.
 */
public class MensalidadeRepository {
    private static final String CAMINHO_ARQUIVO = ".\\src/files/mensalidade.txt";

    /**
     * Garante que o diretório e o arquivo de dados existam.
     */
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

    /**
     * Salva uma lista de gastos no arquivo, sobrescrevendo o conteúdo existente.
     * @param gastos A lista de gastos a ser salva.
     */
    public static void salvar(List<Mensalidade> mensalidades) throws IOException {
        garantirArquivo();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Mensalidade mensalidade: mensalidades) {
                writer.write(mensalidade.toFileString());
                writer.newLine();
            }
        }
    }

    /**
     * Carrega todos os gastos (incluindo mensalidades) do arquivo.
     * @return Uma lista de objetos Gasto.
     */
    public static List<Mensalidade> carregar() throws IOException {
        garantirArquivo();
        List<Mensalidade> mensalidades = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                try {
                    // Tenta converter cada linha em um objeto Gasto.
                    Mensalidade mensalidade= Mensalidade.fromFileString(linha);
                    mensalidades.add(mensalidade);
                } catch (Exception e) {
                    System.err.println("Erro ao ler linha: " + linha + " - " + e.getMessage());
                }
            }
        }

        return mensalidades;
    }
}