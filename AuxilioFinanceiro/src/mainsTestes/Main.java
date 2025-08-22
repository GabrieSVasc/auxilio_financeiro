package mainsTestes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dados.CategoriaManager;
import exceptions.CampoVazioException;
import exceptions.ObjetoNuloException;
import exceptions.ValorNegativoException;
import model.Limite;
import model.lembretes.Lembrete;
import model.lembretes.LembreteLimite;
import model.lembretes.MensalidadeLembrete;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.entidades.Mensalidade;
import service.GastoManager;
import service.LembreteManager;
import service.LimiteManager;
import service.MensalidadeManager;

public class Main {
    public static void main(String[] args) throws ObjetoNuloException, ValorNegativoException {
        Gasto.resetarContador();
        Lembrete.resetarContador();
        
        // A linha a seguir foi removida, pois o método não existe na sua classe Categoria.
        // Categoria.resetarContador(); 

        // Crie a lista de categorias e instancie o CategoriaManager primeiro
        List<Categoria> categorias = new ArrayList<>();
        try {
            categorias.add(new Categoria("Alimentação"));
            categorias.add(new Categoria("Assinaturas"));
        } catch (negocio.exceptions.CampoVazioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        CategoriaManager categoriaManager = new CategoriaManager(categorias);
        
        GastoManager gastoManager = new GastoManager();
        MensalidadeManager mensalidadeManager = null;
		mensalidadeManager = new MensalidadeManager();
        
        // Crie a lista de limites e instancie o LimiteManager corretamente
        List<Limite> limites = new ArrayList<>();
        LimiteManager limiteManager = new LimiteManager(categorias, limites);

        // Agora instancie o LembreteManager com suas dependências
        LembreteManager lembreteManager = new LembreteManager(mensalidadeManager, limiteManager, categoriaManager);

        try {
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
            // Adicione o limite à lista gerenciada pelo LimiteManager
            Limite limiteAlimentacao = new Limite(categoriaManager.getCategorias().get(0), 500.0);
            limites.add(limiteAlimentacao);
            
            LembreteLimite lembreteLimite = new LembreteLimite(limiteAlimentacao, "Limite de Alimentação", LocalDate.now().plusDays(7));
            
            // Atualize o limite diretamente da lista gerenciada pelo LimiteManager
            Iterator<Limite> lI = limiteManager.getLimites().iterator();
            while(lI.hasNext()) {
            	Limite l = lI.next();
            	if(l.getId() == limiteAlimentacao.getId()) {
            		l.setTotalGastos(450.0);
            	}
            }
            
            lembreteManager.criarLembrete(lembreteLimite);
            
            System.out.println("\nLembrete de Limite adicionado e notificação gerada:");
            Iterator<Lembrete> lembrete = lembreteManager.listarTodos().iterator();
            while(lembrete.hasNext()) {
            	Lembrete lem = lembrete.next();
            	if(lem!=null && lem instanceof LembreteLimite) {
            		System.out.println(lem.gerarNotificacao());
            	}
            }
            
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