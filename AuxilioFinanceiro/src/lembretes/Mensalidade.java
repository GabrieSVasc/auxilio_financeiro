package lembretes;

import java.time.format.DateTimeFormatter;
import exceptions.CampoVazioException;

public class Mensalidade extends Lembrete {
    private double valor;

    public Mensalidade(String descricao, String data, double valor) throws CampoVazioException {
        super(descricao, data);
        setValor(valor);
    }

    private Mensalidade(int id, String descricao, String data, boolean concluido, double valor) throws CampoVazioException {
        super(id, descricao, data, concluido);
        setValor(valor);
    }

    public double getValor() { return valor; }
    public void setValor(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("O valor da mensalidade deve ser maior que zero.");
        this.valor = valor;
    }

    @Override
    public String toArquivo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dataStr = data.format(formatter);

        return String.join(";",
            String.valueOf(id),
            descricao.replace(";", ","),
            dataStr,
            String.valueOf(concluido),
            String.valueOf(valor)
        );
    }

    public static Mensalidade fromArquivo(String linha) throws CampoVazioException {
        String[] partes = linha.split(";");
        if (partes.length != 5) throw new IllegalArgumentException("Linha mal formatada: " + linha);

        int id = Integer.parseInt(partes[0].trim());
        String descricao = partes[1].trim();
        String data = partes[2].trim();
        boolean concluido = Boolean.parseBoolean(partes[3].trim());
        double valor = Double.parseDouble(partes[4].trim());

        return new Mensalidade(id, descricao, data, concluido, valor);
    }
}
