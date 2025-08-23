package negocio.entidades;

import java.io.IOException;
import java.util.ArrayList;

import dados.GastoManager;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

public class Barra {
	private Mes mes;
	private ArrayList<Gasto> gastos;
	
	public Barra(Categoria c,  GastoManager gm, Mes mes) throws MesSemGastosException, CategoriaSemGastosException, CampoVazioException {
		this.mes = mes;
		try {
			gastos = gm.getGastosByCategoria(c, mes.getMes(), mes.getAno());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Mes getMes() {
		return this.mes;
	}
	
	public double getValorTotal() {
		double soma = 0;
		for(Gasto g: this.gastos) {
			soma += g.getValor();
		}
		return soma;
	}
}