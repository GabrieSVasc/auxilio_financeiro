package negocio.entidades;

import java.util.ArrayList;

import negocio.CategoriaManager;
import negocio.GastoManager;
import negocio.exceptions.MesSemGastosException;

/**
 * Classe que representa um gráfico de setores associado a um mês
 * @author Maria Gabriela
 */

public class GraficoSetores{
    private Mes mes;
	private ArrayList<Setor> setores;
	
	 /**
	  * Construtor que recebe mês e ano separados
	  * @param mes
	  * @param ano
	  * @param categoriaManager
	  * @param gastoManager
	  * @throws MesSemGastosException
	  */
	public GraficoSetores(int m, int a, CategoriaManager cm, GastoManager gm) throws MesSemGastosException{
		this.mes = new Mes(m, a);
		setores = new ArrayList<Setor>();
		for(Categoria c: cm.getCategorias()) {
			setores.add(new Setor(c, gm, this.mes));
		}
	}
	
	/**
	 * Construtor que recebe a classe mês
	 * @param Mês
	 * @param categoriaManager
	 * @param gastoManager
	 * @throws MesSemGastosException
	 */
	public GraficoSetores(Mes m, CategoriaManager cm, GastoManager gm) throws MesSemGastosException{
		this.mes = m;
		setores = new ArrayList<Setor>();
		for(Categoria c: cm.getCategorias()) {
			setores.add(new Setor(c, gm, this.mes));
		}
	}
	
	public ArrayList<Setor> getSetores(){
		return setores;
	}
	
	public Mes getMes() {
		return mes;
	}

	@Override
	public boolean equals(Object obj) {
		boolean resultado = false;
		if((obj instanceof GraficoSetores) && ((GraficoSetores) obj).getMes().equals(((GraficoSetores) obj).getMes())) {
			resultado = true;
		}
		return resultado;
	}
}