package service;
import exceptions.CampoVazioException;
import exceptions.ObjetoNaoEncontradoException;
import exceptions.ValorNegativoException;
import java.util.List;
import java.util.Scanner;
import model.Categoria;
import util.ConsoleIO;
/** 
 * Serviço para CRUD de Categoria via console. 
 * 
 * @author Pedro Farias
 */
public class CategoriaManager implements CrudMenu {
    private final List<Categoria> categorias; // Lista de categorias carregadas no sistema
    private final Scanner scanner = new Scanner(System.in);

    // Recebe lista carregada
    public CategoriaManager(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public void menu() {
        String opcao;
        do {
            System.out.println("\n--- Menu Categoria ---");
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
                    case "0" -> System.out.println("Saindo do menu de categorias.");
                }
            } catch (CampoVazioException | ValorNegativoException | ObjetoNaoEncontradoException e) {
                System.out.println("[ERRO] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[ERRO] Ocorreu um erro inesperado: " + e.getMessage());
            }
        } while (!"0".equals(opcao));
    }

    private void criar() throws CampoVazioException {
        String nome = ConsoleIO.readNonEmpty(scanner, "Nome da categoria: ");
        categorias.add(new Categoria(nome));
        System.out.println("Categoria adicionada.");
    }

    private void listar() {
        if (categorias.isEmpty()) { System.out.println("Nenhuma categoria."); return; }
        categorias.forEach(c -> System.out.println(c.exibir()));
    }

    private void editar() throws ValorNegativoException, ObjetoNaoEncontradoException, CampoVazioException {
        listar(); if (categorias.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID da categoria a editar: ");
        if (id <= 0) throw new ValorNegativoException("ID");
        Categoria encontrada = categorias.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        if (encontrada == null) throw new ObjetoNaoEncontradoException("Categoria", id);
        String novoNome = ConsoleIO.readNonEmpty(scanner, "Novo nome: ");
        encontrada.setNome(novoNome);
        Categoria.salvarTodas(categorias);
        System.out.println("Categoria atualizada.");
    }

    private void deletar() throws ValorNegativoException, ObjetoNaoEncontradoException {
        listar(); if (categorias.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID da categoria a deletar: ");
        if (id <= 0) throw new ValorNegativoException("ID");
        boolean removido = categorias.removeIf(c -> c.getId() == id);
        if (!removido) throw new ObjetoNaoEncontradoException("Categoria", id);
        Categoria.salvarTodas(categorias);
        System.out.println("Categoria removida.");
    }

    public List<Categoria> getCategorias() { return categorias; }
}