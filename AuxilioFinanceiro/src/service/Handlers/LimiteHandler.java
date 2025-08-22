package service.Handlers;

import model.lembretes.LembreteLimite;

/**
 * Handler responsável por operações de validação e atualização em LembreteLimite.
 */
public class LimiteHandler {
    /**
     * Valida se um LembreteLimite possui um Limite associado.
     * @param lembrete O LembreteLimite a ser validado.
     */
    public void validar(LembreteLimite lembrete) {
        if (lembrete.getLimite() == null) {
            throw new IllegalArgumentException("Limite associado não pode ser nulo");
        }
    }
    
    /**
     * Atualiza o valor do gasto atual em um LembreteLimite.
     * @param lembrete O LembreteLimite a ser atualizado.
     * @param novoGasto O novo valor do gasto.
     */
    public void atualizarGasto(LembreteLimite lembrete, double novoGasto) {
        lembrete.setGastoAtual(novoGasto);
    }
}