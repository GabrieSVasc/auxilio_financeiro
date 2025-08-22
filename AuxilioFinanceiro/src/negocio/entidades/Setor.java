package negocio.entidades;

import java.io.IOException;
import java.util.ArrayList;

import negocio.exceptions.MesSemGastosException;
import service.CategoriaSemGastosException;
import service.GastoManager;

public class Setor{
	private ArrayList<Gasto> gastos;
	private Categoria categoria;
	
	public Setor(Categoria c, GastoManager gm, Mes mes) throws MesSemGastosException {
		this.categoria = c;
		gastos = new ArrayList<Gasto>();
		try {
			gastos = gm.getGastosByCategoria(c, mes.getMes(), mes.getAno());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CategoriaSemGastosException e) {
		}
	}
	
	public String getTituloSetor() {
		return this.categoria.getNome();
	}
	
	public double getValorTotal() {
		double soma = 0;
		for(Gasto g: this.gastos) {
			soma+=g.getValor();
		}
		return soma;
	}
}