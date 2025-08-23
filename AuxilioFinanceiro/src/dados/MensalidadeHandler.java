package dados;

import negocio.entidades.MensalidadeLembrete;

/**
 * Handler responsável pela validação de objetos MensalidadeLembrete.
 */
public class MensalidadeHandler {
    /**
     * Valida se a data de alerta de uma mensalidade é nula.
     * @param lembrete O MensalidadeLembrete a ser validado.
     * @throws IllegalArgumentException Se a data de alerta for nula.
     */
    public void validar(MensalidadeLembrete lembrete) {
        if (lembrete.getDataAlerta() == null) {
            throw new IllegalArgumentException("Data de vencimento é obrigatória");
        }
    }
}