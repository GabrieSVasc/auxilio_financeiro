package negocio.entidades;

import java.time.LocalDate;

/**
 * Representa um lembrete específico para uma fatura.
 * 
 * Essa classe estende a classe base Lembrete, adicionando o atributo número da fatura,
 * e sobrescreve métodos para personalizar a notificação e a persistência.
 * 
 * Demonstra o uso de herança para especializar comportamentos e atributos,
 * além de encapsulamento para proteger os dados internos.
 * 
 * @author Halina Mochel
 */
public class FaturaLembrete extends Lembrete {
    // Número da fatura associada ao lembrete
    private String numeroFatura;
    
    /**
     * Construtor para criação normal de um lembrete de fatura.
     * 
     * @param titulo Título do lembrete
     * @param descricao Descrição do lembrete
     * @param dataAlerta Data do alerta
     * @param numeroFatura Número da fatura associada
     */
    public FaturaLembrete(String titulo, String descricao, LocalDate dataAlerta, String numeroFatura) {
        super(titulo, descricao, dataAlerta);
        this.numeroFatura = numeroFatura;
    }
    
    /**
     * Construtor para recarga a partir de arquivo, incluindo o ID e estado ativo.
     * 
     * @param id Identificador do lembrete
     * @param titulo Título do lembrete
     * @param descricao Descrição do lembrete
     * @param dataCriacao Data de criação do lembrete
     * @param dataAlerta Data do alerta
     * @param ativo Estado ativo/inativo do lembrete
     * @param numeroFatura Número da fatura associada
     */
    public FaturaLembrete(int id, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataAlerta, boolean ativo, String numeroFatura) {
        super(id, titulo, descricao, dataCriacao, dataAlerta, ativo);
        this.numeroFatura = numeroFatura;
    }

    // Getter para o número da fatura
    public String getNumeroFatura() { 
        return numeroFatura; 
    }
    // Setter para o número da fatura
    public void setNumeroFatura(String numeroFatura) { 
        this.numeroFatura = numeroFatura; 
    }

    /**
     * Gera uma notificação formatada específica para lembretes de fatura.
     * Sobrescreve o método da classe base para personalizar a mensagem.
     * 
     * @return String formatada com informações da fatura e data de vencimento
     */
    @Override
    public String gerarNotificacao() {
        return String.format("[FATURA #%s] %s - Vencimento: %s",
                numeroFatura, getTitulo(),
                getDataAlerta().format(DATE_FORMATTER));
    }

    /**
     * Sobrescreve o método toFileString() da classe base para incluir o tipo "FATURA"
     * no início da linha e todos os campos necessários para persistência.
     * 
     * Isso facilita a identificação do tipo ao carregar os dados do arquivo.
     * 
     * @return String formatada para salvar em arquivo
     */
    @Override
    public String toFileString() {
        return String.join(";",
            "FATURA", // Tipo do lembrete para identificação na carga
            String.valueOf(id),
            titulo,
            descricao.replace(";", ","), // Evita conflito com delimitador
            dataCriacao.format(DATE_FORMATTER),
            dataAlerta.format(DATE_FORMATTER),
            String.valueOf(ativo),
            numeroFatura
        );
    }
}