package model.lembretes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Lembrete {
    private static int contadorId = 1;
    private final int id;
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataAlerta;
    private boolean ativo;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Lembrete(String titulo, String descricao, LocalDate dataAlerta) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.dataAlerta = dataAlerta;
        this.ativo = true;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public LocalDate getDataAlerta() { return dataAlerta; }
    public boolean isAtivo() { return ativo; }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser vazio");
        }
        this.titulo = titulo.trim();
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataAlerta(LocalDate dataAlerta) {
        if (dataAlerta.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de alerta não pode ser no passado");
        }
        this.dataAlerta = dataAlerta;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // Serialização
    public String toFileString() {
        return String.join(";",
            String.valueOf(id),
            titulo,
            descricao,
            dataCriacao.format(DATE_FORMATTER),
            dataAlerta.format(DATE_FORMATTER),
            String.valueOf(ativo)
        );
    }

    public static Lembrete fromFileString(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 6) {
            throw new IllegalArgumentException("Formato de linha inválido");
        }

        try {
            int id = Integer.parseInt(partes[0].trim());
            String titulo = partes[1].trim();
            String descricao = partes[2].trim();
            LocalDate dataCriacao = LocalDate.parse(partes[3].trim(), DATE_FORMATTER);
            LocalDate dataAlerta = LocalDate.parse(partes[4].trim(), DATE_FORMATTER);
            boolean ativo = Boolean.parseBoolean(partes[5].trim());

            // Identifica o tipo de lembrete
            if (partes.length > 6) {
                switch (partes[6].trim()) {
                    case "FATURA":
                        return new FaturaLembrete(titulo, descricao, dataAlerta, partes[7].trim());
                    case "MENSALIDADE":
                        int idMensalidade = Integer.parseInt(partes[7].trim());
                        return new MensalidadeLembrete(titulo, descricao, dataAlerta, idMensalidade);
                    default:
                        throw new IllegalArgumentException("Tipo de lembrete desconhecido");
                }
            }
            throw new IllegalArgumentException("Lembrete genérico não pode ser instanciado");
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao processar linha: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("Lembrete #%d: %s - Alerta: %s (%s)",
            id, titulo, dataAlerta.format(DATE_FORMATTER), ativo ? "Ativo" : "Inativo");
    }

    public static void resetarContador() {
        contadorId = 1;
    }
}
