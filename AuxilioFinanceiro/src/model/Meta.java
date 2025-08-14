package model;
import exceptions.CampoVazioException;
import exceptions.ValorNegativoException;
/** Representa uma meta financeira. */
public class Meta extends ObjetivoFinanceiro {
    private String descricao;
    public Meta(double valor, String descricao) throws CampoVazioException, ValorNegativoException {
        super(0); setDescricao(descricao); setValor(valor);
    }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) throws CampoVazioException {
        if (descricao == null || descricao.trim().isEmpty()) throw new CampoVazioException("Descrição da meta");
        this.descricao = descricao.trim();
    }
    @Override public void setValor(double valor) throws ValorNegativoException {
        if (valor <= 0) throw new ValorNegativoException("Valor da meta");
        this.valor = valor;
    }
    @Override public String exibir() { return "Meta #" + id + " - " + descricao + ": R$" + String.format("%.2f", valor); }
}
