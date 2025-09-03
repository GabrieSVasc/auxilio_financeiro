package negocio.entidades;

import java.util.ArrayList;

import negocio.GastoManager;
import negocio.MensalidadeManager;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

/**
 * Classe que representa um setor do gráfico de setores
 * @author Maria Gabriela
 */

public class Setor{
	private ArrayList<Gasto> gastos;
	private Categoria categoria;
	
	/**
	 * Construtor, busca por todos os gastos associados à categoria
	 * @param Categoria
	 * @param GastoManager
	 * @param Mes
	 * @throws MesSemGastosException
	 */
	public Setor(Categoria c, GastoManager gm, Mes mes, MensalidadeManager mm) throws MesSemGastosException{
		this.categoria = c;
		gastos = new ArrayList<Gasto>();
		try {
			if(c.getNome().equals("Mensal")) {
				gastos = mm.getMensalidadeByMes(mes.getMes(), mes.getAno());
			}else {
				gastos = gm.getGastosByCategoria(c, mes.getMes(), mes.getAno());
			}
		}catch (CategoriaSemGastosException e) {
		}
	}
	
	/**
	 * Método que retorna o nome do setor (nome da categoria associada ao setor)
	 * @return nome da categoria
	 */
	public String getTituloSetor() {
		return this.categoria.getNome();
	}
	
	/**
	 * Método que retorna a soma dos valores dos gastos da categoria
	 * @return soma
	 */
	public double getValorTotal() {
		double soma = 0;
		for(Gasto g: this.gastos) {
			soma+=g.getValor();
		}
		return soma;
	}
}