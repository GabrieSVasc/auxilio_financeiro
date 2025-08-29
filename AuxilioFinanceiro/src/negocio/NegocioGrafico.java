package negocio;

import dados.CategoriaManager;
import dados.GastoManager;
import dados.RepositorioMeses;
import negocio.entidades.Categoria;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Mes;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

public class NegocioGrafico {
	public GraficoSetores vizualizarGraficoSetores(Mes m) throws MesSemGastosException, CampoVazioException {
		CategoriaManager cm = new CategoriaManager(Categoria.carregarCategorias());
		return new GraficoSetores(m, cm, new GastoManager(cm));
	}
	
	public GraficoBarras vizualizarGraficoBarras(Categoria c) throws CategoriaSemGastosException, CampoVazioException {
		return new GraficoBarras(c, new RepositorioMeses(), new GastoManager(new CategoriaManager(Categoria.carregarCategorias())));
	}
}