package service;

import exceptions.CampoVazioException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Limite;
import model.gastos.Mensalidade;
import model.lembretes.*;
import repository.LembreteRepository;
import service.Handlers.*;

/**
 * Classe de serviço para gerenciar as operações de negócios dos lembretes.
 * Atua como uma camada entre a interface do usuário e o repositório.
 */
public class LembreteManager {
    // Lista local para armazenar os lembretes em memória.
    private final List<Lembrete> lembretes = new ArrayList<>();
    // Handlers para validar diferentes tipos de lembretes.
    private final FaturaHandler faturaHandler = new FaturaHandler();
    private final LimiteHandler limiteHandler = new LimiteHandler();
    private final MetaHandler metaHandler = new MetaHandler();
    private final MensalidadeHandler mensalidadeHandler = new MensalidadeHandler();
    
    // Managers de dependência
    private final MensalidadeManager mensalidadeManager;
    private final LimiteManager limiteManager;
    private final CategoriaManager categoriaManager;

    /**
     * Construtor que carrega os lembretes do arquivo ao inicializar a classe.
     */
    public LembreteManager(MensalidadeManager mensalidadeManager, LimiteManager limiteManager, CategoriaManager categoriaManager) {
        this.mensalidadeManager = mensalidadeManager;
        this.limiteManager = limiteManager;
        this.categoriaManager = categoriaManager;
        carregarLembretes();
    }

    /**
     * Carrega os lembretes do repositório para a memória, resolvendo as dependências.
     */
    private void carregarLembretes() {
        try {
            List<Mensalidade> mensalidades = mensalidadeManager.listarMensalidades();
            
            // CORREÇÃO AQUI:
            // O seu LimiteManager tem o método getLimites(), que retorna a lista que foi passada no construtor.
            List<Limite> limites = limiteManager.getLimites();
            
            lembretes.addAll(LembreteRepository.carregar(mensalidades, limites));
        } catch (IOException e) {
            System.err.println("Erro ao carregar lembretes: " + e.getMessage());
        }
    }

    /**
     * Adiciona um novo lembrete à lista e o salva.
     * @param lembrete O objeto Lembrete a ser adicionado.
     */
    public void criarLembrete(Lembrete lembrete) throws CampoVazioException {
        // Usa o 'switch expression' para validar o lembrete de acordo com seu tipo.
        switch (lembrete) {
            case FaturaLembrete faturaLembrete -> faturaHandler.validar(faturaLembrete);
            case LembreteLimite lembreteLimite -> limiteHandler.validar(lembreteLimite);
            case LembreteMeta lembreteMeta -> metaHandler.validar(lembreteMeta);
            case MensalidadeLembrete mensalidadeLembrete -> mensalidadeHandler.validar(mensalidadeLembrete);
            default -> {}
        }
        lembretes.add(lembrete);
        salvarNoRepositorio();
    }

    /**
     * Atualiza um lembrete existente com base no seu ID.
     */
    public void atualizarLembrete(int id, String novoTitulo, String novaDescricao, LocalDate novaData) {
        Lembrete lembrete = buscarPorId(id);
        if (lembrete != null) {
            if (novoTitulo != null) lembrete.setTitulo(novoTitulo);
            if (novaDescricao != null) lembrete.setDescricao(novaDescricao);
            if (novaData != null) lembrete.setDataAlerta(novaData);
            salvarNoRepositorio();
        }
    }

    /**
     * Retorna uma cópia da lista de todos os lembretes.
     * @return Uma lista de todos os lembretes.
     */
    public List<Lembrete> listarTodos() {
        return new ArrayList<>(lembretes);
    }

    /**
     * Retorna apenas os lembretes do tipo LembreteLimite.
     * @return Uma lista de LembreteLimite.
     */
    public List<LembreteLimite> listarLembretesLimite() {
        return lembretes.stream()
                .filter(l -> l instanceof LembreteLimite)
                .map(l -> (LembreteLimite) l)
                .toList();
    }

    /**
     * Remove um lembrete com base no seu ID.
     * @return true se o lembrete foi removido.
     */
    public boolean removerLembrete(int id) {
        boolean removido = lembretes.removeIf(l -> l.getId() == id);
        if (removido) {
            salvarNoRepositorio();
        }
        return removido;
    }

    /**
     * Método privado para salvar a lista de lembretes no repositório.
     */
    private void salvarNoRepositorio() {
        try {
            System.out.println("Salvando lembretes no arquivo...");
            LembreteRepository.salvar(lembretes);
            System.out.println("Lembretes salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar lembretes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Busca um lembrete na lista por ID.
     * @return O objeto Lembrete encontrado ou null.
     */
    private Lembrete buscarPorId(int id) {
        return lembretes.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }
}