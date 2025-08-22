package negocio.entidades;

import java.io.IOException;
import java.util.ArrayList;

import negocio.exceptions.MesSemGastosException;
import service.CategoriaSemGastosException;
import service.GastoManager;

public class Barra {
	private Mes mes;
	private ArrayList<Gasto> gastos;
	
	public Barra(Categoria c,  GastoManager gm, Mes mes) throws MesSemGastosException, CategoriaSemGastosException {
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