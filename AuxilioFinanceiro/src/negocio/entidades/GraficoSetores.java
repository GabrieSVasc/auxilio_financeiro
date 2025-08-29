package negocio.entidades;

import java.util.ArrayList;

import dados.CategoriaManager;
import dados.GastoManager;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.MesSemGastosException;

public class GraficoSetores{
    private Mes mes;
	private ArrayList<Setor> setores;
	
	public GraficoSetores(int m, int a, CategoriaManager cm, GastoManager gm) throws MesSemGastosException, CampoVazioException {
		this.mes = new Mes(m, a);
		setores = new ArrayList<Setor>();
		for(Categoria c: cm.getCategorias()) {
			setores.add(new Setor(c, gm, this.mes));
		}
	}
	
	public GraficoSetores(Mes m, CategoriaManager cm, GastoManager gm) throws MesSemGastosException, CampoVazioException {
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