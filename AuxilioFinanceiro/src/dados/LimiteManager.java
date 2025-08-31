package dados;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.entidades.LembreteLimite;
import negocio.entidades.Limite;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;
import negocio.exceptions.ValorNegativoException;
import util.ConsoleIO;

public class LimiteManager implements CrudMenu {
    private final List<Limite> limites;
    private final List<Categoria> categorias;
    private final GastoManager gastoManager;
    private LembreteManager lembreteManager;
    private final Scanner scanner = new Scanner(System.in);

    public LimiteManager(List<Categoria> categorias, List<Limite> limites, LembreteManager lembreteManager) {
        this.categorias = categorias;
        this.limites = limites;
        this.lembreteManager = lembreteManager;
        this.gastoManager = new GastoManager(new CategoriaManager(categorias));
    }
    
    public void atualizarTotais(Categoria cat) {
        if (cat == null) return;
        try {
            List<Gasto> todosGastos = gastoManager.listarGastos();
            double total = todosGastos.stream()
                .filter(g -> g.getCategoria().getId() == cat.getId())
                .mapToDouble(Gasto::getValor)
                .sum();

            Limite lim = limites.stream()
                .filter(l -> l.getCategoria().getId() == cat.getId())
                .findFirst()
                .orElse(null);

            if (lim != null) {
                lim.setTotalGastos(total);
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar totais da categoria " + cat.getNome() + ": " + e.getMessage());
        }
    }

    public void atualizarLembretesLimite(Limite limite) {
        try {
            List<LembreteLimite> lembretes = lembreteManager.listarLembretesLimite()
                .stream()
                .filter(l -> l.getLimite() != null && l.getLimite().getId() == limite.getId())
                .toList();

            for (LembreteLimite lembrete : lembretes) {
                lembrete.setGastoAtual(limite.getTotalGastos());
                lembreteManager.atualizarLembrete(
                    lembrete.getId(), 
                    lembrete.getTitulo(), 
                    lembrete.getDescricao(), 
                    lembrete.getDataAlerta()
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar lembretes: " + e.getMessage());
        }
    }

    @Override
    public void menu() {
        String opcao;
        do {
            System.out.println("\n--- Menu Limite ---");
            System.out.println("1 - Criar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            opcao = ConsoleIO.readOption(scanner, "Escolha: ", "[0-4]");
            try {
                switch (opcao) {
                    case "1" -> criar(0, 0);
                    case "2" -> listar();
                    case "3" -> editar(0, 0);
                    case "4" -> deletar(0);
                    case "0" -> System.out.println("Saindo do menu de limites.");
                }
            } catch (Exception e) {
                System.out.println("[ERRO] " + e.getMessage());
            }
        } while (!"0".equals(opcao));
    }

    public void criar(int idCat, double valor) throws ObjetoNaoEncontradoException, ValorNegativoException, ObjetoNuloException, IOException, CampoVazioException {
        if (categorias.isEmpty()) { System.out.println("Crie categorias primeiro."); return; }
        if (idCat <= 0) throw new ValorNegativoException("ID da categoria");
        Categoria categoriaSelecionada = categorias.stream().filter(c -> c.getId() == idCat).findFirst().orElse(null);
        if (categoriaSelecionada == null) throw new ObjetoNaoEncontradoException("Categoria", idCat);
        Limite lim = new Limite(categoriaSelecionada, valor);
        limites.add(lim);
        Limite.salvarTodos(limites);
    }

    private void listar() {
        if (limites.isEmpty()) { System.out.println("Nenhum limite cadastrado."); return; }
        limites.forEach(l -> System.out.println(l.exibir()));
    }

    public void editar(int id, double novoValor) throws ObjetoNaoEncontradoException, ValorNegativoException, IOException {
        if (id <= 0) throw new ValorNegativoException("ID");
        Limite limite = limites.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
        if (limite == null) throw new ObjetoNaoEncontradoException("Limite", id);
        limite.setValor(novoValor);
        Limite.salvarTodos(limites);
    }

    public void deletar(int id) throws ObjetoNaoEncontradoException, ValorNegativoException, IOException {
        if (id <= 0) throw new ValorNegativoException("ID");
        boolean removido = limites.removeIf(l -> l.getId() == id);
        if (!removido) throw new ObjetoNaoEncontradoException("Limite", id);
        Limite.salvarTodos(limites);
    }

    public List<Limite> getLimites() { return limites; }
    
    public Limite getLimite(int id) {
    	return limites.stream().filter(l -> l.getId()==id).findFirst().orElse(null);
    }
}