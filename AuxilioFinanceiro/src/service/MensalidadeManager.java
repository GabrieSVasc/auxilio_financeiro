package service;

import exceptions.CampoVazioException;
import exceptions.ObjetoNaoEncontradoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import model.gastos.Mensalidade;
import repository.MensalidadeRepository;

public class MensalidadeManager {
    private final List<Mensalidade> mensalidades = new ArrayList<>();
    private final CategoriaManager categoriaManager;

    // Construtor corrigido: Agora recebe o CategoriaManager
    public MensalidadeManager(CategoriaManager categoriaManager) {
        this.categoriaManager = categoriaManager;
        carregarMensalidades();
    }
    
    private void carregarMensalidades() {
        try {
            // Passamos a lista de categorias para o repositório
            mensalidades.addAll(MensalidadeRepository.carregar(categoriaManager.getCategorias()));
        } catch (IOException e) {
            System.err.println("Erro ao carregar mensalidades: " + e.getMessage());
        }
    }

    public void adicionarMensalidade(Mensalidade mensalidade) throws IOException, CampoVazioException {
        if (mensalidade == null) {
            throw new CampoVazioException("Mensalidade");
        }
        mensalidades.add(mensalidade);
        MensalidadeRepository.salvar(mensalidades);
    }
    
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

    public void removerMensalidade(int id) throws IOException, ObjetoNaoEncontradoException {
        boolean removido = mensalidades.removeIf(m -> m.getId() == id);
        if (!removido) {
            throw new ObjetoNaoEncontradoException("Mensalidade", id);
        }
        MensalidadeRepository.salvar(mensalidades);
    }

    public List<Mensalidade> listarMensalidades() {
        return new ArrayList<>(mensalidades);
    }

    public Mensalidade buscarPorId(int id) {
        return mensalidades.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}