package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import util.arquivoUtils;

public class Meta {
    private static int contador = 1;

    private final int id;
    private String descricao;
    private double valorObjetivo;
    private double valorAtual;
    private LocalDate dataPrazo; // pode ser null

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String ARQUIVO = "metas.txt";

    public Meta(String descricao, double valorObjetivo, double valorAtual, LocalDate dataPrazo) {
        this.id = contador++;
        this.descricao = descricao;
        this.valorObjetivo = valorObjetivo;
        this.valorAtual = valorAtual;
        this.dataPrazo = dataPrazo;
    }

    public Meta(int id, String descricao, double valorObjetivo, double valorAtual, LocalDate dataPrazo) {
        this.id = id;
        this.descricao = descricao;
        this.valorObjetivo = valorObjetivo;
        this.valorAtual = valorAtual;
        this.dataPrazo = dataPrazo;
        if (id >= contador) contador = id + 1;
    }

    // Getters e setters
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public double getValorObjetivo() { return valorObjetivo; }
    public void setValorObjetivo(double valorObjetivo) { this.valorObjetivo = valorObjetivo; }
    public double getValorAtual() { return valorAtual; }
    public void setValorAtual(double valorAtual) { this.valorAtual = valorAtual; }
    public LocalDate getDataPrazo() { return dataPrazo; }
    public void setDataPrazo(LocalDate dataPrazo) { this.dataPrazo = dataPrazo; }

    /** Serializa para arquivo: id;descricao;valorObjetivo;valorAtual;dataPrazo */
    public String toArquivo() {
        String prazo = (dataPrazo == null ? "" : dataPrazo.format(FORMATO));
        return id + ";" + descricao.replace(";", ",") + ";" + valorObjetivo + ";" + valorAtual + ";" + prazo;
    }

    public static Meta fromArquivo(String linha) {
        String[] p = linha.split(";", 5);
        int id = Integer.parseInt(p[0].trim());
        String desc = p[1].trim();
        double objetivo = Double.parseDouble(p[2].trim());
        double atual = Double.parseDouble(p[3].trim());
        LocalDate prazo = p[4].trim().isEmpty() ? null : LocalDate.parse(p[4].trim(), FORMATO);
        return new Meta(id, desc, objetivo, atual, prazo);
    }

    /** Salva todas as metas no arquivo */
    public static void salvarLista(List<Meta> metas) {
        if (metas.isEmpty()) {
            arquivoUtils.salvarEmArquivo(ARQUIVO, "", false);
            return;
        }
        arquivoUtils.salvarEmArquivo(ARQUIVO, metas.get(0).toArquivo(), false);
        for (int i = 1; i < metas.size(); i++) {
            arquivoUtils.salvarEmArquivo(ARQUIVO, metas.get(i).toArquivo(), true);
        }
    }

    /** Carrega todas as metas do arquivo */
    public static List<Meta> carregarMetas() {
        List<String> linhas = arquivoUtils.lerDoArquivo(ARQUIVO);
        List<Meta> metas = new ArrayList<>();
        for (String ln : linhas) {
            try {
                metas.add(Meta.fromArquivo(ln));
            } catch (Exception e) {
                System.out.println("Falha ao carregar meta: " + e.getMessage());
            }
        }
        return metas;
    }

    @Override
    public String toString() {
        String prazoStr = (dataPrazo == null ? "sem prazo" : dataPrazo.format(FORMATO));
        return id + " - " + descricao + " | R$ " + valorAtual + " / R$ " + valorObjetivo + " | Prazo: " + prazoStr;
    }
}
