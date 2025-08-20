package main;

import model.Categoria;
import model.gastos.*;
import model.lembretes.*;
import repository.GastoRepository;
import repository.LembreteRepository;
import service.GastoManager;
import service.LembreteManager;
import exceptions.CampoVazioException;
import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // Resetando contadores para testes (opcional)
            Gasto.resetarContador();
            Lembrete.resetarContador();

            // TESTE GASTOS E MENSALIDADES
            System.out.println("\n=== TESTANDO GASTOS ===");
            testarGastos();

            // TESTE LEMBRETES
            System.out.println("\n=== TESTANDO LEMBRETES ===");
            testarLembretes();

            // TESTE INTEGRAÇÃO
            System.out.println("\n=== TESTANDO INTEGRAÇÃO ===");
            testarIntegracao();

        } catch (Exception e) {
            System.err.println("Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testarGastos() throws CampoVazioException, IOException {
        GastoManager gManager = new GastoManager();
        Categoria alimentacao = new Categoria("Alimentação");
        Categoria educacao = new Categoria("Educação");

        // Criando e adicionando gastos
        Gasto compraMercado = new Gasto("Mercado", 150.0, "15-10-2023", alimentacao);
        Gasto mensalidadeEscola = new Gasto("Escola", 800.0, "05-10-2023", educacao);

        gManager.adicionarGasto(compraMercado);
        gManager.adicionarGasto(mensalidadeEscola);

        // Buscando gastos
        System.out.println("Todos os gastos:");
        gManager.listarGastos().forEach(System.out::println);

        // TESTE MENSALIDADE (herança)
        Mensalidade mensalidadeFaculdade = new Mensalidade(
            "Faculdade", 1200.0, "10-10-2023", educacao,
            LocalDate.of(2023, 10, 10), "Mensal"
        );
        gManager.adicionarGasto(mensalidadeFaculdade);
        System.out.println("\nMensalidade adicionada: " + mensalidadeFaculdade);

        // Testando edição
        gManager.editarGasto(mensalidadeFaculdade.getId(), "Faculdade + Materiais", 1350.0, null, null);
        System.out.println("\nApós edição:\n" + gManager.buscarPorId(mensalidadeFaculdade.getId()));

        // Testar serialização
        System.out.println("\nSerialização:\n" + mensalidadeFaculdade.toFileString());
    }

    private static void testarLembretes() throws IOException, CampoVazioException {
        LembreteManager lManager = new LembreteManager();

        // Criando lembretes normais
        Lembrete lembreteConsulta = new Lembrete(
            1, 
            "Consulta Médica", 
            LocalDate.now().plusDays(3)
        );
        lManager.adicionarLembrete(lembreteConsulta);

        // Lembrete especializado (Fatura)
        FaturaLembrete lembreteFatura = new FaturaLembrete(
            2, 
            "Pagamento Fatura Cartão", 
            LocalDate.now().plusDays(5),
            "FAT-12345"
        );
        lManager.adicionarLembrete(lembreteFatura);

        // Listando todos
        System.out.println("\nTodos os lembretes:");
        lManager.listarTodos().forEach(System.out::println);

        // Listando apenas de mensalidades (vazio ainda)
        System.out.println("\nLembretes de mensalidades:");
        lManager.listarLembretesMensalidade().forEach(System.out::println);
    }

    private static void testarIntegracao() throws CampoVazioException, IOException {
        System.out.println("\nTestando integração entre Gasto e Lembrete...");

        GastoManager gManager = new GastoManager();
        LembreteManager lManager = new LembreteManager();

        // Criando mensalidade
        Categoria educacao = new Categoria("Educação");
        Mensalidade mensalidadeEscola = new Mensalidade(
            "Escola", 800.0, "05-11-2023", educacao,
            LocalDate.of(2023, 11, 5), "Mensal"
        );
        gManager.adicionarGasto(mensalidadeEscola);

        // Criando lembrete associado
        MensalidadeLembrete lembreteEscola = new MensalidadeLembrete(
            3, 
            "Pagamento Mensalidade", 
            LocalDate.of(2023, 11, 3), // 2 dias antes
            mensalidadeEscola.getId()
        );
        lManager.adicionarLembrete(lembreteEscola);

        // Demonstrando associação
        System.out.println("\nLembrete criado para mensalidade:");
        System.out.println(lembreteEscola);
        System.out.println("Associado à mensalidade: " + 
            gManager.buscarPorId(lembreteEscola.getIdMensalidade()));

        // Testando persistência
        System.out.println("\nDados persistidos:");
        System.out.println("Último lembrete no arquivo:\n" + 
            LembreteRepository.carregar().getLast());
    }
}
