package service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.CampoVazioException;
import negocio.entidades.Mensalidade;
import repository.MensalidadeRepository;

/**
 * Gerencia as operações de negócios para as mensalidades.
 * Interage com o GastoRepository para persistir e recuperar os dados de mensalidades.
 */
public class MensalidadeManager {
    /**
     * Adiciona uma nova mensalidade.
     * @param mensalidade O objeto Mensalidade a ser adicionado.
     */
    public void adicionarMensalidade(Mensalidade mensalidade) throws IOException, CampoVazioException {
        List<Mensalidade> mensalidades = new ArrayList<Mensalidade>();
        mensalidades = MensalidadeRepository.carregar();
        mensalidades.add(mensalidade);
        MensalidadeRepository.salvar(mensalidades);
    }

    /**
     * Edita uma mensalidade existente com base no seu ID.
     * @return true se a mensalidade foi encontrada e editada, false caso contrário.
     */
    public boolean editarMensalidade(int id, String novoNome, Double novoValor, LocalDate novaDataVencimento, 
                                     String novaRecorrencia, Boolean novoPago) throws IOException, CampoVazioException {
        List<Mensalidade> mensalidades = MensalidadeRepository.carregar();
        boolean encontrado = false;

        for (Mensalidade mensalidade : mensalidades) {
            if (mensalidade.getId() == id) {
                // Atualiza os atributos apenas se os novos valores não forem nulos.
                if (novoNome != null) mensalidade.setNome(novoNome);
                if (novoValor != null) mensalidade.setValor(novoValor);
                if (novaDataVencimento != null) mensalidade.setDataVencimento(novaDataVencimento);
                if (novaRecorrencia != null) mensalidade.setRecorrencia(novaRecorrencia);
                if (novoPago != null) mensalidade.setPago(novoPago);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            MensalidadeRepository.salvar(mensalidades);
        }
        return encontrado;
    }

    /**
     * Remove uma mensalidade com base no seu ID.
     * @param id O ID da mensalidade a ser removida.
     * @return true se foi removida, false caso contrário.
     */
    public boolean removerMensalidade(int id) throws IOException {
        List<Mensalidade> mensalidades = MensalidadeRepository.carregar();
        boolean removido = mensalidades.removeIf(mensalidade -> mensalidade.getId() == id);
        
        if (removido) {
            MensalidadeRepository.salvar(mensalidades);
        }
        return removido;
    }

    /**
     * Retorna a lista completa de mensalidades.
     * @return Uma lista de objetos Mensalidade.
     */
    public List<Mensalidade> listarMensalidades() throws IOException {
        return MensalidadeRepository.carregar();
    }

    /**
     * Busca uma mensalidade específica por seu ID.
     * @param id O ID da mensalidade a ser buscada.
     * @return O objeto Mensalidade ou null se não for encontrado.
     */
    public Mensalidade buscarPorId(int id) throws IOException {
        return listarMensalidades().stream()
            .filter(mensalidade -> mensalidade.getId() == id)
            .findFirst()
            .orElse(null);
    }
}