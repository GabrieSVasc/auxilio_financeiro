package lembretes;

import java.time.format.DateTimeFormatter;
import exceptions.CampoVazioException;
import model.Categoria;

public class Fatura extends Lembrete {
    private double valor;
    private Categoria categoria;

    public Fatura(String descricao, String data, double valor, Categoria categoria) throws CampoVazioException {
        super(descricao, data);
        setValor(valor);
        setCategoria(categoria);
    }

    private Fatura(int id, String descricao, String data, boolean concluido, double valor, Categoria categoria) throws CampoVazioException {
        super(id, descricao, data, concluido);
        setValor(valor);
        setCategoria(categoria);
    }

    public double getValor() { return valor; }
    public void setValor(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("O valor da fatura deve ser maior que zero.");
        this.valor = valor;
    }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) throws CampoVazioException {
        if (categoria == null) throw new CampoVazioException("A categoria não pode ser nula.");
        this.categoria = categoria;
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
            String.valueOf(valor),
            categoria.getNome()
        );
    }

    public static Fatura fromArquivo(String linha) throws CampoVazioException {
        String[] partes = linha.split(";");
        if (partes.length != 6) throw new IllegalArgumentException("Linha mal formatada: " + linha);

        int id = Integer.parseInt(partes[0].trim());
        String descricao = partes[1].trim();
        String data = partes[2].trim();
        boolean concluido = Boolean.parseBoolean(partes[3].trim());
        double valor = Double.parseDouble(partes[4].trim());
        Categoria categoria = new Categoria(partes[5].trim());

        return new Fatura(id, descricao, data, concluido, valor, categoria);
    }
}
