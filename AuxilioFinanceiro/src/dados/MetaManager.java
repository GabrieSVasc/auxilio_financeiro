package dados;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import negocio.entidades.LembreteMeta;
import negocio.entidades.Meta;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;
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
                case "1" -> criar("", 0, 0, LocalDate.now());
                case "2" -> listar();
                case "3" -> editar(0, "", 0,0, LocalDate.now());
                case "4" -> deletar(0);
                case "0" -> System.out.println("Saindo do menu de metas.");
            }
        } while (!"0".equals(op));
    }

    public void criar(String desc, double objetivo, double atual, LocalDate prazo) throws CampoVazioException, IOException, ObjetoNuloException {
        Meta m = new Meta(desc, objetivo, atual, prazo);
        metas.add(m);
        Meta.salvarLista(metas);
        
        // Adiciona lembrete associado à nova meta
        LembreteMeta lembrete = new LembreteMeta(m, "Acompanhamento da meta " + desc);
        lembreteManager.criarLembrete(lembrete);
    }

    private void listar() {
        if (metas.isEmpty()) { 
            System.out.println("Nenhuma meta cadastrada."); 
            return; 
        }
        metas.forEach(System.out::println);
    }

    public void editar(int id, String desc, double objetivo, double atual, LocalDate data) throws ObjetoNaoEncontradoException, IOException, CampoVazioException {
        Meta m = metas.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (m == null) { 
            System.out.println("Meta não encontrada."); 
            return; 
        }
        m.setDescricao(desc);
        m.setValorObjetivo(objetivo);
        m.setValorAtual(atual);
        m.setDataPrazo(data);
        Meta.salvarLista(metas);
        
        // Atualiza lembrete associado à meta
        lembreteManager.atualizarLembrete(m.getId(), "Meta: " + m.getDescricao(), "Acompanhamento da meta " + m.getDescricao(), m.getDataPrazo());
    }

    public void deletar(int id) throws ObjetoNaoEncontradoException, IOException {
        boolean ok = metas.removeIf(m -> m.getId() == id);
        if (!ok) {
            System.out.println("Meta não encontrada.");
        } else { 
            lembreteManager.removerLembrete(id);
            Meta.salvarLista(metas); 
        }
    }
}