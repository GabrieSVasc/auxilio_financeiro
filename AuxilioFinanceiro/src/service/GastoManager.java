package service;

import exceptions.CampoVazioException;
import exceptions.ValorNegativoException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Categoria;
import model.gastos.Gasto;
import repository.GastoRepository;

public class GastoManager {
    private final List<Gasto> gastos = new ArrayList<>();
    private final CategoriaManager categoriaManager;

    // Construtor corrigido: agora recebe o CategoriaManager
    public GastoManager(CategoriaManager categoriaManager) {
        this.categoriaManager = categoriaManager;
        carregarGastos();
    }
    
    // Método para carregar gastos do arquivo.
    private void carregarGastos() {
        try {
            gastos.addAll(GastoRepository.carregar(categoriaManager.getCategorias()));
        } catch (IOException | CampoVazioException e) {
            System.err.println("Erro ao carregar gastos: " + e.getMessage());
        }
    }

    public void adicionarGasto(Gasto gasto) throws IOException {
        gastos.add(gasto);
        GastoRepository.salvar(gastos);
        System.out.println("Gasto adicionado com sucesso e salvo no arquivo!");
    }

    public boolean editarGasto(int id, String novoNome, Double novoValor, LocalDate novaData, Categoria novaCategoria) throws IOException, CampoVazioException {
        Gasto gasto = buscarPorId(id);
        if (gasto == null) {
            return false;
        }
        if (novoNome != null) {
            gasto.setNome(novoNome);
        }
        if (novoValor != null && novoValor > 0) {
            gasto.setValor(novoValor);
        }
        if (novaData != null) {
            gasto.setDataCriacao(novaData);
        }
        // A categoria não pode ser alterada pois agora é final na classe Gasto
        // A chamada a setCategoria() foi removida.
        
        GastoRepository.salvar(gastos);
        System.out.println("Gasto atualizado com sucesso e salvo no arquivo!");
        return true;
    }

    public List<Gasto> listarGastos() {
        return new ArrayList<>(gastos);
    }

    public List<Gasto> listarGastosPorCategoria(int categoriaId) {
        return gastos.stream()
                .filter(g -> g.getCategoria().getId() == categoriaId)
                .collect(Collectors.toList());
    }

    public boolean removerGasto(int id) throws IOException {
        boolean removido = gastos.removeIf(gasto -> gasto.getId() == id);
        if (removido) {
            GastoRepository.salvar(gastos);
            System.out.println("Gasto removido com sucesso e arquivo atualizado!");
        }
        return removido;
    }

    private Gasto buscarPorId(int id) {
        return gastos.stream()
                .filter(gasto -> gasto.getId() == id)
                .findFirst()
                .orElse(null);
    }
}