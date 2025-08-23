package dados;

import negocio.entidades.LembreteMeta;

/**
 * Handler responsável por validar e atualizar LembreteMeta.
 */
public class MetaHandler {
    
    /**
     * Valida se o lembrete possui uma meta associada.
     * @param lembrete O LembreteMeta a ser validado.
     */
    public void validar(LembreteMeta lembrete) {
        if (lembrete.getMeta() == null) {
            throw new IllegalArgumentException("Meta associada não pode ser nula");
        }
    }

    /**
     * Atualiza o valor do progresso da meta associada.
     * @param lembrete O LembreteMeta a ser atualizado.
     * @param novoProgresso O novo valor do progresso.
     */
    public void atualizarProgresso(LembreteMeta lembrete, double novoProgresso) {
        if (novoProgresso < 0) {
            throw new IllegalArgumentException("Progresso não pode ser negativo");
        }
        // Atualiza o valor na meta real.
        lembrete.getMeta().setValorAtual(novoProgresso);
    }
}