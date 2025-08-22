package model.lembretes;

import java.time.LocalDate;
import model.Limite;

/**
 * Representa um lembrete para monitorar um limite de gasto.
 */
public class LembreteLimite extends Lembrete {
    private Limite limite;
    private double gastoAtual;

    // Construtor para criação normal
    public LembreteLimite(Limite limite, String descricao, LocalDate dataAlerta) {
        super(limite.getCategoria().getNome() + " - " + descricao, descricao, dataAlerta);
        this.limite = limite;
        this.gastoAtual = 0;
    }

    // Construtor para recarga a partir de arquivo
    public LembreteLimite(int id, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataAlerta, boolean ativo, Limite limite) {
        super(id, titulo, descricao, dataCriacao, dataAlerta, ativo);
        this.limite = limite;
    }

    public Limite getLimite() { return limite; }
    public void setLimite(Limite limite) { this.limite = limite; }
    public double getGastoAtual() { return gastoAtual; }
    public void setGastoAtual(double gastoAtual) { this.gastoAtual = Math.max(0, gastoAtual); }

    @Override
    public String gerarNotificacao() {
        if (limite == null) return "LembreteLimite #" + getId() + " - (limite não encontrado) | " + getDescricao();
        double limiteValor = limite.getValor();
        String cat = (limite.getCategoria() == null ? "(sem categoria)" : limite.getCategoria().getNome());
        if (limiteValor <= 0) return "Limite inválido para categoria " + cat + ".";

        double percentual = gastoAtual / limiteValor;
        if (percentual >= 1.0) {
            return String.format("Ultrapassou o limite de %s: R$ %.2f / R$ %.2f.", cat, gastoAtual, limiteValor);
        } else if (percentual >= 0.8) {
            return String.format("%s em %d%% do limite (R$ %.2f / R$ %.2f).", cat, Math.round(percentual * 100), gastoAtual, limiteValor);
        } else {
            return String.format("%s sob controle: R$ %.2f de R$ %.2f.", cat, limite.getTotalGastos(), limiteValor);
        }
    }

    @Override
    public String toFileString() {
        int limId = (limite == null ? -1 : limite.getId());
        return String.join(";",
            "LIMITE",
            String.valueOf(id),
            titulo,
            descricao.replace(";", ","),
            dataCriacao.format(DATE_FORMATTER),
            dataAlerta.format(DATE_FORMATTER),
            String.valueOf(ativo),
            String.valueOf(limId)
        );
    }
}