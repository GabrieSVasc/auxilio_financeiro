package negocio;

import dados.CategoriaManager;
import dados.RepositorioGraficos;
import dados.RepositorioMeses;
import negocio.entidades.Categoria;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Mes;
import negocio.exceptions.MesSemGastosException;
import service.CategoriaSemGastosException;
import service.GastoManager;

public class NegocioGrafico {
	private RepositorioGraficos rg;
	
	public NegocioGrafico() {
		rg = new RepositorioGraficos();
	}
	
	public GraficoSetores vizualizarGraficoSetores(Mes m) throws MesSemGastosException {
		GraficoSetores retorno;
		if(rg.existeGS(m)) {
			retorno = rg.consultarGS(m);
		}else {
			retorno = new GraficoSetores(m, new CategoriaManager(), new GastoManager());
			rg.adicionarGS(retorno);
		}
		return retorno;
	}
	
	public GraficoBarras vizualizarGraficoBarras(Categoria c) throws CategoriaSemGastosException {
		GraficoBarras retorno;
		if(rg.existeGB(c)) {
			retorno = rg.consultarGB(c);
		}else {
			retorno = new GraficoBarras(c, new RepositorioMeses(), new GastoManager());
		}
		return retorno;
	}
}