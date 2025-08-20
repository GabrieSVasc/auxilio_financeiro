package repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import model.gastos.Gasto;
import model.gastos.Mensalidade;

public class GastoRepository {
    private static final String CAMINHO_ARQUIVO = "./files/gastos.txt";

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
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO))) {
            for (Gasto gasto : gastos) {
                writer.write(gasto.toFileString());
                writer.newLine();
            }
        }
    }

    public static void salvarMensalidades(List<Mensalidade> mensalidades) throws IOException {
        List<Gasto> gastos = carregar();
        
        // Remove mensalidades existentes
        gastos.removeIf(gasto -> gasto instanceof Mensalidade);
        
        // Adiciona as novas mensalidades
        gastos.addAll(mensalidades);
        
        // Salva tudo
        salvar(gastos);
    }

    public static List<Mensalidade> carregarMensalidades() throws IOException {
        List<Gasto> gastos = carregar();
        List<Mensalidade> mensalidades = new ArrayList<>();
        
        for (Gasto gasto : gastos) {
            if (gasto instanceof Mensalidade mensalidade) {
                mensalidades.add(mensalidade);
            }
        }
        
        return mensalidades;
    }

    public static List<Gasto> carregar() throws IOException {
        garantirArquivo();
        List<Gasto> gastos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                try {
                    // Verifica se é uma mensalidade pelo número de campos
                    if (linha.split(";").length >= 8) {
                        Mensalidade mensalidade = Mensalidade.fromFileString(linha);
                        gastos.add(mensalidade);
                    } else {
                        Gasto gasto = Gasto.fromFileString(linha);
                        gastos.add(gasto);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao ler linha: " + linha + " - " + e.getMessage());
                }
            }
        }

        return gastos;
    }
}
