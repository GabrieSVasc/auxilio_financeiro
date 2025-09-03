package negocio.entidades;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Representa uma meta financeira do usuário.
 * Pode ter um valor alvo e acompanhamento de progresso.
 * 
 * @author Pedro Farias
 */
public class Meta {
    private static int contador = 1;
    private final int id;
    private String descricao;
    private double valorObjetivo;
    private double valorAtual;
    private LocalDate dataPrazo;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public double getValorObjetivo() { return valorObjetivo; }
    public void setValorObjetivo(double valorObjetivo) { this.valorObjetivo = valorObjetivo; }
    public double getValorAtual() { return valorAtual; }
    public void setValorAtual(double valorAtual) { this.valorAtual = valorAtual; }
    public LocalDate getDataPrazo() { return dataPrazo; }
    public void setDataPrazo(LocalDate dataPrazo) { this.dataPrazo = dataPrazo; }

    public String toArquivo() {
        String prazo = (dataPrazo == null ? "" : dataPrazo.format(FORMATO));
        return id + ";" + descricao.replace(";", ",") + ";" + valorObjetivo + ";" + valorAtual + ";" + prazo;
    }

    /**
     * Constrói uma Meta a partir de uma linha do arquivo.
     * 
     * @param linha Linha do arquivo
     * @return Objeto Meta correspondente
     */
    public static Meta fromArquivo(String linha) {
        String[] p = linha.split(";", 5);
        int id = Integer.parseInt(p[0].trim());
        String desc = p[1].trim();
        double objetivo = Double.parseDouble(p[2].trim());
        double atual = Double.parseDouble(p[3].trim());
        LocalDate prazo = p[4].trim().isEmpty() ? null : LocalDate.parse(p[4].trim(), FORMATO);
        return new Meta(id, desc, objetivo, atual, prazo);
    }


    

    @Override
    public String toString() {
        String prazoStr = (dataPrazo == null ? "sem prazo" : dataPrazo.format(FORMATO));
        return id + " - " + descricao + " | R$ " + valorAtual + " / R$ " + valorObjetivo + " | Prazo: " + prazoStr;
    }
}