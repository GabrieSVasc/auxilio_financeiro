package dados;

import java.util.ArrayList;

import negocio.entidades.Categoria;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Mes;

public class RepositorioGraficos {
	private ArrayList<GraficoSetores> graficosSetores;
	private ArrayList<GraficoBarras> graficosBarras;

	public RepositorioGraficos() {
		graficosSetores = new ArrayList<>();
		graficosBarras = new ArrayList<>();
	}

	public ArrayList<GraficoSetores> getGraficosGS() {
		return this.graficosSetores;
	}

	public void adicionarGS(GraficoSetores grafico) {
		this.graficosSetores.add(grafico);
	}

	public void removerGS(GraficoSetores grafico) {
		int id = graficosSetores.indexOf(grafico);
		if (id != -1) {
			this.graficosSetores.remove(grafico);
		}
	}

	public GraficoSetores consultarGS(Mes m) {
		GraficoSetores encontrado = null;
		for (GraficoSetores g : graficosSetores) {
			if (g.getMes().equals(m)) {
				encontrado = g;
				break;
			}
		}
		return encontrado;
	}

	public void atualizarGS(GraficoSetores grafico) {
		int indice = graficosSetores.indexOf(grafico);
		if (indice != -1) {
			graficosSetores.set(indice, grafico);
		}
	}

	public GraficoSetores consultarGS(GraficoSetores grafico) {
		GraficoSetores graficoProcurado = null;
		for (GraficoSetores g : graficosSetores) {
			if (g.equals(grafico)) {
				graficoProcurado = g;
				break;
			}
		}
		return graficoProcurado;
	}

	public boolean existeGS(Mes m) {
		boolean resultado = false;
		for (GraficoSetores g : graficosSetores) {
			if (g.getMes().equals(m)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}
	
	public ArrayList<GraficoBarras> getGraficosGB() {
		return this.graficosBarras;
	}

	public void adicionarGB(GraficoBarras grafico) {
		this.graficosBarras.add(grafico);
	}

	public void removerGB(GraficoBarras grafico) {
		int id = graficosBarras.indexOf(grafico);
		if (id != -1) {
			this.graficosBarras.remove(grafico);
		}
	}

	public GraficoBarras consultarGB(Categoria c) {
		GraficoBarras encontrado = null;
		for (GraficoBarras g : graficosBarras) {
			if (g.getCategoria().getNome().equals(c.getNome())) {
				encontrado = g;
				break;
			}
		}
		return encontrado;
	}

	public void atualizarGB(GraficoBarras grafico) {
		int indice = graficosBarras.indexOf(grafico);
		if (indice != -1) {
			graficosBarras.set(indice, grafico);
		}
	}

	public GraficoBarras consultarGB(GraficoBarras grafico) {
		GraficoBarras graficoProcurado = null;
		for (GraficoBarras g : graficosBarras) {
			if (g.equals(grafico)) {
				graficoProcurado = g;
				break;
			}
		}
		return graficoProcurado;
	}

	public boolean existeGB(Categoria c) {
		boolean resultado = false;
		for (GraficoBarras g : graficosBarras) {
			if (g.getCategoria().getNome().equals(c.getNome())) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}
}