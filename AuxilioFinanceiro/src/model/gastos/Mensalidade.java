package model.gastos;

import exceptions.CampoVazioException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Categoria;

/**
 * Classe que representa uma mensalidade, que é um tipo de Gasto recorrente.
 */
public class Mensalidade extends Gasto {
    private boolean pago;
    private LocalDate dataVencimento;
    private String recorrencia;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Construtor que cria uma nova Mensalidade.
     * Chama o construtor da classe Gasto para inicializar os atributos herdados.
     */
    public Mensalidade(String nome, double valor, String data, Categoria categoria, LocalDate dataVencimento, String recorrencia) throws CampoVazioException {
        super(nome, valor, data, categoria);
        this.dataVencimento = dataVencimento;
        this.recorrencia = recorrencia;
        this.pago = false;
    }

    // Métodos para acessar e modificar os atributos específicos de Mensalidade.
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

    /**
     * Sobrescreve o método da classe pai para incluir os dados específicos da Mensalidade.
     */
    @Override
    public String toFileString() {
        return super.toFileString() + ";" + pago + ";" + 
                dataVencimento.format(DATE_FORMATTER) + ";" + recorrencia;
    }

    /**
     * Cria um objeto Mensalidade a partir de uma linha de texto salva em arquivo.
     */
    public static Mensalidade fromFileString(String linha) {
        String[] partes = linha.split(";", 8);
        if (partes.length != 8) {
            throw new IllegalArgumentException("Formato de linha inválido para Mensalidade");
        }

        try {
            // Usa o método da classe Gasto para lidar com os atributos comuns.
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

    /**
     * Sobrescreve o método toString() para uma representação textual mais completa.
     */
    @Override
    public String toString() {
        return String.format("Mensalidade #%d - %s (R$ %.2f) - Vencimento: %s - Recorrência: %s - Pago: %s",
            getId(), getNome(), getValor(), dataVencimento.format(DATE_FORMATTER), recorrencia, pago ? "Sim" : "Não");
    }
}