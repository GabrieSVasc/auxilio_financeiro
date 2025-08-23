package dados;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Lembrete;
import negocio.entidades.LembreteBase;
/**
 * Gerencia lembretes genéricos (LembreteBase).
 * 
 * @author Pedro Farias
 */
public class LembreteManager {
    private List<LembreteBase> lembrentes;
    private List<Lembrete> lembretes;

    public LembreteManager() {
        this.lembrentes = new ArrayList<>();
    }

    public void adicionarLembrente(LembreteBase l) { lembrentes.add(l); }

    public void adicionarLembrete(Lembrete l) { lembretes.add(l); }

    public void removerLembrente(int id) { lembrentes.removeIf(l -> l.getId() == id); }

    public void listarLembrentes() {
        if (lembrentes.isEmpty()) {
            System.out.println("Nenhum lembrente cadastrado.");
            return;
        }
        for (LembreteBase l : lembrentes) {
            System.out.println("ID: " + l.getId() + " | " + l.getDescricao() + " | " + l.gerarNotificacao());
        }
    }

    public void ativarLembrente(int id) {
        for (LembreteBase l : lembrentes) {
            if (l.getId() == id) {
                l.setAtivo(true);
                System.out.println("Lembrete ativado!");
                return;
            }
        }
        System.out.println("Lembrete não encontrado.");
    }

    public void desativarLembrente(int id) {
        for (LembreteBase l : lembrentes) {
            if (l.getId() == id) {
                l.setAtivo(false);
                System.out.println("Lembrete desativado!");
                return;
            }
        }
        System.out.println("Lembrete não encontrado.");
    }

    public void verificarLembrente() {
        for (LembreteBase l : lembrentes) {
            if (l.isAtivo()) {
                System.out.println(l.gerarNotificacao());
            }
        }
    }

	public void atualizarLembrete(int id, String string, String string2, LocalDate dataPrazo) {
		LembreteBase lb = lembrentes.get(id);
		lb.setDescricao(string);
	}

	public List<LembreteBase> listarTodos() {
		return lembrentes;
	}
}