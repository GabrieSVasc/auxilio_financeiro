package dados;

import negocio.entidades.MensalidadeLembrete;

/**
 * Handler responsável pela validação de objetos MensalidadeLembrete.
 * 
 * Essa classe encapsula a lógica de validação específica para objetos do tipo MensalidadeLembrete,
 * promovendo a separação de responsabilidades e facilitando a manutenção e testes.
 * 
 * O uso de um handler dedicado permite centralizar regras de negócio relacionadas à validação,
 * respeitando o princípio da responsabilidade única (SRP).
 * 
 * @author Halina Mochel
 */
public class MensalidadeHandler {
    /**
     * Valida se a data de alerta de uma mensalidade é nula.
     * 
     * Essa validação é importante para garantir que o objeto MensalidadeLembrete
     * possua uma data de alerta válida antes de ser processado ou salvo.
     * 
     * @param lembrete O MensalidadeLembrete a ser validado.
     * @throws IllegalArgumentException Se a data de alerta for nula.
     */
    public void validar(MensalidadeLembrete lembrete) {
        if (lembrete.getDataAlerta() == null) {
            throw new IllegalArgumentException("Data de vencimento é obrigatória");
        }
    }
}