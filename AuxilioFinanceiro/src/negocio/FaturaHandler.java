package negocio;

import negocio.entidades.FaturaLembrete;

/**
 * Handler responsável pela validação de objetos FaturaLembrete.
 */
public class FaturaHandler {
    /**
     * Valida se os dados de um lembrete de fatura estão corretos.
     * @param lembrete O FaturaLembrete a ser validado.
     * @throws IllegalArgumentException Se o número da fatura for nulo ou vazio.
     */
    public void validar(FaturaLembrete lembrete) {
        if (lembrete.getNumeroFatura() == null || lembrete.getNumeroFatura().isBlank()) {
            throw new IllegalArgumentException("Número da fatura é obrigatório");
        }
    }
}