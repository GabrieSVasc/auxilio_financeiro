package negocio.entidades;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Representa um lembrete para monitorar o progresso de uma meta.
 * 
 * Essa classe estende a classe base Lembrete, associando uma Meta e
 * fornecendo notificações personalizadas baseadas no progresso e prazo da meta.
 * 
 * Demonstra herança, encapsulamento, polimorfismo e uso de coleções para associação.
 * 
 * @author Halina Mochel
 */
public class LembreteMeta extends Lembrete {
    // Meta associada a este lembrete
    private Meta meta;

    /**
     * Construtor para criar um novo LembreteMeta.
     * O título do lembrete é definido como a descrição da meta.
     * A data do alerta é definida como o prazo da meta.
     * 
     * @param meta A meta a ser monitorada
     * @param descricao A descrição do lembrete
     */
    public LembreteMeta(Meta meta, String descricao) {
        super(meta.getDescricao(), descricao, meta.getDataPrazo());
        this.meta = meta;
    }

    /**
     * Construtor para recarregar o objeto a partir de dados persistidos.
     * Busca a meta associada na lista fornecida pelo seu ID.
     * Atualiza o contador de IDs para evitar colisões.
     * 
     * @param id O ID do lembrete
     * @param metaId O ID da meta associada
     * @param descricao A descrição do lembrete
     * @param ativo Estado ativo/inativo do lembrete
     * @param metas Lista de metas para busca da meta associada
     */
    public LembreteMeta(int id, int metaId, String descricao, boolean ativo, List<Meta> metas) {
        // Busca a meta pelo ID; se não encontrada, meta será null (pode causar NullPointerException se não tratado)
        super(descricao, descricao, metas.stream()
                .filter(m -> m.getId() == metaId)
                .findFirst()
                .orElse(null)
                .getDataPrazo());
        this.meta = metas.stream()
                .filter(m -> m.getId() == metaId)
                .findFirst()
                .orElse(null);
        if (id >= contadorId) {
            contadorId = id + 1;
        }
        this.ativo = ativo;
        this.id = id;
    }

    // --- Getters e Setters ---

    public Meta getMeta() { 
        return meta; 
    }
    public void setMeta(Meta meta) { 
        this.meta = meta; 
    }

    /**
     * Gera uma notificação personalizada com o status da meta.
     * Considera progresso, prazo e estado da meta para construir a mensagem.
     * 
     * @return String com a notificação formatada
     */
    @Override
    public String gerarNotificacao() {
        if (meta == null) 
            return "LembreteMeta #" + getId() + " - (meta não encontrada) | " + getDescricao();

        double objetivo = meta.getValorObjetivo();
        double atual = meta.getValorAtual();
        LocalDate prazo = meta.getDataPrazo();

        if (objetivo <= 0) 
            return "Meta inválida para lembrete: " + meta.getDescricao();

        double progresso = (objetivo == 0) ? 0.0 : (atual / objetivo) * 100;
        long diasRestantes = (prazo == null) ? Long.MAX_VALUE : ChronoUnit.DAYS.between(LocalDate.now(), prazo);

        String base = String.format("Meta '%s': %s | R$ %.2f de R$ %.2f (%.0f%%)",
                meta.getDescricao(), getDescricao(), atual, objetivo, progresso);

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

    /**
     * Formata o objeto para ser salvo em arquivo.
     * Inclui o ID do lembrete, ID da meta, descrição e estado ativo.
     * 
     * @return String formatada para persistência
     */
    @Override
    public String toFileString() {
        int metaId = (meta == null ? -1 : meta.getId());
        return getId() + ";" + metaId + ";" + getDescricao().replace(";", ",") + ";" + isAtivo();
    }

    /**
     * Método estático para reconstruir um LembreteMeta a partir de uma linha de arquivo.
     * 
     * @param linha Linha de texto contendo os dados
     * @param metas Lista de metas para associação
     * @return Novo objeto LembreteMeta
     * @throws IllegalArgumentException Caso o formato da linha seja inválido
     */
    public static LembreteMeta fromArquivo(String linha, List<Meta> metas) {
        String[] p = linha.split(";", 4);
        if (p.length < 4) {
            throw new IllegalArgumentException("Formato inválido: " + linha);
        }
        int id = Integer.parseInt(p[0].trim());
        int metaId = Integer.parseInt(p[1].trim());
        String desc = p[2].trim();
        boolean ativo = Boolean.parseBoolean(p[3].trim());
        return new LembreteMeta(id, metaId, desc, ativo, metas);
    }

    /**
     * Representação textual do lembrete, usando a notificação gerada.
     * 
     * @return String com a notificação
     */
    @Override
    public String toString() {
        return gerarNotificacao();
    }
}