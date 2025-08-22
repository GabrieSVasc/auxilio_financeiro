package fachada;

import java.time.LocalDate;
import java.util.ArrayList;

import dados.CategoriaManager;
import negocio.NegocioGrafico;
import negocio.NegocioMes;
import negocio.entidades.Categoria;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Mes;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.MesSemGastosException;
import service.CategoriaSemGastosException;

public class Fachada {
	
	//Gastos
	
	//Lembretes
	
	//Categorias
	private CategoriaManager cm;
	public ArrayList<String> inicializarCategorias(){
		ArrayList<String> categorias = new ArrayList<String>();
		cm = new CategoriaManager();
		
		for(Categoria c : cm.getCategorias()) {
			categorias.add(c.getNome());
		}
		return categorias;
	}
	
	//Graficos
	private static NegocioMes nm = new NegocioMes();
	private static NegocioGrafico nG = new NegocioGrafico();
	public ArrayList<String> inicializarMeses() {
		ArrayList<String> meses = new ArrayList<String>();
		for(Mes m:nm.getMeses()) {
			meses.add(m.toString());
		}
		return meses;
	}
	
	public GraficoSetores inicializarGraficoSetores() throws MesSemGastosException {
		LocalDate lD = LocalDate.now();
		return nG.vizualizarGraficoSetores(new Mes(lD.getMonthValue(), lD.getYear()));
	}
	
	public GraficoSetores inicializarGraficoSetoresMes(String m) throws MesSemGastosException {
		String[] valores = m.split("/");
		Mes mes = new Mes(Integer.valueOf(valores[0]), Integer.valueOf(valores[1])); 
		return nG.vizualizarGraficoSetores(mes);
	}
	
	public GraficoBarras inicializarGraficoBarrasCategoria(String nome) throws CampoVazioException, CategoriaSemGastosException {
		Categoria c = new Categoria(nome);
		return nG.vizualizarGraficoBarras(c);
	}
}