package negocio.entidades;

import java.time.LocalDate;

public class FaturaLembrete extends Lembrete {
    private String numeroFatura;

    public FaturaLembrete(String titulo, String descricao, LocalDate dataAlerta, String numeroFatura) {
        super(titulo, descricao, dataAlerta);
        this.numeroFatura = numeroFatura;
    }

    public String getNumeroFatura() {
        return numeroFatura;
    }

    public void setNumeroFatura(String numeroFatura) {
        this.numeroFatura = numeroFatura;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";FATURA;" + numeroFatura;
    }

    @Override
    public String toString() {
        return super.toString() + " - Fatura: " + numeroFatura;
    }
}
