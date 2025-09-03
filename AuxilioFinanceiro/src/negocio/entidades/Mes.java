package negocio.entidades;

/**
 * Classe que representa um mês de um ano para utilização na criação de gráficos
 * @author Maria Gabriela
 */

public class Mes{
	private int ano, mes;
	
	public Mes(int m, int a) {
		this.ano = a;
		this.mes = m;
	}
	
	public int getMes() {
		return mes;
	}

	public int getAno() {
		return ano;
	}
	
	@Override
	public String toString() {
		return String.format("%02d", mes)+"/"+ano;
	}

	@Override
	public boolean equals(Object obj) {
		boolean resultado = false;
		if(obj instanceof Mes && this.mes == ((Mes) obj).getMes() && this.ano == ((Mes) obj).getAno()) {
			resultado = true;
		}
		return resultado;
	}
}