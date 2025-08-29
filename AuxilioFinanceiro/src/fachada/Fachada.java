package fachada;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dados.CategoriaManager;
import dados.GastoManager;
import dados.LembreteManager;
import dados.LimiteManager;
import dados.MensalidadeManager;
import javafx.fxml.Initializable;
import negocio.NegocioGrafico;
import negocio.NegocioMes;
import negocio.menuPrincipal;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Lembrete;
import negocio.entidades.Limite;
import negocio.entidades.Mes;
import negocio.entidades.Meta;
import negocio.entidades.Parametros;
import negocio.entidades.RetornoInvestimento;
import negocio.entidades.ValorLista;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.MesSemGastosException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.TIRImpossivelException;
import negocio.exceptions.ValorInvalidoException;
import negocio.exceptions.ValorNegativoException;

public class Fachada implements Initializable{
	private static NegocioMes negocioMes = new NegocioMes();
	private static NegocioGrafico negocioGrafico = new NegocioGrafico();
	private static CategoriaManager categoriaManager = new CategoriaManager(Categoria.carregarCategorias());
	private static GastoManager gastoManager = new GastoManager(categoriaManager);
	private static MensalidadeManager mensalidadeManager = new MensalidadeManager(categoriaManager);
	private static LembreteManager lembreteManager = new LembreteManager(mensalidadeManager, categoriaManager);
	private static LimiteManager limiteManager = new LimiteManager(Categoria.carregarCategorias(), Limite.carregarLimites(Categoria.carregarCategorias()), lembreteManager);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.lembreteManager.setLimiteManager(limiteManager);
	}
	
	//Gastos
	public ArrayList<ValorLista> inicializarGastos(){
		ArrayList<ValorLista> gastos = new ArrayList<>();
		
		for(Gasto g: gastoManager.listarGastos()) {
			String strG = "R$ "+g.getValor() + " - "+ g.getNome();
			gastos.add(new ValorLista(strG, g.getId()));
		}
		
		return gastos;
	}
	
	public void removerGasto(int id) throws IOException, CampoVazioException {
		gastoManager.removerGasto(id);
	}
	
	public void criarGasto(String nome, double valor, LocalDate data, String categoria) throws CampoVazioException {
		Categoria c = Categoria.carregarCategorias().stream().filter(x -> x.getNome().equals(categoria)).findFirst().orElse(null);
		try {
			gastoManager.adicionarGasto(new Gasto(nome, valor, c, data));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Gasto getGasto(int v) {
		return gastoManager.listarGastos().stream().filter(x->x.getId()==v).findFirst().orElse(null);
	}
	
	public void editarGasto(int id, String nome, Double valor, LocalDate data, String categoria) throws CampoVazioException{
		Categoria c = Categoria.carregarCategorias().stream().filter(x->x.getNome().equals(categoria)).findFirst().orElse(null);
		try {
			gastoManager.editarGasto(id, nome, valor, data, c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Lembretes
	public ArrayList<ValorLista> inicializarLembretes(){
		ArrayList<ValorLista> lembretes = new ArrayList<>();
		
		for(Lembrete l: lembreteManager.listarTodos()) {
			lembretes.add(new ValorLista(l.gerarNotificacao(), l.getId(), l.isAtivo()));
		}
		return lembretes;
	}
	
	public void criarLembrete(String titulo, String descricao, LocalDate data) throws IOException, ObjetoNuloException {
		lembreteManager.criarLembrete(new Lembrete(titulo, descricao, data));
	}
	
	public void atualizarLembrete(int id, String titulo, String descricao, LocalDate data) throws ObjetoNaoEncontradoException, IOException, CampoVazioException {
		lembreteManager.atualizarLembrete(id, titulo, descricao, data);
	}
	
	public void removerLembrete(int id) throws ObjetoNaoEncontradoException, IOException {
		lembreteManager.removerLembrete(id);
	}
	
	public void setLembreteAtivo(int id, boolean mudar) throws ObjetoNaoEncontradoException, IOException, CampoVazioException {
		Lembrete l = lembreteManager.buscarPorId(id);
		lembreteManager.ativarDesativarLembrete(l.getId(), mudar);
	}
	
	public Lembrete getLembrete(int id) {
		return lembreteManager.buscarPorId(id);
	}
	
	public ArrayList<Lembrete> lembretesNotificados() throws IOException{
		return lembreteManager.getLembreteDia();
	}
	
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
		
		for(Categoria c : categoriaManager.getCategorias()) {
			categorias.add(new ValorLista(c.getNome(), c.getId()));
		}
		return categorias;
	}
	
	public void criarCategoria(String nome) throws CampoVazioException {
		categoriaManager.criar(nome);
	}
	
	public void editarCategoria(int id, String novoNome) throws ValorNegativoException, ObjetoNaoEncontradoException, CampoVazioException {
		categoriaManager.editar(id, novoNome);
	}
	
	public void removerCategoria(int id) throws ValorNegativoException, ObjetoNaoEncontradoException {
		categoriaManager.deletar(id);
	}
	
	public Categoria getCategoria(int v) {
		return categoriaManager.getCategoria(v);
	}
	
	//Limites
	
	public ArrayList<ValorLista> inicializarLimites(){
		ArrayList<ValorLista> limites = new ArrayList<>();
		
		for(Limite l : limiteManager.getLimites()) {
			limites.add(new ValorLista("Limite de "+l.getCategoria().getNome()+": R$"+l.getValor(), l.getId()));
		}
		return limites;
	}
	
	public void criarLimite(int idCat, double valor) throws ObjetoNaoEncontradoException, ValorNegativoException, ObjetoNuloException, IOException, CampoVazioException {
		limiteManager.criar(idCat, valor);
	}
	
	public void editarLimite(int id, double valor) throws ObjetoNaoEncontradoException, ValorNegativoException, IOException {
		limiteManager.editar(id, valor);
	}
	
	public void removerLimite(int id) throws ObjetoNaoEncontradoException, ValorNegativoException, IOException {
		limiteManager.deletar(id);
	}
	
	public Limite getLimite(int id) {
		return limiteManager.getLimite(id);
	}
	
	//Graficos
	public ArrayList<String> inicializarMeses() {
		ArrayList<String> meses = new ArrayList<String>();
		for(Mes m:negocioMes.getMeses()) {
			meses.add(m.toString());
		}
		return meses;
	}
	
	public GraficoSetores inicializarGraficoSetores() throws MesSemGastosException, CampoVazioException {
		LocalDate lD = LocalDate.now();
		return negocioGrafico.vizualizarGraficoSetores(new Mes(lD.getMonthValue(), lD.getYear()));
	}
	
	public GraficoSetores inicializarGraficoSetoresMes(String m) throws MesSemGastosException, CampoVazioException {
		String[] valores = m.split("/");
		Mes mes = new Mes(Integer.valueOf(valores[0]), Integer.valueOf(valores[1])); 
		return negocioGrafico.vizualizarGraficoSetores(mes);
	}
	
	public GraficoBarras inicializarGraficoBarrasCategoria(String nome) throws CampoVazioException, CategoriaSemGastosException {
		Categoria c = new Categoria(nome);
		return negocioGrafico.vizualizarGraficoBarras(c);
	}
	
	//Investimentos
	public RetornoInvestimento investimentos(Parametros p) throws OpcaoInvalidaException, ValorInvalidoException, FormatacaoInvalidaException, TIRImpossivelException {
		menuPrincipal mp = new menuPrincipal();
		return mp.inputMenu(p.getInput1(), p.getInput2(), p.getValor(), p.getTaxa(), p.getNumParcelas(), p.getTipo(), p.getTempo(), p.getArrecadacao());
	}
}