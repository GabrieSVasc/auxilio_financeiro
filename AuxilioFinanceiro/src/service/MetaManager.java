package service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import exceptions.CampoVazioException;
import exceptions.ObjetoNaoEncontradoException;
import exceptions.ObjetoNuloException;
import model.Meta;
import model.lembretes.LembreteMeta;
import util.ConsoleIO;

/** 
 * Serviço para CRUD de Meta via console. 
 * 
 * @author Pedro Farias
 */
public class MetaManager implements CrudMenu {
    private final List<Meta> metas;
    private final LembreteManager lembreteManager; // Injeção do LembreteManager
    private final Scanner sc = new Scanner(System.in);

    public MetaManager(List<Meta> metas, LembreteManager lembreteManager) {
        this.metas = metas;
        this.lembreteManager = lembreteManager; // Inicializa o LembreteManager
    }

    @Override
    public void menu() throws CampoVazioException, IOException, ObjetoNuloException, ObjetoNaoEncontradoException {
        String op;
        do {
            System.out.println("\n--- Menu Metas ---");
            System.out.println("1 - Criar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            op = ConsoleIO.readOption(sc, "Escolha: ", "[0-4]");

            switch (op) {
                case "1" -> criar();
                case "2" -> listar();
                case "3" -> editar();
                case "4" -> deletar();
                case "0" -> System.out.println("Saindo do menu de metas.");
            }
        } while (!"0".equals(op));
    }

    private void criar() throws CampoVazioException, IOException, ObjetoNuloException {
        String desc = ConsoleIO.readNonEmpty(sc, "Descrição da meta: ");
        double objetivo = ConsoleIO.readDouble(sc, "Valor objetivo: ");
        double atual = ConsoleIO.readDouble(sc, "Valor atual: ");
        LocalDate prazo = ConsoleIO.readLocalDate(sc, "Prazo (dd-MM-yyyy) ou vazio: ");
        Meta m = new Meta(desc, objetivo, atual, prazo);
        metas.add(m);
        Meta.salvarLista(metas);
        
        // Adiciona lembrete associado à nova meta
        LembreteMeta lembrete = new LembreteMeta(m, "Acompanhamento da meta " + desc);
        lembreteManager.criarLembrete(lembrete);
        System.out.println("Meta criada.");
    }

    private void listar() {
        if (metas.isEmpty()) { 
            System.out.println("Nenhuma meta cadastrada."); 
            return; 
        }
        metas.forEach(System.out::println);
    }

    private void editar() throws ObjetoNaoEncontradoException, IOException, CampoVazioException {
        listar(); 
        if (metas.isEmpty()) return;
        int id = ConsoleIO.readInt(sc, "ID da meta: ");
        Meta m = metas.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (m == null) { 
            System.out.println("Meta não encontrada."); 
            return; 
        }

        System.out.println("1 - Alterar descrição");
        System.out.println("2 - Alterar valores");
        System.out.println("3 - Alterar prazo");
        String op = ConsoleIO.readOption(sc, "Escolha: ", "[1-3]");

        switch (op) {
            case "1" -> { 
                String nova = ConsoleIO.readNonEmpty(sc, "Nova descrição: "); 
                m.setDescricao(nova); 
            }
            case "2" -> { 
                m.setValorObjetivo(ConsoleIO.readDouble(sc, "Novo valor objetivo: ")); 
                m.setValorAtual(ConsoleIO.readDouble(sc, "Novo valor atual: ")); 
            }
            case "3" -> { 
                m.setDataPrazo(ConsoleIO.readLocalDate(sc, "Novo prazo (dd-MM-yyyy) ou vazio: ")); 
            }
        }
        Meta.salvarLista(metas);
        
        // Atualiza lembrete associado à meta
        lembreteManager.atualizarLembrete(m.getId(), "Meta: " + m.getDescricao(), "Acompanhamento da meta " + m.getDescricao(), m.getDataPrazo());
        System.out.println("Meta atualizada.");
    }

    private void deletar() throws ObjetoNaoEncontradoException, IOException {
        listar(); 
        if (metas.isEmpty()) return;
        int id = ConsoleIO.readInt(sc, "ID da meta a deletar: ");
        boolean ok = metas.removeIf(m -> m.getId() == id);
        if (!ok) {
            System.out.println("Meta não encontrada.");
        } else { 
            lembreteManager.removerLembrete(id);
            Meta.salvarLista(metas); 
            System.out.println("Meta removida."); 
        }
    }
}
