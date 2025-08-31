package negocio.entidades;

import java.time.LocalDate;
import negocio.exceptions.CampoVazioException;

/**
 * Representa uma mensalidade, que é um tipo específico de gasto com recorrência e vencimento.
 * 
 * Essa classe estende a classe Gasto, adicionando atributos para controlar a recorrência,
 * a data de vencimento e o status de pagamento da mensalidade.
 * 
 * Demonstra herança, encapsulamento e tratamento de exceções para garantir integridade dos dados.
 * 
 * @author Halina Mochel
 */
public class Mensalidade extends Gasto {
    // Frequência de pagamento da mensalidade (ex: mensal, anual)
    private String recorrencia;
    // Data de vencimento da mensalidade
    private LocalDate dataVencimento;
    // Indica se a mensalidade já foi paga
    private boolean isPago;
    
    /**
     * Construtor principal para criação de novas mensalidades.
     * Inicializa os atributos herdados e específicos, validando a recorrência.
     * O status de pagamento é inicializado como falso (não pago).
     * 
     * @param nome Nome da mensalidade
     * @param valor Valor da mensalidade
     * @param dataVencimento Data de vencimento no formato String (ex: "2024-06-30")
     * @param categoria Categoria associada
     * @param dataCriacao Data de criação do gasto
     * @param recorrencia Frequência de pagamento (não pode ser vazia)
     * @throws CampoVazioException Se a recorrência for vazia ou nula
     */
    public Mensalidade(String nome, double valor, String dataVencimento, Categoria categoria, LocalDate dataCriacao, String recorrencia) throws CampoVazioException {
        super(nome, valor, categoria, dataCriacao);
        setDataVencimento(dataVencimento);
        setRecorrencia(recorrencia);
        this.isPago = false;
    }

    /**
     * Construtor para recarga a partir do repositório.
     * Aceita o ID, data de criação e status de pagamento para reconstrução do objeto.
     * 
     * @param id Identificador da mensalidade
     * @param nome Nome da mensalidade
     * @param valor Valor da mensalidade
     * @param dataCriacao Data de criação do gasto
     * @param categoria Categoria associada
     * @param dataVencimento Data de vencimento
     * @param recorrencia Frequência de pagamento
     * @param isPago Status de pagamento
     * @throws CampoVazioException Se a recorrência for vazia ou nula
     */
    public Mensalidade(int id, String nome, double valor, LocalDate dataCriacao, Categoria categoria, LocalDate dataVencimento, String recorrencia, boolean isPago) throws CampoVazioException {
        super(id, nome, valor, categoria, dataCriacao);
        this.dataVencimento = dataVencimento;
        setRecorrencia(recorrencia);
        this.isPago = isPago;
    }

    // --- Getters e Setters ---

    public String getRecorrencia() {
        return recorrencia;
    }
    
    /**
     * Define a recorrência da mensalidade, validando que não seja vazia.
     * 
     * @param recorrencia Frequência de pagamento
     * @throws CampoVazioException Se a recorrência for nula ou vazia
     */
    public void setRecorrencia(String recorrencia) throws CampoVazioException {
        if (recorrencia == null || recorrencia.trim().isEmpty()) {
            throw new CampoVazioException("Recorrência");
        }
        this.recorrencia = recorrencia;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }
    
    /**
     * Define a data de vencimento a partir de uma String no formato esperado.
     * 
     * @param dataVencimento Data de vencimento em formato String
     */
    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = LocalDate.parse(dataVencimento, DATE_FORMATTER);
    }
    
    /**
     * Define a data de vencimento diretamente como LocalDate.
     * 
     * @param dataVencimento Data de vencimento
     */
    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isPago() {
        return isPago;
    }
    
    public void setPago(boolean isPago) {
        this.isPago = isPago;
    }

    // --- Métodos de exibição e persistência ---

    /**
     * Retorna uma string formatada para exibir as informações da mensalidade,
     * incluindo status de pagamento.
     * 
     * @return String com detalhes da mensalidade
     */
    @Override
    public String exibir() {
        String statusPagamento = isPago ? "Sim" : "Não";
        return String.format("Mensalidade #%d - %s (R$ %.2f) - Vencimento: %s - Recorrência: %s - Pago: %s",
            this.getId(), this.getNome(), this.getValor(), this.dataVencimento.format(DATE_FORMATTER), this.recorrencia, statusPagamento);
    }
    
    /**
     * Formata os dados da mensalidade para salvar em arquivo,
     * separando os campos por ponto e vírgula.
     * 
     * @return String formatada para persistência
     */
    @Override
    public String toFileString() {
        return String.join(";",
            String.valueOf(id),
            nome,
            String.valueOf(valor),
            dataCriacao.format(DATE_FORMATTER),
            String.valueOf(categoria.getId()),
            dataVencimento.format(DATE_FORMATTER),
            recorrencia,
            String.valueOf(isPago)
        );
    }
}