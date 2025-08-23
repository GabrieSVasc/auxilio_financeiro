package negocio;

import dados.CategoriaManager;
import dados.GastoManager;
import dados.RepositorioGraficos;
import dados.RepositorioMeses;
import negocio.entidades.Categoria;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Mes;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

public class NegocioGrafico {
	private RepositorioGraficos rg;
	
	public NegocioGrafico() {
		rg = new RepositorioGraficos();
	}
	
	public GraficoSetores vizualizarGraficoSetores(Mes m) throws MesSemGastosException, CampoVazioException {
		GraficoSetores retorno;
		if(rg.existeGS(m)) {
			retorno = rg.consultarGS(m);
		}else {
			CategoriaManager cm = new CategoriaManager();
			retorno = new GraficoSetores(m, cm, new GastoManager(cm));
			rg.adicionarGS(retorno);
		}
		return retorno;
	}
	
	public GraficoBarras vizualizarGraficoBarras(Categoria c) throws CategoriaSemGastosException, CampoVazioException {
		GraficoBarras retorno;
		if(rg.existeGB(c)) {
			retorno = rg.consultarGB(c);
		}else {
			retorno = new GraficoBarras(c, new RepositorioMeses(), new GastoManager(new CategoriaManager()));
		}
		return retorno;
	}
}