package repository;

import model.lembretes.Lembrete;
import model.lembretes.FaturaLembrete;
import model.lembretes.MensalidadeLembrete;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class LembreteRepository {
    private static final String CAMINHO_ARQUIVO = "./files/lembretes.txt";

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

    public static void salvar(List<Lembrete> lembretes) throws IOException {
        garantirArquivo();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO))) {
            for (Lembrete lembrete : lembretes) {
                writer.write(lembrete.toFileString());
                writer.newLine();
            }
        }
    }

    public static List<Lembrete> carregar() throws IOException {
        garantirArquivo();
        List<Lembrete> lembretes = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    try {
                        lembretes.add(Lembrete.fromFileString(linha));
                    } catch (Exception e) {
                        System.err.println("Erro ao carregar lembrete: " + e.getMessage());
                    }
                }
            }
        }

        return lembretes;
    }
}