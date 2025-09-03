package negocio.entidades;

import java.util.ArrayList;

import negocio.GastoManager;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

/**
 * Classe que representa a barra de um gráfico de barras
 * 
 * @author Maria Gabriela
 */
public class Barra {
	private Mes mes;
	private ArrayList<Gasto> gastos;

	/**
	 * Construtor de Barra, busca todos os gastos associados à categoria no mês
	 * especifico
	 * 
	 * @param categoria
	 * @param gastoManager
	 * @param mes
	 * @throws MesSemGastosException
	 * @throws CategoriaSemGastosException
	 * @throws CampoVazioException
	 */
	public Barra(Categoria c, GastoManager gm, Mes mes)
			throws MesSemGastosException, CategoriaSemGastosException, CampoVazioException {
		this.mes = mes;
		gastos = gm.getGastosByCategoria(c, mes.getMes(), mes.getAno());
	}

	/**
	 * Retorna o mês
	 * 
	 * @return mes
	 */
	public Mes getMes() {
		return this.mes;
	}

	public double getValorTotal() {
		double soma = 0;
		for (Gasto g : this.gastos) {
			soma += g.getValor();
		}
		return soma;
	}
}