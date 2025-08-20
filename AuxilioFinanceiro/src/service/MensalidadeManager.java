package service;

import exceptions.CampoVazioException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import model.gastos.Mensalidade;
import repository.GastoRepository;

public class MensalidadeManager {
    public void adicionarMensalidade(Mensalidade mensalidade) throws IOException, CampoVazioException {
        List<Mensalidade> mensalidades = GastoRepository.carregarMensalidades();
        mensalidades.add(mensalidade);
        GastoRepository.salvarMensalidades(mensalidades);
    }

    public boolean editarMensalidade(int id, String novoNome, Double novoValor, LocalDate novaDataVencimento, 
                                      String novaRecorrencia, Boolean novoPago) throws IOException, CampoVazioException {
        List<Mensalidade> mensalidades = GastoRepository.carregarMensalidades();
        boolean encontrado = false;

        for (Mensalidade mensalidade : mensalidades) {
            if (mensalidade.getId() == id) {
                if (novoNome != null) mensalidade.setNome(novoNome);
                if (novoValor != null) mensalidade.setValor(novoValor);
                if (novaDataVencimento != null) mensalidade.setDataVencimento(novaDataVencimento);
                if (novaRecorrencia != null) mensalidade.setRecorrencia(novaRecorrencia);
                if (novoPago != null) mensalidade.setPago(novoPago);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            GastoRepository.salvarMensalidades(mensalidades);
        }
        return encontrado;
    }

    public boolean removerMensalidade(int id) throws IOException {
        List<Mensalidade> mensalidades = GastoRepository.carregarMensalidades();
        boolean removido = mensalidades.removeIf(mensalidade -> mensalidade.getId() == id);
        
        if (removido) {
            GastoRepository.salvarMensalidades(mensalidades);
        }
        return removido;
    }

    public List<Mensalidade> listarMensalidades() throws IOException {
        return GastoRepository.carregarMensalidades();
    }

    public Mensalidade buscarPorId(int id) throws IOException {
        return listarMensalidades().stream()
            .filter(mensalidade -> mensalidade.getId() == id)
            .findFirst()
            .orElse(null);
    }
}
