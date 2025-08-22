package negocio.entidades;

import java.time.LocalDate;

public class MensalidadeLembrete extends Lembrete {
    private int idMensalidade;

    public MensalidadeLembrete(String titulo, String descricao, LocalDate dataAlerta, int idMensalidade) {
        super(titulo, descricao, dataAlerta);
        this.idMensalidade = idMensalidade;
    }

    public int getIdMensalidade() {
        return idMensalidade;
    }

    public void setIdMensalidade(int idMensalidade) {
        this.idMensalidade = idMensalidade;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";MENSALIDADE;" + idMensalidade;
    }

    @Override
    public String toString() {
        return super.toString() + " - Mensalidade ID: " + idMensalidade;
    }
}
