package dados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import negocio.entidades.Categoria;
import negocio.entidades.Mensalidade;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;

/**
 * Classe responsável por gerenciar as mensalidades do sistema.
 * 
 * Aplica os conceitos de Orientação a Objetos para encapsular a lista de mensalidades,
 * garantindo controle sobre as operações de adicionar, editar, remover e listar mensalidades.
 * 
 * Utiliza composição ao depender do CategoriaManager para garantir a integridade das categorias associadas.
 * 
 * @author Halina Mochel
 */
public class MensalidadeManager {
    // Lista interna que armazena as mensalidades em memória
    private final List<Mensalidade> mensalidades = new ArrayList<>();
    // Referência para o gerenciador de categorias, para validação e associação correta
    private final CategoriaManager categoriaManager;

    /**
     * Construtor que recebe o gerenciador de categorias e carrega as mensalidades persistidas.
     * Demonstra o princípio da responsabilidade única e a reutilização de componentes.
     * 
     * @param categoriaManager Gerenciador de categorias para associação
     */
    public MensalidadeManager(CategoriaManager categoriaManager) {
        this.categoriaManager = categoriaManager;
        carregarMensalidades();
    }
    
    /**
     * Método privado que carrega as mensalidades do repositório (arquivo).
     * Trata exceções para garantir que falhas na carga não quebrem o sistema.
     */
    private void carregarMensalidades() {
        try {
            // Carrega mensalidades do arquivo, associando categorias existentes
            mensalidades.addAll(MensalidadeRepository.carregar(categoriaManager.getCategorias()));
        } catch (IOException e) {
            System.err.println("Erro ao carregar mensalidades: " + e.getMessage());
        }
    }

    /**
     * Adiciona uma nova mensalidade à lista e persiste a alteração.
     * Aplica validação para evitar inserção de objetos nulos.
     * 
     * @param mensalidade Objeto mensalidade a ser adicionado
     * @throws IOException Caso ocorra erro na persistência
     * @throws CampoVazioException Caso o objeto mensalidade seja nulo
     */
    public void adicionarMensalidade(Mensalidade mensalidade) throws IOException, CampoVazioException {
        if (mensalidade == null) {
            throw new CampoVazioException("Mensalidade");
        }
        mensalidades.add(mensalidade);
        MensalidadeRepository.salvar(mensalidades);
    }
    
    /**
     * Edita uma mensalidade existente identificada pelo id, atualizando os campos fornecidos.
     * Demonstra encapsulamento e controle de acesso aos dados internos.
     * 
     * Nota: A categoria não pode ser alterada pois é final na classe Gasto.
     * 
     * @param id Identificador da mensalidade a ser editada
     * @param novoNome Novo nome para a mensalidade (pode ser null para não alterar)
     * @param novoValor Novo valor para a mensalidade (pode ser null para não alterar)
     * @param novaDataVencimento Nova data de vencimento (pode ser null para não alterar)
     * @param novaCategoria Nova categoria (não utilizada pois categoria é final)
     * @param novaRecorrencia Nova recorrência (pode ser null para não alterar)
     * @param novoStatusPago Novo status de pagamento (pode ser null para não alterar)
     * @throws IOException Caso ocorra erro na persistência
     * @throws ObjetoNaoEncontradoException Caso a mensalidade não seja encontrada
     * @throws CampoVazioException Caso algum campo obrigatório esteja vazio
     */
    public void editarMensalidade(int id, String novoNome, Double novoValor, String novaDataVencimento, Categoria novaCategoria, String novaRecorrencia, Boolean novoStatusPago) throws IOException, ObjetoNaoEncontradoException, CampoVazioException {
        Mensalidade mensalidade = buscarPorId(id);
        if (mensalidade == null) {
            throw new ObjetoNaoEncontradoException("Mensalidade", id);
        }

        if (novoNome != null && !novoNome.trim().isEmpty()) {
            mensalidade.setNome(novoNome);
        }
        if (novoValor != null && novoValor > 0) {
            mensalidade.setValor(novoValor);
        }
        if (novaDataVencimento != null) {
            mensalidade.setDataVencimento(novaDataVencimento);
        }
        // A categoria não pode ser alterada pois agora é final na classe Gasto
        // A chamada a setCategoria() foi removida.
        if (novaRecorrencia != null) {
            mensalidade.setRecorrencia(novaRecorrencia);
        }
        if (novoStatusPago != null) {
            mensalidade.setPago(novoStatusPago);
        }
        MensalidadeRepository.salvar(mensalidades);
    }

    /**
     * Remove uma mensalidade pelo seu identificador e persiste a alteração.
     * Demonstra encapsulamento e controle do ciclo de vida dos objetos.
     * 
     * @param id Identificador da mensalidade a ser removida
     * @throws IOException Caso ocorra erro na persistência
     * @throws ObjetoNaoEncontradoException Caso a mensalidade não seja encontrada
     */
    public void removerMensalidade(int id) throws IOException, ObjetoNaoEncontradoException {
        boolean removido = mensalidades.removeIf(m -> m.getId() == id);
        if (!removido) {
            throw new ObjetoNaoEncontradoException("Mensalidade", id);
        }
        MensalidadeRepository.salvar(mensalidades);
    }

    /**
     * Retorna uma cópia da lista de mensalidades para evitar exposição direta da lista interna.
     * Demonstra encapsulamento e proteção dos dados internos.
     * 
     * @return Lista de mensalidades
     */
    public List<Mensalidade> listarMensalidades() {
        return new ArrayList<>(mensalidades);
    }

    /**
     * Busca uma mensalidade pelo seu identificador.
     * Demonstra encapsulamento e uso de streams para busca eficiente.
     * 
     * @param id Identificador da mensalidade
     * @return Objeto mensalidade encontrado ou null se não existir
     */
    public Mensalidade buscarPorId(int id) {
        return mensalidades.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}