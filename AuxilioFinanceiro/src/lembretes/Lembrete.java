package lembretes;

import exceptions.CampoVazioException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Lembrete {
    private static int contadorId = 1;
    protected int id;
    protected String descricao;
    protected LocalDate data;
    protected boolean concluido;

    public Lembrete(String descricao, String data) throws CampoVazioException {
        this.id = contadorId++;
        setDescricao(descricao);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.data = LocalDate.parse(data, formatter);

        this.concluido = false;
    }

    protected Lembrete(int id, String descricao, String data, boolean concluido) throws CampoVazioException {
        this.id = id;
        setDescricao(descricao);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.data = LocalDate.parse(data, formatter);

        this.concluido = concluido;

        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public LocalDate getData() { return data; }
    public boolean isConcluido() { return concluido; }

    public void setDescricao(String descricao) throws CampoVazioException {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new CampoVazioException("A descrição não pode ser vazia.");
        }
        this.descricao = descricao.trim();
    }

    public void marcarConcluido() { this.concluido = true; }

    public String toArquivo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dataStr = data.format(formatter);

        return String.join(";",
            String.valueOf(id),
            descricao.replace(";", ","),
            dataStr,
            String.valueOf(concluido)
        );
    }

    public static Lembrete fromArquivo(String linha) throws CampoVazioException {
        linha = linha.trim();
        String[] partes = linha.split(";");
        if (partes.length != 4) {
            throw new IllegalArgumentException("Linha mal formatada. Esperado 4 campos: " + linha);
        }

        int id = Integer.parseInt(partes[0].trim());
        String descricao = partes[1].trim();
        String dataStr = partes[2].trim();
        boolean concluido = Boolean.parseBoolean(partes[3].trim());

        return new Lembrete(id, descricao, dataStr, concluido);
    }
}