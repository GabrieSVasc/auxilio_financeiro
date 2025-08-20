package app;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import model.Categoria;
import model.Gastos;
import model.Limite;
import model.Meta;
import service.ArquivoGastosManager;
import service.CategoriaManager;
import service.LembreteLimiteManager;
import service.LembreteMetaManager;
import service.LimiteManager;
import service.MetaManager;
import util.ConsoleIO;

public class Main {
    public static void main(String[] args) throws Exception {
        // Carrega dados do arquivo
        List<Categoria> categorias = Categoria.carregarCategorias();
        List<Meta> metas = Meta.carregarMetas();
        List<Limite> limites = Limite.carregarLimites(categorias);
        List<Gastos> gastos = ArquivoGastosManager.listar(categorias);

        for (Limite l : limites) {
            double total = gastos.stream()
                            .filter(g -> g.getCategoria().equals(l.getCategoria()))
                            .mapToDouble(Gastos::getValor)
                            .sum();
        l.setTotalGastos(total);
    }

        // Cria managers com listas carregadas
        CategoriaManager categoriaManager = new CategoriaManager(categorias);
        MetaManager metaManager = new MetaManager(metas);
        LimiteManager limiteManager = new LimiteManager(categorias, limites);
        LembreteMetaManager lembreteMetaManager = new LembreteMetaManager(metas);
        LembreteLimiteManager lembreteLimiteManager = new LembreteLimiteManager(limites);

        Scanner sc = new Scanner(System.in);
        String op;

        do {
            System.out.println("\n=== Sistema Financeiro - Demo ===");
            System.out.println("1 - Gerenciar Categorias");
            System.out.println("2 - Gerenciar Metas");
            System.out.println("3 - Gerenciar Limites");
            System.out.println("4 - Gerenciar Lembretes de Metas");
            System.out.println("5 - Gerenciar Lembretes de Limites");
            System.out.println("6 - Gerenciar Gastos");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            op = sc.nextLine().trim();

            switch (op) {
                case "1" -> categoriaManager.menu();
                case "2" -> metaManager.menu();
                case "3" -> limiteManager.menu();
                case "4" -> lembreteMetaManager.menu();
                case "5" -> lembreteLimiteManager.menu();
                case "6" -> menuGastos(sc, categorias, limites, lembreteLimiteManager);
                case "0" -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (!"0".equals(op));

        sc.close();
    }

    private static void menuGastos(Scanner sc, List<Categoria> categorias, List<Limite> limites, LembreteLimiteManager lembreteLimiteManager) {
        String opcao;
        do {
            System.out.println("\n--- Menu Gastos ---");
            System.out.println("1 - Adicionar gasto");
            System.out.println("2 - Listar gastos");
            System.out.println("3 - Editar gasto");
            System.out.println("4 - Remover gasto");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextLine().trim();

            try {
                switch (opcao) {
                    case "1":
                        adicionarGasto(sc, categorias, limites, lembreteLimiteManager);
                        break;
                    case "2":
                        listarGastos(categorias);
                        break;
                    case "3":
                        editarGasto(sc, categorias, limites, lembreteLimiteManager);
                        break;
                    case "4":
                        removerGasto(sc, categorias, limites, lembreteLimiteManager);
                        break;
                    case "0":
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("[ERRO] " + e.getMessage());
            }

        } while (!"0".equals(opcao));
    }

    private static void adicionarGasto(Scanner sc, List<Categoria> categorias, List<Limite> limites, LembreteLimiteManager lembreteLimiteManager) throws Exception {
        if (categorias.isEmpty()) {
            System.out.println("Crie categorias antes de adicionar um gasto.");
            return;
        }

        System.out.println("Escolha a categoria:");
        categorias.forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));
        int idCat = ConsoleIO.readInt(sc, "ID da categoria: ");
        Categoria cat = categorias.stream().filter(c -> c.getId() == idCat).findFirst().orElse(null);
        if (cat == null) {
            System.out.println("Categoria inválida.");
            return;
        }

        String nome = ConsoleIO.readNonEmpty(sc, "Nome do gasto: ");
        double valor = ConsoleIO.readDouble(sc, "Valor: ");
        String dataStr = ConsoleIO.readNonEmpty(sc, "Data (dd-MM-yyyy): ");
        LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Gastos g = new Gastos(nome, valor, dataStr, cat);
        ArquivoGastosManager.adicionar(g, categorias);

        // Atualiza limites vinculados
        for (Limite l : limites) {
            if (l.getCategoria().equals(cat)) {
                double totalGastos = ArquivoGastosManager.listar(categorias).stream()
                        .filter(ga -> ga.getCategoria().equals(cat))
                        .mapToDouble(Gastos::getValor)
                        .sum();
                l.setTotalGastos(totalGastos); // atualiza gasto total
            }
        }

        System.out.println("Gasto adicionado com sucesso!");
    }

    private static void listarGastos(List<Categoria> categorias) {
        List<Gastos> lista = ArquivoGastosManager.listar(categorias);
        if (lista.isEmpty()) {
            System.out.println("Nenhum gasto cadastrado.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Gastos g : lista) {
            String dataFormatada = g.getData().format(formatter);
            System.out.println(g.getId() + " | " + g.getNome() + " | " + g.getValor() + " | " + dataFormatada + " | " + g.getCategoria().getNome());
        }
    }

    private static void editarGasto(Scanner sc, List<Categoria> categorias, List<Limite> limites, LembreteLimiteManager lembreteLimiteManager) throws Exception {
    listarGastos(categorias);
    int id = ConsoleIO.readInt(sc, "ID do gasto a editar: ");
    List<Gastos> lista = ArquivoGastosManager.listar(categorias);
    Gastos g = lista.stream().filter(ga -> ga.getId() == id).findFirst().orElse(null);
    if (g == null) {
        System.out.println("Gasto não encontrado.");
        return;
    }

    // Entrada do nome
    System.out.print("Novo nome (Enter para manter: \"" + g.getNome() + "\"): ");
    String novoNome = sc.nextLine().trim();
    if (novoNome.isEmpty()) novoNome = null;

    // Entrada do valor
    System.out.print("Novo valor (0 para manter: " + g.getValor() + "): ");
    String valorStr = sc.nextLine().trim();
    double novoValor = valorStr.isEmpty() ? 0 : Double.parseDouble(valorStr);

    // Entrada da data
    System.out.print("Nova data (dd-MM-yyyy) (Enter para manter: " + g.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "): ");
    String novaDataStr = sc.nextLine().trim();
    LocalDate novaData = novaDataStr.isEmpty() ? null : LocalDate.parse(novaDataStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    // Escolha da categoria
    System.out.println("Escolha nova categoria (Enter para manter: \"" + g.getCategoria().getNome() + "\"):");
    categorias.forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));
    String catInput = sc.nextLine().trim();
    Categoria novaCat = catInput.isEmpty() ? g.getCategoria() :
            categorias.stream().filter(c -> c.getId() == Integer.parseInt(catInput)).findFirst().orElse(g.getCategoria());

    // Atualiza o gasto
    ArquivoGastosManager.editarCampos(g.getId(), novoNome, novoValor, novaData,novaCat, categorias);

    // Atualiza os limites da categoria
    for (Limite l : limites) {
        if (l.getCategoria().equals(novaCat)) {
            double totalGastos = ArquivoGastosManager.listar(categorias).stream()
                    .filter(ga -> ga.getCategoria().equals(novaCat))
                    .mapToDouble(Gastos::getValor)
                    .sum();
            l.setTotalGastos(totalGastos);
        }
    }

    System.out.println("Gasto editado com sucesso!");
}

    private static void removerGasto(Scanner sc, List<Categoria> categorias, List<Limite> limites, LembreteLimiteManager lembreteLimiteManager) throws Exception {
        listarGastos(categorias);
        int id = ConsoleIO.readInt(sc, "ID do gasto a remover: ");
        List<Gastos> lista = ArquivoGastosManager.listar(categorias);
        Gastos g = lista.stream().filter(ga -> ga.getId() == id).findFirst().orElse(null);
        if (g == null) {
            System.out.println("Gasto não encontrado.");
            return;
        }

        ArquivoGastosManager.removerPorId(id, categorias);

        // Atualiza limites
        for (Limite l : limites) {
            if (l.getCategoria().equals(g.getCategoria())) {
                double totalGastos = ArquivoGastosManager.listar(categorias).stream()
                        .filter(ga -> ga.getCategoria().equals(l.getCategoria()))
                        .mapToDouble(Gastos::getValor)
                        .sum();
                l.setTotalGastos(totalGastos);
            }
        }

        System.out.println("Gasto removido com sucesso!");
    }
}