package negocio.entidades;

import java.time.LocalDate;

/**
 * Representa um lembrete específico para monitorar o limite de gastos de uma categoria.
 * 
 * Essa classe estende a classe base Lembrete, adicionando a associação com um objeto Limite
 * e o acompanhamento do gasto atual para gerar notificações relevantes.
 * 
 * Demonstra herança, encapsulamento e polimorfismo ao sobrescrever métodos da classe base.
 * 
 * @author Halina Mochel
 */
public class LembreteLimite extends Lembrete {
    // Limite financeiro associado ao lembrete
    private Limite limite;
    // Valor atual gasto monitorado pelo lembrete
    private double gastoAtual;

    /**
     * Construtor para criação normal do lembrete de limite.
     * O título é gerado automaticamente combinando o nome da categoria e a descrição.
     * Inicializa o gasto atual como zero.
     * 
     * @param limite Objeto Limite associado
     * @param descricao Descrição do lembrete
     * @param dataAlerta Data do alerta
     */
    public LembreteLimite(Limite limite, String descricao, LocalDate dataAlerta) {
        super(limite.getCategoria().getNome() + " - " + descricao, descricao, dataAlerta);
        this.limite = limite;
        this.gastoAtual = 0;
    }

    /**
     * Construtor para recarga a partir de arquivo, incluindo estado e ID.
     * 
     * @param id Identificador do lembrete
     * @param titulo Título do lembrete
     * @param descricao Descrição do lembrete
     * @param dataCriacao Data de criação
     * @param dataAlerta Data do alerta
     * @param ativo Estado ativo/inativo
     * @param limite Objeto Limite associado
     */
    public LembreteLimite(int id, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataAlerta, boolean ativo, Limite limite) {
        super(id, titulo, descricao, dataCriacao, dataAlerta, ativo);
        this.limite = limite;
    }

    // --- Getters e Setters ---

    public Limite getLimite() { 
        return limite; 
    }
    public void setLimite(Limite limite) { 
        this.limite = limite; 
    }
    public double getGastoAtual() { 
        return gastoAtual; 
    }
    /**
     * Define o gasto atual, garantindo que não seja negativo.
     * 
     * @param gastoAtual Valor do gasto atual
     */
    public void setGastoAtual(double gastoAtual) { 
        this.gastoAtual = Math.max(0, gastoAtual); 
    }

    /**
     * Gera uma notificação personalizada baseada no estado do gasto atual em relação ao limite.
     * 
     * - Se o limite não existir, informa que o limite não foi encontrado.
     * - Se o limite for inválido (zero ou negativo), informa erro.
     * - Se o gasto ultrapassar o limite, alerta sobre ultrapassagem.
     * - Se o gasto estiver acima de 80% do limite, alerta sobre proximidade.
     * - Caso contrário, informa que está sob controle.
     * 
     * @return String com a notificação formatada
     */
    @Override
    public String gerarNotificacao() {
        if (limite == null) 
            return "LembreteLimite #" + getId() + " - (limite não encontrado) | " + getDescricao();

        double limiteValor = limite.getValor();
        String cat = (limite.getCategoria() == null ? "(sem categoria)" : limite.getCategoria().getNome());

        if (limiteValor <= 0) 
            return "Limite inválido para categoria " + cat + ".";

        double percentual = gastoAtual / limiteValor;

        if (percentual >= 1.0) {
            return String.format("Ultrapassou o limite de %s: R$ %.2f / R$ %.2f.", cat, gastoAtual, limiteValor);
        } else if (percentual >= 0.8) {
            return String.format("%s em %d%% do limite (R$ %.2f / R$ %.2f).", cat, Math.round(percentual * 100), gastoAtual, limiteValor);
        } else {
            return String.format("%s sob controle: R$ %.2f de R$ %.2f.", cat, limite.getTotalGastos(), limiteValor);
        }
    }

    /**
     * Formata o objeto para ser salvo em arquivo.
     * Inclui o tipo "LIMITE" para identificação e o ID do limite associado.
     * 
     * @return String formatada para persistência
     */
    @Override
    public String toFileString() {
        int limId = (limite == null ? -1 : limite.getId());
        return String.join(";",
            "LIMITE", // Tipo do lembrete para identificação na carga
            String.valueOf(id),
            titulo,
            descricao.replace(";", ","), // Evita conflito com delimitador
            dataCriacao.format(DATE_FORMATTER),
            dataAlerta.format(DATE_FORMATTER),
            String.valueOf(ativo),
            String.valueOf(limId)
        );
    }
}