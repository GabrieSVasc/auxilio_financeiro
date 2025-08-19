package model.gastos;

import model.Categoria;
import exceptions.CampoVazioException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Mensalidade extends Gasto {
    private boolean pago;
    private LocalDate dataVencimento;
    private String recorrencia;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Mensalidade(String nome, double valor, String data, Categoria categoria, LocalDate dataVencimento, String recorrencia) throws CampoVazioException {
        super(nome, valor, data, categoria);
        this.dataVencimento = dataVencimento;
        this.recorrencia = recorrencia;
        this.pago = false;
    }

    // Getters e Setters
    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(String recorrencia) {
        this.recorrencia = recorrencia;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";" + pago + ";" + 
               dataVencimento.format(DATE_FORMATTER) + ";" + recorrencia;
    }

    public static Mensalidade fromFileString(String linha) {
        String[] partes = linha.split(";", 8);
        if (partes.length != 8) {
            throw new IllegalArgumentException("Formato de linha inválido para Mensalidade");
        }

        try {
            Gasto gasto = Gasto.fromFileString(String.join(";", partes[0], partes[1], partes[2], partes[3], partes[4]));
            boolean pago = Boolean.parseBoolean(partes[5].trim());
            LocalDate dataVencimento = LocalDate.parse(partes[6].trim(), DATE_FORMATTER);
            String recorrencia = partes[7].trim();

            Mensalidade mensalidade = new Mensalidade(
                gasto.getNome(),
                gasto.getValor(),
                gasto.getData().format(Gasto.getDateFormatter()),
                gasto.getCategoria(),
                dataVencimento,
                recorrencia
            );
            mensalidade.setPago(pago);
            mensalidade.id = gasto.getId();
            return mensalidade;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao criar Mensalidade: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("Mensalidade #%d - %s (R$ %.2f) - Vencimento: %s - Recorrência: %s - Pago: %s",
            getId(), getNome(), getValor(), dataVencimento.format(DATE_FORMATTER), recorrencia, pago ? "Sim" : "Não");
    }
}