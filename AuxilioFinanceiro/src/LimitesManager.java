import java.util.ArrayList;
import java.util.Scanner;

public class LimitesManager {
    private ArrayList<Limites> limites = new ArrayList<>();
    private ArrayList<Categoria> categorias;
    private Scanner scanner = new Scanner(System.in);

    public LimiteManager(ArrayList<Categoria>categorias) {
            this.categorias = categorias;
    }

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
        if (categorias.isEmpty()) {
            System.out.println("Crie categorias primeiro.");
            return;
        }
        System.out.println("Escolha uma categoria:");
        for (Categoria c : categorias) {
            System.out.println(c.getId() + " - " + c.getNome());
        }
        int id = scanner.nextInt();
        scanner.nextLine();
        Categoria categoriaSelecionada = null;
            for (Categoria c : categorias) {
                
            }
    }
}
