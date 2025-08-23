package negocio.entidades;

import java.time.LocalDate;

import negocio.exceptions.CampoVazioException;

public class Mensalidade extends Gasto {
    private String recorrencia;
    private LocalDate dataVencimento;
    private boolean isPago;
    
    // Construtor principal para criação de novas mensalidades
    public Mensalidade(String nome, double valor, String dataVencimento, Categoria categoria, LocalDate dataCriacao, String recorrencia) throws CampoVazioException {
        super(nome, valor, categoria, dataCriacao);
        setDataVencimento(dataVencimento);
        setRecorrencia(recorrencia);
        this.isPago = false;
    }

    // CONSTRUTOR CORRIGIDO PARA O REPOSITÓRIO:
    // Ele agora aceita o ID, a data de criação e o status 'isPago'.
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
    
    public void setRecorrencia(String recorrencia) throws CampoVazioException {
        if (recorrencia == null || recorrencia.trim().isEmpty()) {
            throw new CampoVazioException("Recorrência");
        }
        this.recorrencia = recorrencia;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }
    
    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = LocalDate.parse(dataVencimento, DATE_FORMATTER);
    }
    
    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isPago() {
        return isPago;
    }
    
    public void setPago(boolean isPago) {
        this.isPago = isPago;
    }

    // --- Métodos de Exibição e de Arquivo ---
    
    @Override
    public String exibir() {
        String statusPagamento = isPago ? "Sim" : "Não";
        return String.format("Mensalidade #%d - %s (R$ %.2f) - Vencimento: %s - Recorrência: %s - Pago: %s",
            this.getId(), this.getNome(), this.getValor(), this.dataVencimento.format(DATE_FORMATTER), this.recorrencia, statusPagamento);
    }
    
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