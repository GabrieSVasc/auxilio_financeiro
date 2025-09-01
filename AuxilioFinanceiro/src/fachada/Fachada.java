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
import dados.MetaManager;
import javafx.fxml.Initializable;
import negocio.NegocioGrafico;
import negocio.NegocioInvestimentosPrincipal;
import negocio.NegocioMes;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Lembrete;
import negocio.entidades.Limite;
import negocio.entidades.Mensalidade;
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

/**
 * Classe fachada, para onde as tela mandam todas as suas requisições
 * 
 * @author Maria Gabriela
 */

public class Fachada implements Initializable {
	private static NegocioMes negocioMes = new NegocioMes();
	private static NegocioGrafico negocioGrafico = new NegocioGrafico();
	private static CategoriaManager categoriaManager = new CategoriaManager(Categoria.carregarCategorias());
	private static GastoManager gastoManager = new GastoManager(categoriaManager);
	private static MensalidadeManager mensalidadeManager = new MensalidadeManager(categoriaManager);
	private static LembreteManager lembreteManager = new LembreteManager(mensalidadeManager);
	private static LimiteManager limiteManager = new LimiteManager(Categoria.carregarCategorias(),
			Limite.carregarLimites(Categoria.carregarCategorias()), lembreteManager);
	private static MetaManager metaManager = new MetaManager(Meta.carregarMetas(), lembreteManager);

	@Override
 	public void initialize(URL arg0, ResourceBundle arg1) {
		lembreteManager.setLimiteManager(limiteManager);
	}

	// Gastos
	/**
	 * Inicializa para a tela todos os gastos
	 * @return ArrayList de Valor Lista contendo um String e o id do gasto
	 */
	public ArrayList<ValorLista> inicializarGastos() {
		ArrayList<ValorLista> gastos = new ArrayList<>();

		for (Gasto g : gastoManager.listarGastos()) {
			String strG = "R$ " + g.getValor() + " - " + g.getNome();
			gastos.add(new ValorLista(strG, g.getId()));
		}
		for (Mensalidade m : mensalidadeManager.listarMensalidades()) {
			String strM = "MENSALIDADE R$ " + m.getValor() + " - " + m.getNome();
			gastos.add(new ValorLista(strM, m.getId()));
		}
		return gastos;
	}
	
	/**
	 * Método que remove um gasto
	 * @param id do gasto
	 * @param nome (Nome do gasto de acordo com o que foi colocado em ValorLista)
	 * @throws IOException
	 * @throws CampoVazioException
	 * @throws ObjetoNaoEncontradoException
	 */
	public void removerGasto(int id, String nome)
			throws IOException, CampoVazioException, ObjetoNaoEncontradoException {
		if (nome.contains("MENSALIDADE")) {
			mensalidadeManager.removerMensalidade(id);
		} else {
			gastoManager.removerGasto(id);
		}
	}

	/**
	 * Método que cria um gasto novo
	 * @param nome
	 * @param valor
	 * @param data
	 * @param categoria
	 * @param recorrencia para gastos da categoria Mensal
	 * @throws CampoVazioException
	 * @throws IOException
	 */
	public void criarGasto(String nome, double valor, LocalDate data, String categoria, String recorrencia)
			throws CampoVazioException, IOException {
		Categoria c = Categoria.carregarCategorias().stream().filter(x -> x.getNome().equals(categoria)).findFirst()
				.orElse(null);
		if (c.getNome().equals("Mensal")) {
			mensalidadeManager
					.adicionarMensalidade(new Mensalidade(nome, valor, data.plusMonths(1), c, data, recorrencia));
		} else {
			try {
				gastoManager.adicionarGasto(new Gasto(nome, valor, c, data));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Método que busca um gasto específico
	 * @param id do gasto desejado
	 * @param nome do gasto na listagem
	 * @return Gasto encontrado ou null
	 */
	public Gasto getGasto(int v, String nome) {
		Gasto retorno = null;
		if (nome.contains("MENSALIDADE")) {
			retorno = mensalidadeManager.listarMensalidades().stream().filter(x -> x.getId() == v).findFirst()
					.orElse(null);
		} else {
			retorno = gastoManager.listarGastos().stream().filter(x -> x.getId() == v).findFirst().orElse(null);
		}
		return retorno;
	}
	
	/**
	 * Método para edição de um gasto
	 * @param id do gasto a ser editado
	 * @param Novo nome
	 * @param Novo valor
	 * @param Nova data
	 * @param Categoria do gasto
	 * @throws CampoVazioException
	 * @throws IOException
	 * @throws ObjetoNaoEncontradoException
	 */
	public void editarGasto(int id, String nome, Double valor, LocalDate data, Categoria c)
			throws CampoVazioException, IOException, ObjetoNaoEncontradoException {
		if (c.getNome().equals("Mensal")) {
			mensalidadeManager.editarMensalidade(id, nome, valor, nome, c, nome, false);
		} else {
			try {
				gastoManager.editarGasto(id, nome, valor, data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Lembretes
	/**
	 * Método que lista os lembretes cadastrados
	 * @return ArrayList<ValorLista> com os Strings que serão listados e os ids de cada lembrete
	 */
	public ArrayList<ValorLista> inicializarLembretes() {
		ArrayList<ValorLista> lembretes = new ArrayList<>();

		for (Lembrete l : lembreteManager.listarTodos()) {
			lembretes.add(new ValorLista(l.gerarNotificacao(), l.getId(), l.isAtivo()));
		}
		return lembretes;
	}
	
	/**
	 * Método para criar um lembrete
	 * @param titulo do lembrete
	 * @param descricao do lembrete
	 * @param data de aviso do lembrete
	 * @throws IOException
	 * @throws ObjetoNuloException
	 */
	public void criarLembrete(String titulo, String descricao, LocalDate data) throws IOException, ObjetoNuloException {
		lembreteManager.criarLembrete(new Lembrete(titulo, descricao, data));
	}

	/**
	 * Método para editar lembrete
	 * @param id do lembrete a ser editado
	 * @param novo titulo
	 * @param nova descricao
	 * @param nova data
	 * @throws ObjetoNaoEncontradoException
	 * @throws IOException
	 * @throws CampoVazioException
	 */
	public void atualizarLembrete(int id, String titulo, String descricao, LocalDate data)
			throws ObjetoNaoEncontradoException, IOException, CampoVazioException {
		lembreteManager.atualizarLembrete(id, titulo, descricao, data);
	}

	/**
	 * Método para remover um lembrete
	 * @param id do lembrete
	 * @throws ObjetoNaoEncontradoException
	 * @throws IOException
	 */
	public void removerLembrete(int id) throws ObjetoNaoEncontradoException, IOException {
		lembreteManager.removerLembrete(id);
	}
	
	/**
	 * Método que ativa ou desativa um lembrete
	 * @param id do lembrete
	 * @param novo estado do lembrete
	 * @throws ObjetoNaoEncontradoException
	 * @throws IOException
	 * @throws CampoVazioException
	 */
	public void setLembreteAtivo(int id, boolean mudar)
			throws ObjetoNaoEncontradoException, IOException, CampoVazioException {
		Lembrete l = lembreteManager.buscarPorId(id);
		lembreteManager.ativarDesativarLembrete(l.getId(), mudar);
	}
	
	/**
	 * Método que recupera um lembrete
	 * @param id do lembrete
	 * @return Lembrete encontrado ou null se não houver esse lembrete
	 */
	public Lembrete getLembrete(int id) {
		return lembreteManager.buscarPorId(id);
	}
	
	/**
	 * Método que busca os lembretes próximos da data de aviso
	 * @return ArrayList<Lembrete> com os lembretes notificados
	 * @throws IOException
	 */
	public ArrayList<Lembrete> lembretesNotificados() throws IOException {
		return lembreteManager.getLembreteDia();
	}

	// Metas
	/**
	 * Método para criar metas
	 * @param descricao da meta
	 * @param valorObjetivo
	 * @param valorAtual
	 * @param data máxima da meta
	 * @throws ObjetoNuloException 
	 * @throws IOException 
	 * @throws CampoVazioException 
	 */
	public void criarMeta(String descricao, double valorObjetivo, double valorAtual, LocalDate data) throws CampoVazioException, IOException, ObjetoNuloException {
		metaManager.criar(descricao, valorObjetivo, valorAtual, data);
	}
	
	/**
	 * Método que lista todas as metas
	 * @return ArrayList<ValorLista> que possui o texto que será exibido na listagem e o id do lembrete
	 */
	public ArrayList<ValorLista> inicializarMetas() {
		ArrayList<ValorLista> metas = new ArrayList<>();

		for (Meta m : Meta.carregarMetas()) {
			metas.add(new ValorLista(m.getDescricao() + " - (" + m.getValorAtual() + "/" + m.getValorObjetivo() + ")",
					m.getId()));
		}

		return metas;
	}
	
	/**
	 * Método que remove uma meta
	 * @param id
	 * @throws IOException 
	 * @throws ObjetoNaoEncontradoException
	 */
	public void removerMeta(int id) throws ObjetoNaoEncontradoException, IOException {
		metaManager.deletar(id);
	}
	
	/**
	 * Método que edita uma meta
	 * @param id da meta
	 * @param nova descricao
	 * @param novo valor de objetivo
	 * @param novo valor Atual
	 * @param nova data
	 * @throws CampoVazioException 
	 * @throws IOException 
	 * @throws ObjetoNaoEncontradoException 
	 */
	public void editarMeta(int id, String descricao, double valorOb,double valorAt,  LocalDate data) throws ObjetoNaoEncontradoException, IOException, CampoVazioException {
		metaManager.editar(id, descricao, valorOb, valorAt, data);
	}
	
	/**
	 * Método que recupera uma meta pelo id
	 * @param id da meta
	 * @return Meta encontrada ou null se não foi encontrada
	 */
	public Meta getMeta(int id) {
		List<Meta> metas = Meta.carregarMetas();
		Meta m = metas.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		return m;
	}

	// Categorias
	/**
	 * Método que inicializa as categorias para a interface
	 * @return ArrayList<ValorLista> que contem o texto referente a cada categoria e o id de cada uma
	 */
	public ArrayList<ValorLista> inicializarCategorias() {
		ArrayList<ValorLista> categorias = new ArrayList<ValorLista>();

		for (Categoria c : categoriaManager.getCategorias()) {
			categorias.add(new ValorLista(c.getNome(), c.getId()));
		}
		return categorias;
	}

	/**
	 * Método que cria uma categoria
	 * @param nome
	 * @throws CampoVazioException
	 */
	public void criarCategoria(String nome) throws CampoVazioException {
		categoriaManager.criar(nome);
	}
	
	/**
	 * Método que edita uma categoria
	 * @param id da categoria
	 * @param novo nome
	 * @throws ValorNegativoException
	 * @throws ObjetoNaoEncontradoException
	 * @throws CampoVazioException
	 */
	public void editarCategoria(int id, String novoNome)
			throws ValorNegativoException, ObjetoNaoEncontradoException, CampoVazioException {
		categoriaManager.editar(id, novoNome);
	}
	
	/**
	 * Método que remove uma categoria
	 * @param id da categoria
	 * @throws ValorNegativoException
	 * @throws ObjetoNaoEncontradoException
	 */
	public void removerCategoria(int id) throws ValorNegativoException, ObjetoNaoEncontradoException {
		categoriaManager.deletar(id);
	}
	
	/**
	 * Método que recupera uma categoria
	 * @param id da categoria
	 * @return Uma categoria ou null se não existir
	 */
	public Categoria getCategoria(int v) {
		return categoriaManager.getCategoria(v);
	}

	// Limites
	
	/**
	 * Método que inicializa limites
	 * @return Um ArrayList<ValorLista> com todos os textos para a listagem dos limites e os ids dos limites
	 */
	public ArrayList<ValorLista> inicializarLimites() {
		ArrayList<ValorLista> limites = new ArrayList<>();

		for (Limite l : limiteManager.getLimites()) {
			limites.add(new ValorLista("Limite de " + l.getCategoria().getNome() + ": R$" + l.getValor(), l.getId()));
		}
		return limites;
	}

	/**
	 * Método que cria um limite
	 * @param id da categoria
	 * @param valor
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 * @throws ObjetoNuloException
	 * @throws IOException
	 * @throws CampoVazioException
	 */
	public void criarLimite(int idCat, double valor) throws ObjetoNaoEncontradoException, ValorNegativoException,
			ObjetoNuloException, IOException, CampoVazioException {
		limiteManager.criar(idCat, valor);
	}
	
	/**
	 * Método que edita um limite
	 * @param id do limites
	 * @param novo valor
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 * @throws IOException
	 */
	public void editarLimite(int id, double valor)
			throws ObjetoNaoEncontradoException, ValorNegativoException, IOException {
		limiteManager.editar(id, valor);
	}
	
	/**
	 * Método que remove um limite
	 * @param id do limite
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 * @throws IOException
	 */
	public void removerLimite(int id) throws ObjetoNaoEncontradoException, ValorNegativoException, IOException {
		limiteManager.deletar(id);
	}

	/**
	 * Método que recupera um limite
	 * @param id do limite
	 * @return Limite encontrado ou null se não for encontrado
	 */
	public Limite getLimite(int id) {
		return limiteManager.getLimite(id);
	}

	// Graficos
	/**
	 * Método que inicializa os meses para serem listados na interfaca
	 * @return ArrayList<String> com os meses
	 */
	public ArrayList<String> inicializarMeses() {
		ArrayList<String> meses = new ArrayList<String>();
		for (Mes m : negocioMes.getMeses()) {
			meses.add(m.toString());
		}
		return meses;
	}
	
	/**
	 * Método que inicializa o gráfico de setores do mês atual 
	 * @return GraficoSetores com os dados do mês atual
	 * @throws MesSemGastosException
	 */
	public GraficoSetores inicializarGraficoSetores() throws MesSemGastosException{
		LocalDate lD = LocalDate.now();
		GraficoSetores gs = null;
		try {
			gs = negocioGrafico.vizualizarGraficoSetores(new Mes(lD.getMonthValue(), lD.getYear()));
		} catch (CampoVazioException e) {
		}
		return gs;
	}

	/**
	 * Método que inicializa um gráfico de setores para diferentes meses
	 * @param string referente ao mês
	 * @return GraficoSetores gerado
	 * @throws MesSemGastosException
	 */
	public GraficoSetores inicializarGraficoSetoresMes(String m) throws MesSemGastosException{
		String[] valores = m.split("/");
		Mes mes = new Mes(Integer.valueOf(valores[0]), Integer.valueOf(valores[1]));
		try {
			return negocioGrafico.vizualizarGraficoSetores(mes);
		} catch (CampoVazioException e) {
		}
		return null;
	}

	/**
	 * Método que inicializa um gráfico de barras para diferentes categorias
	 * @param nome da categoria
	 * @return GraficoBarras gerado
	 * @throws CampoVazioException
	 * @throws CategoriaSemGastosException
	 */
	public GraficoBarras inicializarGraficoBarrasCategoria(String nome)
			throws CampoVazioException, CategoriaSemGastosException {
		Categoria c = new Categoria(nome);
		return negocioGrafico.vizualizarGraficoBarras(c);
	}

	// Investimentos
	/**
	 * Método único para lidar com os investimentos
	 * @param Parametros com os parametros de investimento
	 * @return RetornoInvestimento com os retornos da simulação
	 * @throws OpcaoInvalidaException
	 * @throws ValorInvalidoException
	 * @throws FormatacaoInvalidaException
	 * @throws TIRImpossivelException
	 */
	public RetornoInvestimento investimentos(Parametros p)
			throws OpcaoInvalidaException, ValorInvalidoException, FormatacaoInvalidaException, TIRImpossivelException {
		NegocioInvestimentosPrincipal mp = new NegocioInvestimentosPrincipal();
		return mp.inputMenu(p.getInput1(), p.getInput2(), p.getValor(), p.getTaxa(), p.getNumParcelas(), p.getTipo(),
				p.getTempo(), p.getArrecadacao());
	}
}