package repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import negocio.entidades.Gasto;
import negocio.entidades.Mensalidade;

/**
 * Classe de repositório para gerenciar a persistência de objetos Gasto e Mensalidade.
 * Lida com a leitura e escrita de dados em um arquivo de texto.
 */
public class GastoRepository {
    private static final String CAMINHO_ARQUIVO = "files/gastos.txt";

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
    public static void salvar(List<Gasto> gastos) throws IOException {
        garantirArquivo();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Gasto gasto : gastos) {
                writer.write(gasto.toFileString());
                writer.newLine();
            }
        }
    }

    /**
     * Salva uma lista de mensalidades, mantendo os outros gastos.
     * @param mensalidades A lista de mensalidades a ser salva.
     */
    public static void salvarMensalidades(List<Mensalidade> mensalidades) throws IOException {
        List<Gasto> gastos = carregar();
        
        // Remove as mensalidades antigas para adicionar as novas.
        gastos.removeIf(gasto -> gasto instanceof Mensalidade);
        
        // Adiciona as novas mensalidades na lista.
        for (Mensalidade mensalidade : mensalidades) {
            gastos.add(mensalidade);
        }
        
        // Salva a lista completa (gastos e mensalidades).
        salvar(gastos);
    }

    /**
     * Carrega e retorna apenas as mensalidades do arquivo.
     * @return Uma lista de objetos Mensalidade.
     */
    public static List<Mensalidade> carregarMensalidades() throws IOException {
        List<Gasto> gastos = carregar();
        List<Mensalidade> mensalidades = new ArrayList<>();
        
        // Filtra a lista para retornar apenas as instâncias de Mensalidade.
        for (Gasto gasto : gastos) {
            if (gasto instanceof Mensalidade mensalidade) {
                mensalidades.add(mensalidade);
            }
        }
        
        return mensalidades;
    }

    /**
     * Carrega todos os gastos (incluindo mensalidades) do arquivo.
     * @return Uma lista de objetos Gasto.
     */
    public static List<Gasto> carregar() throws IOException {
        garantirArquivo();
        List<Gasto> gastos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                try {
                    // Tenta converter cada linha em um objeto Gasto.
                    Gasto gasto = Gasto.fromFileString(linha);
                    gastos.add(gasto);
                } catch (Exception e) {
                    System.err.println("Erro ao ler linha: " + linha + " - " + e.getMessage());
                }
            }
        }
        return gastos;
    }
}