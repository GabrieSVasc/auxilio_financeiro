package negocio.entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import negocio.exceptions.CampoVazioException;

/**
 * Representa um gasto financeiro.
 * 
 * Essa classe encapsula informações essenciais de um gasto, como nome, valor, data de criação e categoria.
 * Aplica validações para garantir integridade dos dados e oferece métodos para persistência e exibição.
 * 
 * Demonstra o uso de encapsulamento, construtores sobrecarregados, e controle de identidade via contador estático.
 * 
 * @author Halina Mochel
 */
public class Gasto {
    // Contador estático para gerar IDs únicos sequenciais para cada gasto criado
    private static int contadorId = 1;

    // Identificador único do gasto
    protected int id;
    // Nome do gasto
    protected String nome;
    // Valor monetário do gasto
    protected double valor;
    // Data de criação do gasto
    protected LocalDate dataCriacao;
    // Categoria associada ao gasto (imutável após criação)
    protected final Categoria categoria;
    
    // Formato padrão para datas (dd-MM-yyyy)
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Construtor para criação normal de um gasto.
     * Aplica validações para categoria, nome e valor.
     * 
     * @param nome Nome do gasto
     * @param valor Valor do gasto (deve ser maior que zero)
     * @param categoria Categoria associada (não pode ser nula)
     * @param dataCriacao Data de criação do gasto
     * @throws CampoVazioException Caso a categoria ou nome sejam inválidos
     */
    public Gasto(String nome, double valor, Categoria categoria, LocalDate dataCriacao) throws CampoVazioException {
        if (categoria == null) {
            throw new CampoVazioException("Categoria do gasto");
        }
        this.id = contadorId++; // Gera ID sequencial único
        setNome(nome);
        setValor(valor);
        this.dataCriacao = dataCriacao;
        this.categoria = categoria;
    }
    
    /**
     * Construtor usado pelo repositório para recarregar dados do arquivo.
     * Atualiza o contador de IDs para evitar colisões.
     * 
     * @param id Identificador do gasto
     * @param nome Nome do gasto
     * @param valor Valor do gasto
     * @param categoria Categoria associada
     * @param dataCriacao Data de criação
     * @throws CampoVazioException Caso a categoria ou nome sejam inválidos
     */
    public Gasto(int id, String nome, double valor, Categoria categoria, LocalDate dataCriacao) throws CampoVazioException {

        if (categoria == null) {
            throw new CampoVazioException("Categoria do gasto");
        }
        this.id = id;
        if (id >= contadorId) {
            contadorId = id + 1; // Atualiza contador para próximo ID válido
        }
        setNome(nome);
        setValor(valor);
        this.dataCriacao = dataCriacao;
        this.categoria = categoria;
    }

    // --- Getters e Setters ---

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public Categoria getCategoria() { return categoria; }
    
    /**
     * Define o nome do gasto, aplicando validação para não aceitar valores nulos ou vazios.
     * 
     * @param nome Novo nome do gasto
     * @throws CampoVazioException Caso o nome seja nulo ou vazio
     */
    public void setNome(String nome) throws CampoVazioException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new CampoVazioException("Nome do gasto");
        }
        this.nome = nome.trim();
    }
    
    /**
     * Define o valor do gasto, garantindo que seja maior que zero.
     * 
     * @param valor Novo valor do gasto
     * @throws IllegalArgumentException Caso o valor seja menor ou igual a zero
     */
    public void setValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do gasto deve ser maior que zero");
        }
        this.valor = valor;
    }

    /**
     * Define a data de criação do gasto.
     * 
     * @param data Nova data de criação
     */
    public void setDataCriacao(LocalDate data) {
        this.dataCriacao = data;
    }
    
    // --- Métodos de persistência, exibição e utilitários ---

    /**
     * Converte o objeto para uma string formatada para salvar em arquivo.
     * Os campos são separados por ponto e vírgula.
     * 
     * @return String formatada para persistência
     */
    public String toFileString() {
        return String.join(";",
            String.valueOf(id),
            nome,
            String.valueOf(valor),
            dataCriacao.format(DATE_FORMATTER),
            String.valueOf(categoria.getId())
        );
    }
    
    /**
     * Reseta o contador de IDs para 1.
     * Útil para testes ou reinicialização do sistema.
     */
    public static void resetarContador() {
        contadorId = 1;
    }
    
    /**
     * Retorna uma string formatada para exibição amigável do gasto.
     * 
     * @return String descritiva do gasto
     */
    public String exibir() {
        return String.format("Gasto #%d - %s (R$ %.2f em %s) - %s",
            id, nome, valor, dataCriacao.format(DATE_FORMATTER), categoria.getNome());
    }
    
    @Override
    public String toString() {
        return exibir();
    }
}