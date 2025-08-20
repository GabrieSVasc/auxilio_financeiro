package service;

import model.lembretes.*;
import repository.LembreteRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LembreteManager {
    public void adicionarLembrete(Lembrete lembrete) throws IOException {
        List<Lembrete> lembretes = LembreteRepository.carregar();
        lembretes.add(lembrete);
        LembreteRepository.salvar(lembretes);
    }

    public boolean atualizarLembrete(int id, String novoTitulo, String novaDescricao, LocalDate novaDataAlerta, Boolean novoAtivo) throws IOException {
        List<Lembrete> lembretes = LembreteRepository.carregar();
        boolean encontrado = false;

        for (Lembrete lembrete : lembretes) {
            if (lembrete.getId() == id) {
                if (novoTitulo != null) lembrete.setTitulo(novoTitulo);
                if (novaDescricao != null) lembrete.setDescricao(novaDescricao);
                if (novaDataAlerta != null) lembrete.setDataAlerta(novaDataAlerta);
                if (novoAtivo != null) lembrete.setAtivo(novoAtivo);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            LembreteRepository.salvar(lembretes);
        }
        return encontrado;
    }

    public boolean removerLembrete(int id) throws IOException {
        List<Lembrete> lembretes = LembreteRepository.carregar();
        boolean removido = lembretes.removeIf(l -> l.getId() == id);
        if (removido) {
            LembreteRepository.salvar(lembretes);
        }
        return removido;
    }

    public List<Lembrete> listarTodos() throws IOException {
        return LembreteRepository.carregar();
    }

    public List<Lembrete> listarAtivos() throws IOException {
        return LembreteRepository.carregar().stream().filter(Lembrete::isAtivo).collect(Collectors.toList());
    }

    public List<MensalidadeLembrete> listarLembretesMensalidade() throws IOException {
        return LembreteRepository.carregar().stream().filter(l -> l instanceof MensalidadeLembrete).map(l -> (MensalidadeLembrete) l).collect(Collectors.toList());
    }

    public List<FaturaLembrete> listarLembretesFatura() throws IOException {
        return LembreteRepository.carregar().stream().filter(l -> l instanceof FaturaLembrete).map(l -> (FaturaLembrete) l).collect(Collectors.toList());
    }

    public Lembrete buscarPorId(int id) throws IOException {
        return LembreteRepository.carregar().stream().filter(l -> l.getId() == id).findFirst().orElse(null);
    }
}