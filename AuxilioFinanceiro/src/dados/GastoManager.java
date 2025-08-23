package dados;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

public class GastoManager {
	private final List<Gasto> gastos = new ArrayList<>();
	private final CategoriaManager categoriaManager;

	// Construtor corrigido: agora recebe o CategoriaManager
	public GastoManager(CategoriaManager categoriaManager) {
		this.categoriaManager = categoriaManager;
		carregarGastos();
	}

	// Método para carregar gastos do arquivo.
	private void carregarGastos() {
		try {
			gastos.addAll(GastoRepository.carregar(categoriaManager.getCategorias()));
		} catch (IOException | CampoVazioException e) {
			System.err.println("Erro ao carregar gastos: " + e.getMessage());
		}
	}

	public void adicionarGasto(Gasto gasto) throws IOException {
		gastos.add(gasto);
		GastoRepository.salvar(gastos);
		System.out.println("Gasto adicionado com sucesso e salvo no arquivo!");
	}

	public boolean editarGasto(int id, String novoNome, Double novoValor, LocalDate novaData, Categoria novaCategoria)
			throws IOException, CampoVazioException {
		Gasto gasto = buscarPorId(id);
		if (gasto == null) {
			return false;
		}
		if (novoNome != null) {
			gasto.setNome(novoNome);
		}
		if (novoValor != null && novoValor > 0) {
			gasto.setValor(novoValor);
		}
		if (novaData != null) {
			gasto.setDataCriacao(novaData);
		}
		// A categoria não pode ser alterada pois agora é final na classe Gasto
		// A chamada a setCategoria() foi removida.

		GastoRepository.salvar(gastos);
		System.out.println("Gasto atualizado com sucesso e salvo no arquivo!");
		return true;
	}

	public List<Gasto> listarGastos() {
		return new ArrayList<>(gastos);
	}

	public boolean removerGasto(int id) throws IOException {
		boolean removido = gastos.removeIf(gasto -> gasto.getId() == id);
		if (removido) {
			GastoRepository.salvar(gastos);
			System.out.println("Gasto removido com sucesso e arquivo atualizado!");
		}
		return removido;
	}

	private Gasto buscarPorId(int id) {
		return gastos.stream().filter(gasto -> gasto.getId() == id).findFirst().orElse(null);
	}

	public ArrayList<Gasto> getGastoByMes(int m, int a) throws IOException, CampoVazioException {
		List<Gasto> gastos = GastoRepository.carregar(categoriaManager.getCategorias());
		ArrayList<Gasto> gastosMes = new ArrayList<Gasto>();
		for (Gasto g : gastos) {
			if (g.getDataCriacao().getMonthValue() == m && g.getDataCriacao().getYear() == a) {
				gastosMes.add(g);
			}
		}
		return gastosMes;
	}

	// Buscando entre os gastos aqueles associados a uma categoria específica em um
	// mês de um ano específico
	public ArrayList<Gasto> getGastosByCategoria(Categoria c, int m, int a)
			throws IOException, MesSemGastosException, CategoriaSemGastosException, CampoVazioException {
		List<Gasto> gastos = this.getGastoByMes(m, a);
		if (!categoriaTemGastos(c)) {
			throw new CategoriaSemGastosException();
		}
		if (gastos.size() == 0) {
			throw new MesSemGastosException();
		}
		ArrayList<Gasto> gastosCategoria = new ArrayList<Gasto>();
		for (Gasto g : gastos) {
			if (g.getCategoria().getNome().equals(c.getNome()) && g.getDataCriacao().getMonthValue() == m
					&& g.getDataCriacao().getYear() == a) {
				gastosCategoria.add(g);
			}
		}
		return gastosCategoria;
	}

	public boolean categoriaTemGastos(Categoria c) throws IOException, CampoVazioException {
		List<Gasto> gastos = GastoRepository.carregar(categoriaManager.getCategorias());
		boolean tem = false;
		for (Gasto g : gastos) {
			if (g.getCategoria().getNome().equals(c.getNome())) {
				tem = true;
				break;
			}
		}
		return tem;
	}
}