package dados;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import negocio.entidades.LembreteLimite;
import negocio.entidades.Limite;
import negocio.exceptions.ObjetoNaoEncontradoException;
import util.ConsoleIO;
import util.arquivoUtils;
/** 
 * Serviço para CRUD de LembreteLimite via console. 
 * 
 * @author Pedro Farias
 */
public class LembreteLimiteManager implements CrudMenu {
    private final List<LembreteLimite> lembretes = new ArrayList<>();
    private final List<Limite> limites;
    private final Scanner scanner = new Scanner(System.in);
    private static final String ARQ = "lembretes_limites.txt";

    public LembreteLimiteManager(List<Limite> limites) {
        this.limites = limites;
        carregar();
    }

    @Override
    public void menu() {
        String op;
        do {
            System.out.println("\n--- Menu Lembretes de Limites ---");
            System.out.println("1 - Criar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar descrição / Ativar-Inativar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            op = ConsoleIO.readOption(scanner, "Escolha: ", "[0-4]");

            try {
                switch (op) {
                    case "1" -> criar();
                    case "2" -> listar();
                    case "3" -> editar();
                    case "4" -> deletar();
                    case "0" -> System.out.println("Saindo do menu de lembretes de limites.");
                }
            } catch (Exception e) {
                System.out.println("[ERRO] " + e.getMessage());
            }

        } while (!"0".equals(op));
    }

    private void criar() throws ObjetoNaoEncontradoException {
        if (limites.isEmpty()) { 
            System.out.println("Crie limites primeiro."); 
            return; 
        }

        System.out.println("Escolha o limite:");
        limites.forEach(l -> {
            String cat = (l.getCategoria() == null ? "(sem categoria)" : l.getCategoria().getNome());
            System.out.println(l.getId() + " - " + cat + " (R$ " + String.format("%.2f", l.getValorLimite()) + ")");
        });

        int idLim = ConsoleIO.readInt(scanner, "ID do limite: ");
        Limite lim = limites.stream().filter(l -> l.getId() == idLim).findFirst().orElse(null);
        if (lim == null) throw new ObjetoNaoEncontradoException("Limite", idLim);

        String desc = ConsoleIO.readNonEmpty(scanner, "Descrição do lembrete: ");
        LembreteLimite ll = new LembreteLimite(lim, desc);
        lembretes.add(ll);
        salvarTudo();
        System.out.println("Lembrete de limite criado.");
    }

    private void listar() {
        if (lembretes.isEmpty()) { 
            System.out.println("Nenhum lembrete de limite."); 
            return; 
        }
        for (LembreteLimite l : lembretes) {
            System.out.println("ID: " + l.getId() + " | " + l.getDescricao() + " | " + l.gerarNotificacao());
        }
    }

    private void editar() throws ObjetoNaoEncontradoException {
        listar(); 
        if (lembretes.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID do lembrete: ");
        LembreteLimite ll = lembretes.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
        if (ll == null) throw new ObjetoNaoEncontradoException("Lembrete de Limite", id);

        System.out.println("1 - Alterar descrição");
        System.out.println("2 - Alternar ativo/inativo");
        String es = ConsoleIO.readOption(scanner, "Escolha: ", "[1-2]");

        if ("1".equals(es)) {
            String nova = ConsoleIO.readNonEmpty(scanner, "Nova descrição: ");
            ll.setDescricao(nova);
        } else {
            ll.setAtivo(!ll.isAtivo());
        }
        salvarTudo();
        System.out.println("Lembrete atualizado.");
    }

    private void deletar() throws ObjetoNaoEncontradoException {
        listar(); 
        if (lembretes.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID do lembrete a deletar: ");
        boolean ok = lembretes.removeIf(l -> l.getId() == id);
        if (!ok) throw new ObjetoNaoEncontradoException("Lembrete de Limite", id);
        salvarTudo();
        System.out.println("Lembrete removido.");
    }

    private void salvarTudo() {
        if (lembretes.isEmpty()) {
            arquivoUtils.salvarEmArquivo(ARQ, "", false);
            return;
        }
        arquivoUtils.salvarEmArquivo(ARQ, lembretes.get(0).toArquivo(), false);
        for (int i = 1; i < lembretes.size(); i++) {
            arquivoUtils.salvarEmArquivo(ARQ, lembretes.get(i).toArquivo(), true);
        }
    }

    private void carregar() {
        List<String> linhas = arquivoUtils.lerDoArquivo(ARQ);
        for (String ln : linhas) {
            try {
                LembreteLimite ll = LembreteLimite.fromArquivo(ln, limites);
                lembretes.add(ll);
            } catch (Exception e) {
                System.out.println("Falha ao carregar lembrete de limite: " + e.getMessage());
            }
        }
    }

    public List<LembreteLimite> getLembretes() { return lembretes; }

    public void atualizarTotalGastos(Limite limite, double total) {
        limite.setTotalGastos(total);
        System.out.println("Status do limite '" + limite.getCategoria().getNome() + "': " + (limite.estaAtingido() ? "ATINGIDO!" : "Ainda não atingido"));
    }

    public void atualizarStatusDeTodos() {
        for (LembreteLimite ll : lembretes) {
            Limite l = ll.getLimite();
            System.out.println("Lembrete: " + ll.getDescricao() + " | "
                + (l.estaAtingido() ? "Limite ATINGIDO!" : "Limite OK"));
        }
    }
}