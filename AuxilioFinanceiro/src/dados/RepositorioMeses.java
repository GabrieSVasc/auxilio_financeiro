package dados;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import negocio.entidades.Mes;

/**
 * Classe que armazena os meses usados na execução do sistema
 * 
 * @author Maria Gabriela
 */

public class RepositorioMeses {
	private ArrayList<Mes> meses;

	/**
	 * Construtor que carrega os meses
	 */
	public RepositorioMeses() {
		carregarMeses();
	}

	/**
	 * Carrega os últimos 12 meses
	 */
	private void carregarMeses() {
		this.meses = new ArrayList<>();
		LocalDate dia = LocalDate.now();
		for (int i = 0; i <= 11; i++) {
			LocalDate diaAux = dia.minus(i, ChronoUnit.MONTHS);
			this.meses.add(new Mes(diaAux.getMonthValue(), diaAux.getYear()));
		}
	}

	/**
	 * Método que retorna os meses
	 * 
	 * @return Uma lista com os meses
	 */
	public ArrayList<Mes> getMeses() {
		return this.meses;
	}

	/**
	 * Método que consulta um mês
	 * 
	 * @param m O mes do ano
	 * @param a O ano
	 * @return O mes se encontrado ou null se não
	 */
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

	/**
	 * Método que atualiza um mês
	 * 
	 * @param mes O mês a ser atualizado
	 */
	public void atualizar(Mes mes) {
		int indice = meses.indexOf(mes);
		if (indice != -1) {
			meses.set(indice, mes);
		}
	}

	/**
	 * Método que verifica se um mês está listado
	 * 
	 * @param m O mes do ano
	 * @param a O ano
	 * @return true se estiver, false se não
	 */
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