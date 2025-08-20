package service;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import model.Meta;
import util.ConsoleIO;
/** 
 * Serviço para CRUD de Meta via console. 
 * 
 * @author Pedro Farias
 */
public class MetaManager implements CrudMenu {
    private final List<Meta> metas;
    private final Scanner sc = new Scanner(System.in);

    public MetaManager(List<Meta> metas) {
        this.metas = metas;
    }

    @Override
    public void menu() {
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

    private void criar() {
        String desc = ConsoleIO.readNonEmpty(sc, "Descrição da meta: ");
        double objetivo = ConsoleIO.readDouble(sc, "Valor objetivo: ");
        double atual = ConsoleIO.readDouble(sc, "Valor atual: ");
        LocalDate prazo = ConsoleIO.readLocalDate(sc, "Prazo (dd-MM-yyyy) ou vazio: ");
        Meta m = new Meta(desc, objetivo, atual, prazo);
        metas.add(m);
        Meta.salvarLista(metas);
        System.out.println("Meta criada.");
    }

    private void listar() {
        if (metas.isEmpty()) { System.out.println("Nenhuma meta cadastrada."); return; }
        metas.forEach(System.out::println);
    }

    private void editar() {
        listar(); if (metas.isEmpty()) return;
        int id = ConsoleIO.readInt(sc, "ID da meta: ");
        Meta m = metas.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (m == null) { System.out.println("Meta não encontrada."); return; }

        System.out.println("1 - Alterar descrição");
        System.out.println("2 - Alterar valores");
        System.out.println("3 - Alterar prazo");
        String op = ConsoleIO.readOption(sc, "Escolha: ", "[1-3]");

        switch (op) {
            case "1" -> { String nova = ConsoleIO.readNonEmpty(sc, "Nova descrição: "); m.setDescricao(nova); }
            case "2" -> { m.setValorObjetivo(ConsoleIO.readDouble(sc, "Novo valor objetivo: ")); 
                          m.setValorAtual(ConsoleIO.readDouble(sc, "Novo valor atual: ")); }
            case "3" -> { m.setDataPrazo(ConsoleIO.readLocalDate(sc, "Novo prazo (dd-MM-yyyy) ou vazio: ")); }
        }
        Meta.salvarLista(metas);
        System.out.println("Meta atualizada.");
    }

    private void deletar() {
        listar(); if (metas.isEmpty()) return;
        int id = ConsoleIO.readInt(sc, "ID da meta a deletar: ");
        boolean ok = metas.removeIf(m -> m.getId() == id);
        if (!ok) System.out.println("Meta não encontrada.");
        else { Meta.salvarLista(metas); System.out.println("Meta removida."); }
    }
}