package model.lembretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import model.Meta;

/**
 * Representa um lembrete para monitorar o progresso de uma meta.
 * Herda da classe Lembrete.
 */
public class LembreteMeta extends Lembrete {
    // A meta de gasto associada a este lembrete.
    private Meta meta;

    /**
     * Construtor para criar um novo LembreteMeta.
     * @param meta A meta a ser monitorada.
     * @param descricao A descrição do lembrete.
     */
    public LembreteMeta(Meta meta, String descricao) {
        // Chama o construtor da classe pai (Lembrete) com o título e data da meta.
        super(meta.getDescricao(), descricao, meta.getDataPrazo());
        this.meta = meta;
    }

    /**
     * Construtor para recarregar o objeto a partir de um arquivo.
     * @param id O ID do lembrete.
     * @param metaId O ID da meta associada.
     * @param descricao A descrição do lembrete.
     * @param ativo O estado de ativação do lembrete.
     * @param metas A lista de metas para encontrar a meta associada.
     */
    public LembreteMeta(int id, int metaId, String descricao, boolean ativo, List<Meta> metas) {
        // Encontra a meta na lista fornecida para inicializar a classe pai.
        super(descricao, descricao, metas.stream().filter(m -> m.getId() == metaId).findFirst().orElse(null).getDataPrazo());
        this.meta = metas.stream().filter(m -> m.getId() == metaId).findFirst().orElse(null);
        // Atualiza o contador de IDs para garantir a unicidade.
        if (id >= contadorId) contadorId = id + 1;
    }

    // Métodos de acesso (getters e setters) para a meta.
    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }

    /**
     * Gera a notificação personalizada com o status da meta.
     * @return Uma string com a notificação do progresso da meta.
     */
    @Override
    public String gerarNotificacao() {
        if (meta == null) return "LembreteMeta #" + getId() + " - (meta não encontrada) | " + getDescricao();

        double objetivo = meta.getValorObjetivo();
        double atual = meta.getValorAtual();
        LocalDate prazo = meta.getDataPrazo();

        if (objetivo <= 0) return "Meta inválida para lembrete: " + meta.getDescricao();

        double progresso = (objetivo == 0) ? 0.0 : (atual / objetivo) * 100;
        long diasRestantes = (prazo == null) ? Long.MAX_VALUE : ChronoUnit.DAYS.between(LocalDate.now(), prazo);

        String base = String.format("Meta '%s': %s | R$ %.2f de R$ %.2f (%.0f%%)",
                meta.getDescricao(), getDescricao(), atual, objetivo, progresso);

        if (prazo == null) {
            return base + " - Meta sem Prazo!";
        }

        // Lógica para diferentes cenários de notificação.
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

    /**
     * Formata o objeto para ser salvo em arquivo.
     * @return Uma string com os dados formatados.
     */
    @Override
    public String toFileString() {
        int metaId = (meta == null ? -1 : meta.getId());
        return getId() + ";" + metaId + ";" + getDescricao().replace(";", ",") + ";" + isAtivo();
    }

    /**
     * Método estático para reconstruir o objeto a partir de uma linha de arquivo.
     * @param linha A linha de texto.
     * @param metas A lista de metas para encontrar a meta associada.
     * @return Um novo objeto LembreteMeta.
     */
    public static LembreteMeta fromArquivo(String linha, List<Meta> metas) {
        String[] p = linha.split(";", 4);
        if (p.length < 4) throw new IllegalArgumentException("Formato inválido: " + linha);
        int id = Integer.parseInt(p[0].trim());
        int metaId = Integer.parseInt(p[1].trim());
        String desc = p[2].trim();
        boolean ativo = Boolean.parseBoolean(p[3].trim());
        return new LembreteMeta(id, metaId, desc, ativo, metas);
    }

    /**
     * Sobrescreve toString() para usar a notificação como representação textual.
     */
    @Override
    public String toString() {
        return gerarNotificacao();
    }
}