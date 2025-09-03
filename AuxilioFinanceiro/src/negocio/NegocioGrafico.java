package negocio;

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
	public GraficoSetores vizualizarGraficoSetores(Mes m, CategoriaManager cm, GastoManager gm) throws MesSemGastosException, CampoVazioException {
		return new GraficoSetores(m, cm, gm);
	}
	
	/**
	 * Método que inicializa um gráfico de barras
	 * @param Categoria desejada
	 * @return GraficoBarras gerado
	 * @throws CategoriaSemGastosException
	 * @throws CampoVazioException
	 */
	public GraficoBarras vizualizarGraficoBarras(Categoria c, GastoManager gm, NegocioMes nm) throws CategoriaSemGastosException, CampoVazioException {
		return new GraficoBarras(c, nm, gm);
	}
}