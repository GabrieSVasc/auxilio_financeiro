package negocio.entidades;

import java.time.LocalDate;

/**
 * Representa um lembrete específico para mensalidades.
 * 
 * Essa classe estende a classe base Lembrete, adicionando o atributo idMensalidade
 * para associar o lembrete a uma mensalidade específica.
 * 
 * Sobrescreve métodos para personalizar a notificação e a persistência.
 * 
 * @author Halina Mochel
 */
public class MensalidadeLembrete extends Lembrete {
    // Identificador da mensalidade associada ao lembrete
    private int idMensalidade;

    /**
     * Construtor para criação normal do lembrete de mensalidade.
     * 
     * @param titulo Título do lembrete
     * @param descricao Descrição do lembrete
     * @param dataAlerta Data do alerta
     * @param idMensalidade ID da mensalidade associada
     */
    public MensalidadeLembrete(String titulo, String descricao, LocalDate dataAlerta, int idMensalidade) {
        super(titulo, descricao, dataAlerta);
        this.idMensalidade = idMensalidade;
    }
    
    /**
     * Construtor para recarga a partir de arquivo, incluindo estado e ID.
     * 
     * @param id ID do lembrete
     * @param titulo Título do lembrete
     * @param descricao Descrição do lembrete
     * @param dataCriacao Data de criação
     * @param dataAlerta Data do alerta
     * @param ativo Estado ativo/inativo
     * @param idMensalidade ID da mensalidade associada
     */
    public MensalidadeLembrete(int id, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataAlerta, boolean ativo, int idMensalidade) {
        super(id, titulo, descricao, dataCriacao, dataAlerta, ativo);
        this.idMensalidade = idMensalidade;
    }

    // --- Getters e Setters ---

    public int getIdMensalidade() { 
        return idMensalidade; 
    }
    public void setIdMensalidade(int idMensalidade) { 
        this.idMensalidade = idMensalidade; 
    }

    /**
     * Gera uma notificação personalizada para o lembrete de mensalidade,
     * incluindo o ID da mensalidade, título, data de vencimento e descrição (valor a pagar).
     * 
     * @return String formatada com a notificação
     */
    @Override
    public String gerarNotificacao() {
        return String.format("[MENSALIDADE #%d] %s - Vencimento: %s | Valor a pagar: %s",
                idMensalidade, getTitulo(),
                getDataAlerta().format(DATE_FORMATTER),
                getDescricao());
    }

    /**
     * Formata o objeto para ser salvo em arquivo.
     * Inclui o tipo "MENSALIDADE" para identificação e o ID da mensalidade associada.
     * 
     * @return String formatada para persistência
     */
    @Override
    public String toFileString() {
        return String.join(";",
            "MENSALIDADE", // Tipo do lembrete para identificação na carga
            String.valueOf(id),
            titulo,
            descricao.replace(";", ","), // Evita conflito com delimitador
            dataCriacao.format(DATE_FORMATTER),
            dataAlerta.format(DATE_FORMATTER),
            String.valueOf(ativo),
            String.valueOf(idMensalidade)
        );
    }
}