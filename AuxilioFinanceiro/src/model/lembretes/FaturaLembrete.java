package model.lembretes;

import java.time.LocalDate;

/**
 * Representa um lembrete para uma fatura específica.
 */
public class FaturaLembrete extends Lembrete {
    private String numeroFatura;
    
    // Construtor para criação normal
    public FaturaLembrete(String titulo, String descricao, LocalDate dataAlerta, String numeroFatura) {
        super(titulo, descricao, dataAlerta);
        this.numeroFatura = numeroFatura;
    }
    
    // Construtor para recarga a partir de arquivo
    public FaturaLembrete(int id, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataAlerta, boolean ativo, String numeroFatura) {
        super(id, titulo, descricao, dataCriacao, dataAlerta, ativo);
        this.numeroFatura = numeroFatura;
    }

    public String getNumeroFatura() { return numeroFatura; }
    public void setNumeroFatura(String numeroFatura) { this.numeroFatura = numeroFatura; }

    @Override
    public String gerarNotificacao() {
        return String.format("[FATURA #%s] %s - Vencimento: %s",
                numeroFatura, getTitulo(),
                getDataAlerta().format(DATE_FORMATTER));
    }

    /**
     * CORRIGIDO: Agora o método toFileString() sobrescreve completamente
     * o método da classe base e inclui o tipo no início da linha.
     */
    @Override
    public String toFileString() {
        return String.join(";",
            "FATURA",
            String.valueOf(id),
            titulo,
            descricao.replace(";", ","),
            dataCriacao.format(DATE_FORMATTER),
            dataAlerta.format(DATE_FORMATTER),
            String.valueOf(ativo),
            numeroFatura
        );
    }
}