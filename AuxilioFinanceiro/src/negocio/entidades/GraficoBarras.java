package negocio.entidades;

import java.util.ArrayList;

import negocio.GastoManager;
import negocio.MensalidadeManager;
import negocio.NegocioMes;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

/**
 * Classe que representa gráficos de barras associados a uma categoria
 * 
 * @author Maria Gabriela
 */

public class GraficoBarras {
	private Categoria categoria;
	private ArrayList<Barra> barras;

	/**
	 * Construtor gera as barras do gráfico para cada mês considerado
	 * 
	 * @param c
	 * @param nm
	 * @param gm
	 * @throws CategoriaSemGastosException
	 */
	public GraficoBarras(Categoria c, NegocioMes nm, GastoManager gm, MensalidadeManager mm) throws CategoriaSemGastosException {
		this.categoria = c;
		barras = new ArrayList<Barra>();
		for (Mes m : nm.getMeses()) {
			try {
				barras.add(new Barra(c, gm, m, mm));
			} catch (MesSemGastosException e) {
			}
		}
	}

	public ArrayList<Barra> getBarras() {
		return barras;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	@Override
	public boolean equals(Object obj) {
		boolean resultado = false;
		if ((obj instanceof GraficoBarras)
				&& ((GraficoBarras) obj).getCategoria().equals(((GraficoBarras) obj).getCategoria())) {
			resultado = true;
		}
		return resultado;
	}
}
