package dados;
import java.util.List;
import java.util.Scanner;

import negocio.entidades.Categoria;
import negocio.entidades.Limite;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;
import negocio.exceptions.ValorNegativoException;
import util.ConsoleIO;
/** Serviço para CRUD de Limite via console. */

public class LimiteManager implements CrudMenu {
    private final List<Limite> limites;
    private final List<Categoria> categorias;
    private final Scanner scanner = new Scanner(System.in);

    public LimiteManager(List<Categoria> categorias, List<Limite> limites) {
        this.categorias = categorias;
        this.limites = limites;
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
        limites.add(new Limite(categoriaSelecionada, valor));
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
        System.out.println("Limite atualizado.");
    }

    private void deletar() throws ObjetoNaoEncontradoException, ValorNegativoException {
        listar(); if (limites.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID do limite a deletar: ");
        if (id <= 0) throw new ValorNegativoException("ID");
        boolean removido = limites.removeIf(l -> l.getId() == id);
        if (!removido) throw new ObjetoNaoEncontradoException("Limite", id);
        System.out.println("Limite removido.");
    }

    public List<Limite> getLimites() { return limites; }
}