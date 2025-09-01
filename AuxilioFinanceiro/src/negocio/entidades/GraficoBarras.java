package negocio.entidades;

import java.util.ArrayList;

import dados.GastoManager;
import dados.RepositorioMeses;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

/**
 * Classe que representa gráficos de barras associados a uma categoria
 * @author Maria Gabriela
 */

public class GraficoBarras {
	private Categoria categoria;
	private ArrayList<Barra> barras;

	public GraficoBarras(Categoria c, RepositorioMeses rm, GastoManager gm) throws CategoriaSemGastosException, CampoVazioException {
		this.categoria = c;
		barras = new ArrayList<Barra>();
		for(Mes m: rm.getMeses()) {
			try {
				barras.add(new Barra(c, gm, m));
			}catch(MesSemGastosException e) {
			}
		}
	}
	
	public ArrayList<Barra> getBarras(){
		return barras;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean resultado = false;
		if((obj instanceof GraficoBarras) && ((GraficoBarras) obj).getCategoria().equals(((GraficoBarras) obj).getCategoria())) {
			resultado = true;
		}
		return resultado;
	}
}
