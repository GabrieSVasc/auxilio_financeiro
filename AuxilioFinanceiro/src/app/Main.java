package app;

import java.util.List;

import dados.CategoriaManager;
import dados.LimiteManager;
import dados.MetaManager;
import negocio.entidades.Categoria;
import negocio.entidades.Limite;
import negocio.entidades.Meta;

public class Main {
    public static void main(String[] args) throws Exception {
        // Carrega dados do arquivo (pasta src/dados criada automaticamente pelo arquivoUtils)
        List<Categoria> categorias = Categoria.carregarCategorias();
        List<Meta> metas = Meta.carregarMetas();
        List<Limite> limites = Limite.carregarLimites(categorias);

        // Cria managers com listas carregadas
        CategoriaManager categoriaManager = new CategoriaManager(categorias);
        MetaManager metaManager = new MetaManager(metas);
        LimiteManager limiteManager = new LimiteManager(categorias, limites);

        java.util.Scanner sc = new java.util.Scanner(System.in);
        String op;

        do {
            System.out.println("\n=== Sistema Financeiro - Demo ===");
            System.out.println("1 - Gerenciar Categorias");
            System.out.println("2 - Gerenciar Metas");
            System.out.println("3 - Gerenciar Limites");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            op = sc.nextLine().trim();

            switch (op) {
                case "1" -> categoriaManager.menu();
                case "2" -> metaManager.menu();
                case "3" -> limiteManager.menu();
                case "0" -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (!"0".equals(op));

        sc.close();
    }
}
