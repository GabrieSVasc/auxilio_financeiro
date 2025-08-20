package model;
import java.time.LocalDate;
/** Classe base para lembretes. 
*
* @author Pedro Farias 
*/
public abstract class LembreteBase implements Exibivel {
    protected final int id;
    protected String descricao;
    protected boolean ativo;
    protected final LocalDate dataCriacao;

    // Construtor para criação nova (ID definido externamente)
    protected LembreteBase(String descricao, int id) {
        this.id = id;
        this.descricao = (descricao == null ? "" : descricao.trim());
        this.ativo = true;
        this.dataCriacao = LocalDate.now();
    }

    // Construtor para recarga (ID conhecido)
    protected LembreteBase(int id, String descricao, boolean ativo) {
        this.id = id;
        this.descricao = (descricao == null ? "" : descricao.trim());
        this.ativo = ativo;
        this.dataCriacao = LocalDate.now();
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = (descricao == null ? "" : descricao.trim()); }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDate getDataCriacao() { return dataCriacao; }

    /** Subclasses devem implementar a lógica da mensagem/alerta do lembrete. */
    public abstract String gerarNotificacao();

    @Override
    public String exibir() { 
        return gerarNotificacao() + " | Status: " + (ativo ? "Ativo" : "Inativo"); 
    }

    @Override
    public String toString() { return gerarNotificacao(); }
}