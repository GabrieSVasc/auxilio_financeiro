package service;

import exceptions.ObjetoNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.LembreteMeta;
import model.Meta;
import util.ConsoleIO;
import util.arquivoUtils;

public class LembreteMetaManager implements CrudMenu {
    private final List<LembreteMeta> lembretes = new ArrayList<>();
    private final List<Meta> metas;
    private final Scanner sc = new Scanner(System.in);
    private static final String ARQ = "lembretes_meta.txt";

    public LembreteMetaManager(List<Meta> metas) {
        this.metas = metas;
        carregar();
    }

    @Override
    public void menu() {
        String op;
        do {
            System.out.println("\n--- Menu Lembretes de Metas ---");
            System.out.println("1 - Criar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar descrição / Ativar-Inativar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            op = ConsoleIO.readOption(sc, "Escolha: ", "[0-4]");

            try {
                switch (op) {
                    case "1" -> criar();
                    case "2" -> listar();
                    case "3" -> editar();
                    case "4" -> deletar();
                    case "0" -> System.out.println("Saindo do menu de lembretes de metas.");
                }
            } catch (Exception e) { System.out.println("[ERRO] " + e.getMessage()); }

        } while (!"0".equals(op));
    }

    private void criar() throws ObjetoNaoEncontradoException {
        if (metas.isEmpty()) { System.out.println("Crie metas primeiro."); return; }

        System.out.println("Escolha a meta:");
        metas.forEach(m -> System.out.println(m.getId() + " - " + m.getDescricao()));
        int idMeta = ConsoleIO.readInt(sc, "ID da meta: ");
        Meta meta = metas.stream().filter(m -> m.getId() == idMeta).findFirst().orElse(null);
        if (meta == null) throw new ObjetoNaoEncontradoException("Meta", idMeta);

        String descricao = ConsoleIO.readNonEmpty(sc, "Descrição do lembrete: ");
        LembreteMeta lm = new LembreteMeta(meta, descricao);
        lembretes.add(lm);
        salvarTudo();
        System.out.println("Lembrete de meta criado.");
    }

    private void listar() {
        if (lembretes.isEmpty()) { System.out.println("Nenhum lembrete de meta."); return; }
        for (LembreteMeta l : lembretes) {
            System.out.println("ID: " + l.getId() + " | " + l.getDescricao() + " | " + l.gerarNotificacao());
        }
    }

    private void editar() throws ObjetoNaoEncontradoException {
        listar(); if (lembretes.isEmpty()) return;
        int id = ConsoleIO.readInt(sc, "ID do lembrete: ");
        LembreteMeta lm = lembretes.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
        if (lm == null) throw new ObjetoNaoEncontradoException("Lembrete de Meta", id);

        System.out.println("1 - Alterar descrição");
        System.out.println("2 - Alternar ativo/inativo");
        String es = ConsoleIO.readOption(sc, "Escolha: ", "[1-2]");

        if ("1".equals(es)) { 
            String nova = ConsoleIO.readNonEmpty(sc, "Nova descrição: "); 
            lm.setDescricao(nova); 
        } else { 
            lm.setAtivo(!lm.isAtivo()); 
        }

        salvarTudo();
        System.out.println("Lembrete atualizado.");
    }

    private void deletar() throws ObjetoNaoEncontradoException {
        listar(); if (lembretes.isEmpty()) return;
        int id = ConsoleIO.readInt(sc, "ID do lembrete a deletar: ");
        boolean ok = lembretes.removeIf(l -> l.getId() == id);
        if (!ok) throw new ObjetoNaoEncontradoException("Lembrete de Meta", id);
        salvarTudo();
        System.out.println("Lembrete removido.");
    }

    private void salvarTudo() {
        if (lembretes.isEmpty()) { arquivoUtils.salvarEmArquivo(ARQ, "", false); return; }
        arquivoUtils.salvarEmArquivo(ARQ, lembretes.get(0).toArquivo(), false);
        for (int i = 1; i < lembretes.size(); i++) {
            arquivoUtils.salvarEmArquivo(ARQ, lembretes.get(i).toArquivo(), true);
        }
    }

    private void carregar() {
        List<String> linhas = arquivoUtils.lerDoArquivo(ARQ);
        for (String ln : linhas) {
            try { lembretes.add(LembreteMeta.fromArquivo(ln, metas)); }
            catch (Exception e) { System.out.println("Falha ao carregar lembrete de meta: " + e.getMessage()); }
        }
    }

    public List<LembreteMeta> getLembretes() { return lembretes; }
}