package model.lembretes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe base para todos os tipos de lembretes.
 */
public abstract class Lembrete {
    protected int id;
    protected String titulo;
    protected String descricao;
    protected LocalDate dataCriacao;
    protected LocalDate dataAlerta;
    protected boolean ativo;
    protected static int contadorId = 1;
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Lembrete(String titulo, String descricao, LocalDate dataAlerta) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.dataAlerta = dataAlerta;
        this.ativo = true;
    }

    // Construtor para ser usado pelo repositório ao carregar do arquivo
    public Lembrete(int id, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataAlerta, boolean ativo) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataAlerta = dataAlerta;
        this.ativo = ativo;
        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    // Métodos de acesso
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public LocalDate getDataAlerta() { return dataAlerta; }
    public boolean isAtivo() { return ativo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDataAlerta(LocalDate dataAlerta) { this.dataAlerta = dataAlerta; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    /**
     * Gera a notificação específica para cada tipo de lembrete.
     */
    public abstract String gerarNotificacao();

    /**
     * Formata o objeto para ser salvo em arquivo.
     */
    public abstract String toFileString();

    public static void resetarContador() {
        contadorId = 1;
    }

    public static void resetarContadorPara(int id) {
        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    @Override
    public String toString() {
        return "Lembrete #" + id + ": " + titulo + " - Alerta: " + dataAlerta.format(DATE_FORMATTER) + " (" + (ativo ? "Ativo" : "Inativo") + ")";
    }
}