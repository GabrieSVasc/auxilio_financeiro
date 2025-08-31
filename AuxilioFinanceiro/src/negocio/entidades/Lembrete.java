package negocio.entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe base para todos os tipos de lembretes.
 * 
 * Essa classe encapsula os atributos e comportamentos comuns a todos os lembretes,
 * como identificação, título, descrição, datas de criação e alerta, e estado ativo.
 * 
 * Aplica o princípio da herança para permitir especializações em subclasses.
 * 
 * @author Halina Mochel
 */
public class Lembrete {
    // Identificador único do lembrete
    protected int id;
    // Título do lembrete
    protected String titulo;
    // Descrição detalhada do lembrete
    protected String descricao;
    // Data em que o lembrete foi criado
    protected LocalDate dataCriacao;
    // Data em que o alerta deve ser disparado
    protected LocalDate dataAlerta;
    // Indica se o lembrete está ativo ou não
    protected boolean ativo;
    // Contador estático para gerar IDs únicos sequenciais
    protected static int contadorId = 1;
    // Formato padrão para datas (dd-MM-yyyy)
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Construtor para criação de um lembrete novo.
     * Atribui um ID sequencial automático e define a data de criação como a data atual.
     * O lembrete é criado como ativo por padrão.
     * 
     * @param titulo Título do lembrete
     * @param descricao Descrição do lembrete
     * @param dataAlerta Data do alerta
     */
    public Lembrete(String titulo, String descricao, LocalDate dataAlerta) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.dataAlerta = dataAlerta;
        this.ativo = true;
    }

    /**
     * Construtor usado pelo repositório para recarregar lembretes a partir de arquivo.
     * Atualiza o contador de IDs para evitar colisões.
     * 
     * @param id Identificador do lembrete
     * @param titulo Título do lembrete
     * @param descricao Descrição do lembrete
     * @param dataCriacao Data de criação
     * @param dataAlerta Data do alerta
     * @param ativo Estado ativo/inativo
     */
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

    // --- Getters e Setters ---

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
     * Pode ser sobrescrito por subclasses para personalizar a mensagem.
     * 
     * @return String com a notificação formatada
     */
    public String gerarNotificacao() {
        return "Lembrete Comum para (" + getDataAlerta() + "): " + getId() + " - " + getDescricao();
    }

    /**
     * Formata o objeto para ser salvo em arquivo.
     * Inclui o tipo do lembrete para facilitar a identificação na carga.
     * Substitui caracteres problemáticos na descrição para evitar erros.
     * 
     * @return String formatada para persistência
     */
    public String toFileString() {
        return String.join(";",
                "LEMBRETE",
                String.valueOf(id),
                titulo,
                descricao.replace(";", ","), // Evita conflito com delimitador
                dataCriacao.format(DATE_FORMATTER),
                dataAlerta.format(DATE_FORMATTER),
                String.valueOf(ativo)
            );
    }

    /**
     * Reseta o contador de IDs para 1.
     * Útil para reinicializar o sistema ou em testes.
     */
    public static void resetarContador() {
        contadorId = 1;
    }

    /**
     * Ajusta o contador de IDs para um valor mínimo, evitando colisões.
     * 
     * @param id Valor mínimo para o contador
     */
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