package service;
import exceptions.ObjetoNaoEncontradoException;
import exceptions.ObjetoNuloException;
import exceptions.ValorNegativoException;
import java.util.List;
import java.util.Scanner;
import model.Categoria;
import model.Gastos;
import model.Limite;
import util.ConsoleIO;
/** 
 * Serviço para CRUD de Limite via console. 
 * 
 * @author Pedro Farias
 */
public class LimiteManager implements CrudMenu {
    private final List<Limite> limites;
    private final List<Categoria> categorias;
    private LembreteLimiteManager lembreteLimiteManager;
    private final Scanner scanner = new Scanner(System.in);

    public LimiteManager(List<Categoria> categorias, List<Limite> limites) {
        this.categorias = categorias;
        this.limites = limites;
        this.lembreteLimiteManager = lembreteLimiteManager;
    }

     public void atualizarTotais(Categoria cat) {
        if (cat == null) return;

        try {
            List<Gastos> todosGastos = ArquivoGastosManager.listar(categorias);
            double total = todosGastos.stream()
                    .filter(g -> g.getCategoria().getId() == cat.getId())
                    .mapToDouble(Gastos::getValor)
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
                    case "1" -> criar();
                    case "2" -> listar();
                    case "3" -> editar();
                    case "4" -> deletar();
                    case "0" -> System.out.println("Saindo do menu de limites.");
                }
            } catch (Exception e) {
                System.out.println("[ERRO] " + e.getMessage());
            }
        } while (!"0".equals(opcao));
    }

    private void criar() throws ObjetoNaoEncontradoException, ValorNegativoException, ObjetoNuloException {
        if (categorias.isEmpty()) { System.out.println("Crie categorias primeiro."); return; }
        System.out.println("Escolha uma categoria:");
        categorias.forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));
        int idCat = ConsoleIO.readInt(scanner, "ID da categoria: ");
        if (idCat <= 0) throw new ValorNegativoException("ID da categoria");
        Categoria categoriaSelecionada = categorias.stream().filter(c -> c.getId() == idCat).findFirst().orElse(null);
        if (categoriaSelecionada == null) throw new ObjetoNaoEncontradoException("Categoria", idCat);
        double valor = ConsoleIO.readDouble(scanner, "Valor do limite: ");
        Limite l = new Limite(categoriaSelecionada, valor);
        limites.add(l);
        Limite.salvarTodos(limites);
        System.out.println("Limite criado.");
    }

    private void listar() {
        if (limites.isEmpty()) { System.out.println("Nenhum limite cadastrado."); return; }
        limites.forEach(l -> System.out.println(l.exibir()));
    }

    private void editar() throws ObjetoNaoEncontradoException, ValorNegativoException {
        listar(); if (limites.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID do limite a editar: ");
        if (id <= 0) throw new ValorNegativoException("ID");
        Limite limite = limites.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
        if (limite == null) throw new ObjetoNaoEncontradoException("Limite", id);
        double novoValor = ConsoleIO.readDouble(scanner, "Novo valor do limite: ");
        limite.setValor(novoValor);
        Limite.salvarTodos(limites);
        System.out.println("Limite atualizado.");
    }

    private void deletar() throws ObjetoNaoEncontradoException, ValorNegativoException {
        listar(); if (limites.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID do limite a deletar: ");
        if (id <= 0) throw new ValorNegativoException("ID");
        boolean removido = limites.removeIf(l -> l.getId() == id);
        if (!removido) throw new ObjetoNaoEncontradoException("Limite", id);
        Limite.salvarTodos(limites);
        System.out.println("Limite removido.");
    }

    public List<Limite> getLimites() { return limites; }
}