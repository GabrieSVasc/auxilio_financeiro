package negocio.entidades;

import java.util.ArrayList;

import negocio.GastoManager;
import negocio.MensalidadeManager;
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
	 */
	public Barra(Categoria c, GastoManager gm, Mes mes, MensalidadeManager mm)
			throws MesSemGastosException, CategoriaSemGastosException{
		this.mes = mes;
		if(c.getNome().equals("Mensal")) {
			gastos = mm.getMensalidadeByMes(mes.getMes(), mes.getAno());
		}else {
			gastos = gm.getGastosByCategoria(c, mes.getMes(), mes.getAno());
		}
	}

	/**
	 * Retorna o mês
	 * 
	 * @return mes
	 */
	public Mes getMes() {
		return this.mes;
	}

	/**
	 * Método que calcula a soma dos valores dos gastos
	 * @return Retorna a soma dos gastos associados à barra
	 */
	public double getValorTotal() {
		double soma = 0;
		for (Gasto g : this.gastos) {
			soma += g.getValor();
		}
		return soma;
	}
}