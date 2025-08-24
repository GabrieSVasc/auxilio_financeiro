package fachada;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dados.CategoriaManager;
import dados.GastoManager;
import negocio.NegocioGrafico;
import negocio.NegocioMes;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Mes;
import negocio.entidades.Meta;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

public class Fachada {
	private static CategoriaManager cm = new CategoriaManager();
	private static GastoManager gm = new GastoManager(cm);

	//Gastos
	public ArrayList<TransferindoListas> inicializarGastos(){
		ArrayList<TransferindoListas> gastos = new ArrayList<>();
		
		for(Gasto g: gm.listarGastos()) {
			String strG = "R$ "+g.getValor() + " - "+ g.getNome();
			gastos.add(new TransferindoListas(strG, g.getId()));
		}
		
		return gastos;
	}
	
	public void removerGasto(int id) throws IOException, CampoVazioException {
        gm.removerGasto(id);
	}
	
	public void criarGasto(String nome, double valor, LocalDate data, String categoria) {
		Categoria c = Categoria.carregarCategorias().stream().filter(x -> x.getNome().equals(categoria)).findFirst().orElse(null);
		try {
			gm.adicionarGasto(new Gasto(nome, valor, c, data));
		} catch (IOException | CampoVazioException e) {
			e.printStackTrace();
		}
	}
	
	public Gasto getGasto(int v) {
		return gm.listarGastos().stream().filter(x->x.getId()==v).findFirst().orElse(null);
	}
	
	public void editarGasto(int id, String nome, Double valor, LocalDate data, String categoria) {
		Categoria c = Categoria.carregarCategorias().stream().filter(x->x.getNome().equals(categoria)).findFirst().orElse(null);
		try {
			gm.editarGasto(id, nome, valor, data, c);
		} catch (IOException | CampoVazioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Lembretes
	
	//Metas
	public void criarMeta(String descricao, double valorObjetivo, double valorAtual, LocalDate data) {
		Meta meta = new Meta(descricao, valorObjetivo, valorAtual, data);
		List<Meta> metas = Meta.carregarMetas();
		metas.add(meta);
		Meta.salvarLista(metas);
	}
	
	public ArrayList<TransferindoListas> inicializarMetas(){
		ArrayList<TransferindoListas> metas = new ArrayList<>();
		
		for(Meta m : Meta.carregarMetas()) {
			metas.add(new TransferindoListas(m.getDescricao()+" - ("+m.getValorAtual()+"/"+m.getValorObjetivo()+")", m.getId()));
		}
		
		return metas;
	}
	
	public void removerMeta(int id) {
		List<Meta> metas = Meta.carregarMetas();
        Meta m = metas.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        metas.remove(m);
        Meta.salvarLista(metas);
	}
	
	public void editarMeta(int id, String descricao, double valorOb, double valorAt, LocalDate data) {
		List<Meta> metas = Meta.carregarMetas();
		Meta m = metas.stream().filter(x -> x.getId()==id).findFirst().orElse(null);
		m.setDescricao(descricao);
		m.setDataPrazo(data);
		m.setValorAtual(valorAt);
		m.setValorObjetivo(valorOb);
		metas.set(metas.indexOf(m), m);
		Meta.salvarLista(metas);
	}
	
	public Meta getMeta(int id) {
		List<Meta> metas = Meta.carregarMetas();
		Meta m = metas.stream().filter(x->x.getId() == id).findFirst().orElse(null);
		return m;
	}
	
	//Categorias
	public ArrayList<String> inicializarCategorias(){
		ArrayList<String> categorias = new ArrayList<String>();
		
		for(Categoria c : cm.getCategorias()) {
			categorias.add(c.getNome());
		}
		return categorias;
	}
	
	public void criarCategoria(String nome) {
		List<Categoria> categorias = cm.getCategorias();
		try {
			categorias.add(new Categoria(nome));
			Categoria.salvarTodas(categorias);
		} catch (CampoVazioException e) {
		}
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
	
	public GraficoSetores inicializarGraficoSetores() throws MesSemGastosException, CampoVazioException {
		LocalDate lD = LocalDate.now();
		return nG.vizualizarGraficoSetores(new Mes(lD.getMonthValue(), lD.getYear()));
	}
	
	public GraficoSetores inicializarGraficoSetoresMes(String m) throws MesSemGastosException, CampoVazioException {
		String[] valores = m.split("/");
		Mes mes = new Mes(Integer.valueOf(valores[0]), Integer.valueOf(valores[1])); 
		return nG.vizualizarGraficoSetores(mes);
	}
	
	public GraficoBarras inicializarGraficoBarrasCategoria(String nome) throws CampoVazioException, CategoriaSemGastosException {
		Categoria c = new Categoria(nome);
		return nG.vizualizarGraficoBarras(c);
	}
}