package fachada;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dados.CategoriaManager;
import dados.GastoManager;
import dados.LimiteManager;
import negocio.NegocioGrafico;
import negocio.NegocioMes;
import negocio.menuPrincipal;
import negocio.Excecoes.FormatacaoInvalidaException;
import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.TIRImpossivelException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Limite;
import negocio.entidades.Mes;
import negocio.entidades.Meta;
import negocio.entidades.entidades.Parametros;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;
import negocio.exceptions.ValorNegativoException;

public class Fachada {
	private static CategoriaManager cm = new CategoriaManager();
	private static GastoManager gm = new GastoManager(cm);

	//Gastos
	public ArrayList<ValorLista> inicializarGastos(){
		ArrayList<ValorLista> gastos = new ArrayList<>();
		
		for(Gasto g: gm.listarGastos()) {
			String strG = "R$ "+g.getValor() + " - "+ g.getNome();
			gastos.add(new ValorLista(strG, g.getId()));
		}
		
		return gastos;
	}
	
	public void removerGasto(int id) throws IOException, CampoVazioException {
        gm.removerGasto(id);
	}
	
	public void criarGasto(String nome, double valor, LocalDate data, String categoria) throws CampoVazioException {
		Categoria c = Categoria.carregarCategorias().stream().filter(x -> x.getNome().equals(categoria)).findFirst().orElse(null);
		try {
			gm.adicionarGasto(new Gasto(nome, valor, c, data));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Gasto getGasto(int v) {
		return gm.listarGastos().stream().filter(x->x.getId()==v).findFirst().orElse(null);
	}
	
	public void editarGasto(int id, String nome, Double valor, LocalDate data, String categoria) throws CampoVazioException{
		Categoria c = Categoria.carregarCategorias().stream().filter(x->x.getNome().equals(categoria)).findFirst().orElse(null);
		try {
			gm.editarGasto(id, nome, valor, data, c);
		} catch (IOException e) {
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
	
	public ArrayList<ValorLista> inicializarMetas(){
		ArrayList<ValorLista> metas = new ArrayList<>();
		
		for(Meta m : Meta.carregarMetas()) {
			metas.add(new ValorLista(m.getDescricao()+" - ("+m.getValorAtual()+"/"+m.getValorObjetivo()+")", m.getId()));
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
	public ArrayList<ValorLista> inicializarCategorias(){
		ArrayList<ValorLista> categorias = new ArrayList<ValorLista>();
		
		for(Categoria c : cm.getCategorias()) {
			categorias.add(new ValorLista(c.getNome(), c.getId()));
		}
		return categorias;
	}
	
	public void criarCategoria(String nome) throws CampoVazioException {
		cm.criar(nome);
	}
	
	public void editarCategoria(int id, String novoNome) throws ValorNegativoException, ObjetoNaoEncontradoException, CampoVazioException {
		cm.editar(id, novoNome);
	}
	
	public void removerCategoria(int id) throws ValorNegativoException, ObjetoNaoEncontradoException {
		cm.deletar(id);
	}
	
	public Categoria getCategoria(int v) {
		return cm.getCategoria(v);
	}
	
	//Limites
	private static LimiteManager lm = new LimiteManager(Categoria.carregarCategorias(), Limite.carregarLimites(Categoria.carregarCategorias()));
	
	public ArrayList<ValorLista> inicializarLimites(){
		ArrayList<ValorLista> limites = new ArrayList<>();
		
		for(Limite l : lm.getLimites()) {
			limites.add(new ValorLista("Limite de "+l.getCategoria().getNome()+": R$"+l.getValor(), l.getId()));
		}
		return limites;
	}
	
	public void criarLimite(int idCat, double valor) throws ObjetoNaoEncontradoException, ValorNegativoException, ObjetoNuloException {
		lm.criar(idCat, valor);
	}
	
	public void editarLimite(int id, double valor) throws ObjetoNaoEncontradoException, ValorNegativoException {
		lm.editar(id, valor);
	}
	
	public void removerLimite(int id) throws ObjetoNaoEncontradoException, ValorNegativoException {
		lm.deletar(id);
	}
	
	public Limite getLimite(int id) {
		return lm.getLimite(id);
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
	
	//Investimentos
	
	public void investimentos(Parametros p) throws OpcaoInvalidaException, ValorInvalidoException, FormatacaoInvalidaException, TIRImpossivelException {
		menuPrincipal mp = new menuPrincipal();
		mp.inputMenu(p.getInput1(), p.getInput2(), p.getValor(), p.getTaxa(), p.getNumParcelas(), p.getTipo(), p.getTempo(), p.getArrecadacao());
	}
	
}