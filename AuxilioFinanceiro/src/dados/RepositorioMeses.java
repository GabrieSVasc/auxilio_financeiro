package dados;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import negocio.entidades.Mes;

public class RepositorioMeses {
	private ArrayList<Mes> meses;

	public RepositorioMeses() {
		carregarMeses();
	}

	private void carregarMeses() {
		this.meses = new ArrayList<>();
		LocalDate dia = LocalDate.now();
		for (int i = 0; i <= 11; i++) {
			LocalDate diaAux = dia.minus(i, ChronoUnit.MONTHS);
			this.meses.add(new Mes(diaAux.getMonthValue(), diaAux.getYear()));
		}
	}

	public ArrayList<Mes> getMeses() {
		return this.meses;
	}

	public Mes consultar(int m, int a) {
		Mes encontrado = null;
		for (Mes mes : meses) {
			if (mes.equals(new Mes(m, a))) {
				encontrado = mes;
				break;
			}
		}
		return encontrado;
	}

	public void atualizar(Mes mes) {
		int indice = meses.indexOf(mes);
		if (indice != -1) {
			meses.set(indice, mes);
		}
	}

	public Mes consultar(Mes mes) {
		Mes mesProcurado = null;
		for (Mes m : meses) {
			if (m.equals(mes)) {
				mesProcurado = m;
				break;
			}
		}
		return mesProcurado;
	}

	public boolean existe(int m, int a) {
		boolean resultado = false;
		for (Mes mes : meses) {
			if (mes.equals(new Mes(m, a))) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}
}