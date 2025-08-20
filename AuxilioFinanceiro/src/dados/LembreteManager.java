package service;

import java.util.ArrayList;
import java.util.List;
import model.LembreteBase;

public class LembreteManager {
    private List<LembreteBase> lembrentes;

    public LembreteManager() {
        this.lembrentes = new ArrayList<>();
    }

    public void adicionarLembrente(LembreteBase l) { lembrentes.add(l); }
    public void removerLembrente(int id) { lembrentes.removeIf(l -> l.getId() == id); }

    public void listarLembrentes() {
        if (lembrentes.isEmpty()) {
            System.out.println("Nenhum lembrente cadastrada.");
            return;
        }
        for (LembreteBase l : lembrentes) {
            System.out.println(l);
        }
    }

    public void ativarLembrente(int id) {
        for (LembreteBase l : lembrentes) {
            if (l.getId() == id) {
                l.setAtivo(true);
                System.out.println("Lembrente ativado!");
                return;
            }
        }
        System.out.println("Lembrente não encontrado.");
    }

    public void desativarLembrente(int id) {
        for (LembreteBase l : lembrentes) {
            if (l.getId() == id) {
                l.setAtivo(false);
                System.out.println("Lembrente desativado!");
                return;
            }
        }
        System.out.println("Lembrente não encontrada.");
    }

    public void verificarLembrente() {
        for (LembreteBase l : lembrentes) {
            if (l.isAtivo()) {
                System.out.println(l.gerarNotificacao());
            }
        }
    }
}
