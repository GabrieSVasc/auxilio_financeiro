package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Limite;
import model.lembretes.Lembrete;
import model.lembretes.LembreteLimite;
import model.lembretes.MensalidadeLembrete;
import negocio.entidades.Mensalidade;

public class LembreteRepository {
    private static final String CAMINHO_ARQUIVO = "files/lembretes.txt";
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

    public static void salvar(List<Lembrete> lembretes) throws IOException {
        garantirArquivo();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO))) {
            for (Lembrete lembrete : lembretes) {
                writer.write(lembrete.toFileString());
                writer.newLine();
            }
        }
    }

    /**
     * Carrega todos os lembretes do arquivo, lidando com suas dependências.
     * @param mensalidades Lista de mensalidades para encontrar a associada ao lembrete.
     * @param limites Lista de limites para encontrar o associado ao lembrete.
     * @return Uma lista de objetos Lembrete.
     * @throws IOException
     */
    public static List<Lembrete> carregar(List<Mensalidade> mensalidades, List<Limite> limites) throws IOException {
        garantirArquivo();
        List<Lembrete> lembretes = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                try {
                    String[] partes = linha.split(";", 8); // Ajustado para 8 partes
                    String tipo = partes[0].trim();
                    int id = Integer.parseInt(partes[1].trim());
                    String titulo = partes[2].trim();
                    String descricao = partes[3].trim();
                    LocalDate dataCriacao = LocalDate.parse(partes[4].trim(), DATE_FORMATTER);
                    LocalDate dataAlerta = LocalDate.parse(partes[5].trim(), DATE_FORMATTER);
                    boolean ativo = Boolean.parseBoolean(partes[6].trim());

                    switch (tipo) {
                        case "LIMITE":
                            int limiteId = Integer.parseInt(partes[7].trim());
                            Optional<Limite> limite = limites.stream().filter(l -> l.getId() == limiteId).findFirst();
                            if (limite.isPresent()) {
                                lembretes.add(new LembreteLimite(id, titulo, descricao, dataCriacao, dataAlerta, ativo, limite.get()));
                            } else {
                                System.err.println("Erro ao carregar LembreteLimite: Limite ID " + limiteId + " não encontrado.");
                            }
                            break;
                        case "MENSALIDADE":
                            int mensalidadeId = Integer.parseInt(partes[7].trim());
                            Optional<Mensalidade> mensalidade = mensalidades.stream().filter(m -> m.getId() == mensalidadeId).findFirst();
                            if (mensalidade.isPresent()) {
                                lembretes.add(new MensalidadeLembrete(id, titulo, descricao, dataCriacao, dataAlerta, ativo, mensalidadeId));
                            } else {
                                System.err.println("Erro ao carregar MensalidadeLembrete: Mensalidade ID " + mensalidadeId + " não encontrada.");
                            }
                            break;
                        // Adicionar outros tipos de lembretes aqui
                        default:
                            System.err.println("Tipo de lembrete desconhecido: " + tipo + " na linha: " + linha);
                            break;
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar lembrete: " + e.getMessage() + " | Linha: " + linha);
                }
            }
        }
        return lembretes;
    }
}