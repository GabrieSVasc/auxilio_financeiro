package negocio;

import java.util.ArrayList;

import dados.RepositorioMeses;
import negocio.entidades.Mes;

/**
 * Classe que lida com os meses
 * @author Maria Gabriela
 */

public class NegocioMes {
	private RepositorioMeses rm;

	public NegocioMes() {
		rm = new RepositorioMeses();
	}

	public int quantidadeMeses() {
		return rm.getMeses().size();
	}

	public ArrayList<Mes> getMeses() {
		return rm.getMeses();
	}
}