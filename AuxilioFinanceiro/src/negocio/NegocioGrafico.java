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
/**
 * Classe que lida com a inicialização dos gráficos de barras e de setores
 * @author Maria Gabriela
 */
public class NegocioGrafico {
	
	/**
	 * Método que inicializa um gráfico de setores
	 * @param Mês desejado
	 * @return Grafico gerado
	 * @throws MesSemGastosException
	 * @throws CampoVazioException
	 */
	public GraficoSetores vizualizarGraficoSetores(Mes m) throws MesSemGastosException, CampoVazioException {
		CategoriaManager cm = new CategoriaManager(Categoria.carregarCategorias());
		return new GraficoSetores(m, cm, new GastoManager(cm));
	}
	
	/**
	 * Método que inicializa um gráfico de barras
	 * @param Categoria desejada
	 * @return GraficoBarras gerado
	 * @throws CategoriaSemGastosException
	 * @throws CampoVazioException
	 */
	public GraficoBarras vizualizarGraficoBarras(Categoria c) throws CategoriaSemGastosException, CampoVazioException {
		return new GraficoBarras(c, new RepositorioMeses(), new GastoManager(new CategoriaManager(Categoria.carregarCategorias())));
	}
}