package negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import dados.RepositorioMeses;
import negocio.entidades.Mes;

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