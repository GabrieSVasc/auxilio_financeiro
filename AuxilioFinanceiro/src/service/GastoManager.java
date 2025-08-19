package service;

import exceptions.CampoVazioException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import model.Categoria;
import model.gastos.Gasto;
import repository.GastoRepository;

public class GastoManager {
    public void adicionarGasto(Gasto gasto) throws IOException, CampoVazioException {
        List<Gasto> gastos = GastoRepository.carregar();
        gastos.add(gasto);
        GastoRepository.salvar(gastos);
    }

    public boolean editarGasto(int id, String novoNome, Double novoValor, LocalDate novaData, Categoria novaCategoria) 
            throws IOException, CampoVazioException {
        
        List<Gasto> gastos = GastoRepository.carregar();
        boolean encontrado = false;

        for (Gasto gasto : gastos) {
            if (gasto.getId() == id) {
                // Para categoria, precisamos criar um novo Gasto
                if (novaCategoria != null) {
                    Gasto novoGasto = new Gasto(
                        novoNome != null ? novoNome : gasto.getNome(),
                        novoValor != null ? novoValor : gasto.getValor(),
                        novaData != null ? novaData.format(Gasto.getDateFormatter()) : gasto.getData().format(Gasto.getDateFormatter()),
                        novaCategoria
                    );
                    gastos.set(gastos.indexOf(gasto), novoGasto);
                } else {
                    // Atualiza outros campos se não mudou a categoria
                    if (novoNome != null) gasto.setNome(novoNome);
                    if (novoValor != null) gasto.setValor(novoValor);
                    if (novaData != null) gasto.setData(novaData);
                }
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            GastoRepository.salvar(gastos);
        }
        return encontrado;
    }

    public boolean removerGasto(int id) throws IOException {
        List<Gasto> gastos = GastoRepository.carregar();
        boolean removido = gastos.removeIf(gasto -> gasto.getId() == id);
        
        if (removido) {
            GastoRepository.salvar(gastos);
        }
        return removido;
    }

    public List<Gasto> listarGastos() throws IOException {
        return GastoRepository.carregar();
    }
}