package service;
import exceptions.CampoVazioException;
import exceptions.ObjetoNaoEncontradoException;
import exceptions.ValorNegativoException;
import java.util.List;
import java.util.Scanner;
import model.Meta;
import util.ConsoleIO;
/** Serviço para CRUD de Meta via console. */

public class MetaManager implements CrudMenu {
    private final List<Meta> metas;
    private final Scanner scanner = new Scanner(System.in);

    public MetaManager(List<Meta> metas) {
        this.metas = metas;
    }

    @Override
    public void menu() {
        String opcao;
        do {
            System.out.println("\n--- Menu Meta ---");
            System.out.println("1 - Criar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            opcao = ConsoleIO.readOption(scanner, "Escolha: ", "[0-4]");
            try {
                switch (opcao) {
                    case "1" -> criar();
                    case "2" -> listar();
                    case "3" -> editar();
                    case "4" -> deletar();
                    case "0" -> System.out.println("Saindo do menu de metas.");
                }
            } catch (Exception e) {
                System.out.println("[ERRO] " + e.getMessage());
            }
        } while (!"0".equals(opcao));
    }

    private void criar() throws CampoVazioException, ValorNegativoException {
        String descricao = ConsoleIO.readNonEmpty(scanner, "Descrição da meta: ");
        double valor = ConsoleIO.readDouble(scanner, "Valor da meta: ");
        metas.add(new Meta(valor, descricao));
        System.out.println("Meta adicionada.");
    }

    private void listar() {
        if (metas.isEmpty()) { System.out.println("Nenhuma meta cadastrada."); return; }
        metas.forEach(m -> System.out.println(m.exibir()));
    }

    private void editar() throws ObjetoNaoEncontradoException, CampoVazioException, ValorNegativoException {
        listar(); if (metas.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID da meta a editar: ");
        if (id <= 0) throw new ValorNegativoException("ID");
        Meta meta = metas.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
        if (meta == null) throw new ObjetoNaoEncontradoException("Meta", id);
        String novaDescricao = ConsoleIO.readNonEmpty(scanner, "Nova descrição: ");
        double novoValor = ConsoleIO.readDouble(scanner, "Novo valor: ");
        meta.setDescricao(novaDescricao);
        meta.setValor(novoValor);
        System.out.println("Meta atualizada.");
    }

    private void deletar() throws ObjetoNaoEncontradoException, ValorNegativoException {
        listar(); if (metas.isEmpty()) return;
        int id = ConsoleIO.readInt(scanner, "ID da meta a deletar: ");
        if (id <= 0) throw new ValorNegativoException("ID");
        boolean removido = metas.removeIf(m -> m.getId() == id);
        if (!removido) throw new ObjetoNaoEncontradoException("Meta", id);
        System.out.println("Meta removida.");
    }

    public List<Meta> getMetas() { return metas; }
}
