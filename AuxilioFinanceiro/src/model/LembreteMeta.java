package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/** Lembrete associado a uma Meta. */
public class LembreteMeta extends LembreteBase {
    private static int contadorMeta = 1; // contador próprio de LembreteMeta
    private Meta meta;

    // Criação nova
    public LembreteMeta(Meta meta, String descricao) {
        super(descricao, contadorMeta++);
        this.meta = meta;
    }

    // Recarga do arquivo
    public LembreteMeta(int id, int metaId, String descricao, boolean ativo, List<Meta> metas) {
        super(id, descricao, ativo);
        if (metas != null) {
            this.meta = metas.stream().filter(m -> m.getId() == metaId).findFirst().orElse(null);
        }
        if (id >= contadorMeta) contadorMeta = id + 1;
    }

    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }

    @Override
    public String gerarNotificacao() {
        if (meta == null) return "LembreteMeta #" + id + " - (meta não encontrada) | " + descricao;

        double objetivo = meta.getValorObjetivo();
        double atual = meta.getValorAtual();
        LocalDate prazo = meta.getDataPrazo();

        if (objetivo <= 0) return "Meta inválida para lembrete: " + meta.getDescricao();

        double progresso = (objetivo == 0) ? 0.0 : (atual / objetivo) * 100; // porcentagem
        long diasRestantes = (prazo == null) ? Long.MAX_VALUE : ChronoUnit.DAYS.between(LocalDate.now(), prazo);

        String base = String.format("Meta '%s': %s | R$ %.2f de R$ %.2f (%.0f%%)",
                meta.getDescricao(), descricao, atual, objetivo, progresso);

        if (prazo == null) {
            return base + " - Meta sem Prazo!";
        }

        if (progresso >= 100) {
            return "Meta '" + meta.getDescricao() + "' atingida! " + base;
        } else if (diasRestantes < 0) {
            return "Prazo expirado para '" + meta.getDescricao() + "'. " + base;
        } else if (diasRestantes <= 7) {
            return "Faltam " + diasRestantes + " dias para '" + meta.getDescricao() + "'. " + base + " Vamos lá, você consegue!";
        } else if (progresso >= 80) {
            return base + " - Tá quase lá!";
        } else {
            return base + " - Faltam " + diasRestantes + " dias.";
        }
    }

    /** Serializa para arquivo: id;metaId;descricao;ativo */
    public String toArquivo() {
        int metaId = (meta == null ? -1 : meta.getId());
        return id + ";" + metaId + ";" + descricao.replace(";", ",") + ";" + ativo;
    }

    public static LembreteMeta fromArquivo(String linha, List<Meta> metas) {
        String[] p = linha.split(";", 4);
        if (p.length < 4) throw new IllegalArgumentException("Formato inválido: " + linha);
        int id = Integer.parseInt(p[0].trim());
        int metaId = Integer.parseInt(p[1].trim());
        String desc = p[2].trim();
        boolean ativo = Boolean.parseBoolean(p[3].trim());
        return new LembreteMeta(id, metaId, desc, ativo, metas);
    }

    @Override
    public String toString() {
        return gerarNotificacao();
    }
}
