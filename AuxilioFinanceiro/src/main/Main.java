package main;

import exceptions.CampoVazioException;
import exceptions.ObjetoNuloException;
import exceptions.ValorNegativoException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import model.Limite;
import model.gastos.Gasto;
import model.gastos.Mensalidade;
import model.lembretes.Lembrete;
import model.lembretes.LembreteLimite;
import model.lembretes.MensalidadeLembrete;
import service.CategoriaManager;
import service.GastoManager;
import service.LembreteManager;
import service.LimiteManager;
import service.MensalidadeManager;

public class Main {
    public static void main(String[] args) throws ObjetoNuloException, ValorNegativoException {
        // Reinicie os contadores para garantir que os IDs comecem do 1 em cada execução de teste.
        Gasto.resetarContador();
        Lembrete.resetarContador();
        Categoria.resetarContador();
        
        try {
            // 1. Instancie o CategoriaManager primeiro, pois ele é uma dependência crucial
            List<Categoria> categorias = new ArrayList<>();
            categorias.add(new Categoria("Alimentação"));
            categorias.add(new Categoria("Assinaturas"));
            CategoriaManager categoriaManager = new CategoriaManager(categorias);

            // 2. Instancie o LembreteManager, pois ele é uma dependência para LimiteManager e MensalidadeManager
            LembreteManager lembreteManager = new LembreteManager(null, null, categoriaManager);
            
            // 3. Agora instancie os outros managers, passando as dependências na ordem correta
            GastoManager gastoManager = new GastoManager(categoriaManager);
            MensalidadeManager mensalidadeManager = new MensalidadeManager(categoriaManager);
            
            List<Limite> limites = new ArrayList<>();
            // O LimiteManager precisa de Limites, CategoriaManager e LembreteManager
           LimiteManager limiteManager = new LimiteManager(categorias, limites, lembreteManager);

            // 4. Conecte os managers que dependem uns dos outros
            lembreteManager.setMensalidadeManager(mensalidadeManager);
            lembreteManager.setLimiteManager(limiteManager);

            // ... (o resto do seu código de teste) ...
            System.out.println("--- Testando GastoManager ---");
            Gasto gasto1 = new Gasto("Compras de Mercado", 150.50, categoriaManager.getCategorias().get(0), LocalDate.now());
            gastoManager.adicionarGasto(gasto1);
            System.out.println("Gasto adicionado: " + gasto1);

            List<Gasto> todosGastos = gastoManager.listarGastos();
            System.out.println("\nGastos na lista:");
            todosGastos.forEach(System.out::println);

            System.out.println("\nEditando gasto com ID 1...");
            boolean editado = gastoManager.editarGasto(1, "Compras de Mercado (Atualizado)", 160.75, null, null);
            System.out.println("Gasto editado? " + editado);
            System.out.println("Gasto atualizado: " + gastoManager.listarGastos().stream().filter(g -> g.getId() == 1).findFirst().orElse(null));

            System.out.println("\n--- Testando MensalidadeManager ---");
            Mensalidade mensalidade1 = new Mensalidade("Netflix", 39.90, "01-08-2025", categoriaManager.getCategorias().get(1), LocalDate.of(2025, 8, 15), "Mensal");
            mensalidadeManager.adicionarMensalidade(mensalidade1);
            System.out.println("Mensalidade adicionada: " + mensalidade1);

            List<Mensalidade> todasMensalidades = mensalidadeManager.listarMensalidades();
            System.out.println("\nMensalidades na lista:");
            todasMensalidades.forEach(System.out::println);

            System.out.println("\n--- Testando LembreteManager ---");
            Limite limiteAlimentacao = new Limite(categoriaManager.getCategorias().get(0), 500.0);
            limites.add(limiteAlimentacao);
            
            // O gasto do limite é atualizado diretamente na lista do manager
            limiteManager.atualizarTotais(limiteAlimentacao.getCategoria());

            LembreteLimite lembreteLimite = new LembreteLimite(limiteAlimentacao, "Limite de Alimentação", LocalDate.now().plusDays(7));
            lembreteManager.criarLembrete(lembreteLimite);
            
            System.out.println("\nLembrete de Limite adicionado e notificação gerada:");
            lembreteManager.listarTodos().stream()
                .filter(l -> l instanceof LembreteLimite)
                .forEach(l -> System.out.println(l.gerarNotificacao()));

            Mensalidade mensalidadeParaLembrete = new Mensalidade("Spotify", 21.90, "01-08-2025", categoriaManager.getCategorias().get(1), LocalDate.of(2025, 8, 25), "Mensal");
            mensalidadeManager.adicionarMensalidade(mensalidadeParaLembrete);
            System.out.println("\nCriando lembrete para a mensalidade Spotify (ID: " + mensalidadeParaLembrete.getId() + ")");
            MensalidadeLembrete lembreteMensalidade = new MensalidadeLembrete("Pagar Spotify", "Valor: R$ 21,90", mensalidadeParaLembrete.getDataVencimento(), mensalidadeParaLembrete.getId());
            lembreteManager.criarLembrete(lembreteMensalidade);
            
            System.out.println("\nTodos os lembretes criados:");
            lembreteManager.listarTodos().forEach(System.out::println);

            System.out.println("\nRemovendo o primeiro gasto...");
            boolean removido = gastoManager.removerGasto(1);
            System.out.println("Gasto removido? " + removido);
            System.out.println("Lista de gastos após remoção:");
            gastoManager.listarGastos().forEach(System.out::println);

        } catch (IOException | CampoVazioException e) {
            System.err.println("Ocorreu um erro no programa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}