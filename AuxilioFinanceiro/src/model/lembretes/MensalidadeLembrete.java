package model.lembretes;

import java.time.LocalDate;

/**
 * Representa um lembrete específico para mensalidades.
 */
public class MensalidadeLembrete extends Lembrete {
    private int idMensalidade;

    // Construtor para criação normal
    public MensalidadeLembrete(String titulo, String descricao, LocalDate dataAlerta, int idMensalidade) {
        super(titulo, descricao, dataAlerta);
        this.idMensalidade = idMensalidade;
    }
    
    // Construtor para recarga a partir de arquivo
    public MensalidadeLembrete(int id, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataAlerta, boolean ativo, int idMensalidade) {
        super(id, titulo, descricao, dataCriacao, dataAlerta, ativo);
        this.idMensalidade = idMensalidade;
    }

    public int getIdMensalidade() { return idMensalidade; }
    public void setIdMensalidade(int idMensalidade) { this.idMensalidade = idMensalidade; }

    @Override
    public String gerarNotificacao() {
        return String.format("[MENSALIDADE #%d] %s - Vencimento: %s | Valor a pagar: %s",
                idMensalidade, getTitulo(),
                getDataAlerta().format(DATE_FORMATTER),
                getDescricao());
    }

    @Override
    public String toFileString() {
        return String.join(";",
            "MENSALIDADE",
            String.valueOf(id),
            titulo,
            descricao.replace(";", ","),
            dataCriacao.format(DATE_FORMATTER),
            dataAlerta.format(DATE_FORMATTER),
            String.valueOf(ativo),
            String.valueOf(idMensalidade)
        );
    }
}