import java.util.ArrayList;
import java.util.Scanner;

public class CategoriaManager {
    private ArrayList<Categoria> categorias = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        String opcao;
        do {
            System.out.println("\n--- Menu Categoria ---");
            System.out.println("1 - Criar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            System.out.println("Escolha: ");
            opcao = scanner.nextLine();
            scanner.nextLine();

            switch(opcao) {
                case "1" -> criar();
                case "2" -> listar();
                case "3" -> editar();
                case "4" -> deletar();
            }
        } while (!"0".equals(opcao));
    }

    private void criar() {
        System.out.println("Nome da categoria: ");
        String nome = scanner.nextLine();
        categorias.add(new Categoria(nome));
        System.out.println("Categoria adicionada.");
    }

    private void listar() {
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria.");
            return;
        }
        for (Categoria c : categorias) {
            System.out.println(c);
        }
    }

    private void editar() {
        listar();
        System.out.println("ID da categoria a editar: ");
        int id = scanner.nextInt();
scanner.nextLine();
        for (Categorias c : categoria) {
            if (c.getId() == id) {
                System.out.println("Novo nome: ");
                String novoNome = scanner.nextLine();
                c.setNome(novoNome);
                System.out.println("Categoria atualizada.");
                return;
            }
        }
        System.out.println("Categoria não encontrada.");
    }

    private void deletar() {
        listar();
        System.out.println("ID da categoria a deletar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        categoria.removeIf(c -> c.getId() == id);
        System.out.println("Categoria removida.");
    }
}