package fachada;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import negocio.CambioNegocio;
import negocio.CategoriaManager;
import negocio.GastoManager;
import negocio.LembreteManager;
import negocio.LimiteManager;
import negocio.MensalidadeManager;
import negocio.MetaManager;
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
import negocio.exceptions.ErroAoReceberConversaoException;
import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.LimiteDeConvesoesException;
import negocio.exceptions.MesSemGastosException;
import negocio.exceptions.ObjetoJaExisteException;
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

public class Fachada {
	private CambioNegocio cambioNegocio;
	private static NegocioMes negocioMes = new NegocioMes();
	private static NegocioGrafico negocioGrafico = new NegocioGrafico();
	private static CategoriaManager categoriaManager = new CategoriaManager();
	private static GastoManager gastoManager = new GastoManager(categoriaManager);
	private static MensalidadeManager mensalidadeManager = new MensalidadeManager(categoriaManager);
	private static LimiteManager limiteManager = new LimiteManager(categoriaManager, gastoManager);
	private static MetaManager metaManager = new MetaManager();
	private static LembreteManager lembreteManager = new LembreteManager(mensalidadeManager, limiteManager, metaManager);

	public Fachada() {
		limiteManager.setLembreteManager(lembreteManager);
		metaManager.setLembreteManager(lembreteManager);
	}
	
	// Gastos
	/**
	 * Inicializa para a tela todos os gastos
	 * 
	 * @return ArrayList de Valor Lista contendo um String e o id do gasto
	 */
	public ArrayList<ValorLista> inicializarGastos() {
		ArrayList<ValorLista> gastos = new ArrayList<>();

		for (Gasto g : gastoManager.listarGastos()) {
			String strG = "R$ " + g.getValor() + " - " + g.getNome() + " (" + g.getDataCriacao() + ")";
			gastos.add(new ValorLista(strG, g.getId()));
		}
		for (Mensalidade m : mensalidadeManager.listarMensalidades()) {
			String strM = "MENSALIDADE R$ " + m.getValor() + " - " + m.getNome() + " (" + m.getDataCriacao() + ")";
			gastos.add(new ValorLista(strM, m.getId()));
		}
		return gastos;
	}

	/**
	 * Método que remove um gasto
	 * 
	 * @param id   Id do gasto
	 * @param nome (Nome do gasto de acordo com o que foi colocado em ValorLista)
	 * @throws ObjetoNaoEncontradoException
	 */
	public void removerGasto(int id, String nome) throws ObjetoNaoEncontradoException {
		if (nome.contains("MENSALIDADE")) {
			mensalidadeManager.removerMensalidade(id);
		} else {
			gastoManager.removerGasto(id);
		}
	}

	/**
	 * Método que cria um gasto novo
	 * 
	 * @param nome        Nome do gasto
	 * @param valor       Valor do gasto
	 * @param data        Data de realização do gasto
	 * @param categoria   Categoria associada ao gasto
	 * @param recorrencia Para gastos da categoria Mensal
	 * @throws CampoVazioException
	 */
	public void criarGasto(String nome, double valor, LocalDate data, String categoria, String recorrencia)
			throws CampoVazioException {
		Categoria c = categoriaManager.getCategorias().stream().filter(x -> x.getNome().equals(categoria)).findFirst()
				.orElse(null);
		if (c.getNome().equals("Mensal")) {
			mensalidadeManager
					.adicionarMensalidade(new Mensalidade(nome, valor, data.plusMonths(1), c, data, recorrencia));
		} else {
			gastoManager.adicionarGasto(new Gasto(nome, valor, c, data));
		}
	}

	/**
	 * Método que busca um gasto específico
	 * 
	 * @param id   Id do gasto desejado
	 * @param nome Nome do gasto na listagem
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
	 * 
	 * @param id              Id do gasto
	 * @param nome            Novo nome
	 * @param valor           Novo valor
	 * @param data            Nova data
	 * @param novaRecorrencia Recorrencia caso seja uma mensalidade
	 * @param c               Categoria associada ao gasto
	 * @throws CampoVazioException
	 * @throws ObjetoNaoEncontradoException
	 */
	public void editarGasto(int id, String nome, double valor, LocalDate data, String novaRecorrencia, Categoria c)
			throws CampoVazioException, ObjetoNaoEncontradoException {
		if (c.getNome().equals("Mensal")) {
			mensalidadeManager.editarMensalidade(id, nome, valor, data, novaRecorrencia, false);
		} else {
			gastoManager.editarGasto(id, nome, valor, data);
		}
	}

	// Lembretes
	/**
	 * Método que lista os lembretes cadastrados
	 * 
	 * @return ArrayList<ValorLista> com os Strings que serão listados e os ids de
	 *         cada lembrete
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
	 * 
	 * @param titulo    Titulo do lembrete
	 * @param descricao Descricao do lembrete
	 * @param data      Data de aviso do lembrete
	 * @throws ObjetoNuloException
	 */
	public void criarLembrete(String titulo, String descricao, LocalDate data) throws ObjetoNuloException {
		lembreteManager.criarLembrete(new Lembrete(titulo, descricao, data));
	}

	/**
	 * Método para editar lembrete
	 * 
	 * @param id        Id do lembrete a ser editado
	 * @param titulo    Novo titulo
	 * @param descricao Nova descricao
	 * @param data      Nova data
	 * @throws ObjetoNaoEncontradoException
	 * @throws CampoVazioException
	 */
	public void atualizarLembrete(int id, String titulo, String descricao, LocalDate data, boolean ativo)
			throws ObjetoNaoEncontradoException, CampoVazioException {
		lembreteManager.atualizarLembrete(id, titulo, descricao, data, ativo);
	}

	/**
	 * Método para remover um lembrete
	 * 
	 * @param id do lembrete
	 * @throws ObjetoNaoEncontradoException
	 */
	public void removerLembrete(int id) throws ObjetoNaoEncontradoException {
		lembreteManager.removerLembrete(id);
	}

	/**
	 * Método que ativa ou desativa um lembrete
	 * 
	 * @param id    Id do lembrete
	 * @param mudar Novo estado do lembrete
	 * @throws ObjetoNaoEncontradoException
	 * @throws CampoVazioException
	 */
	public void setLembreteAtivo(int id, boolean mudar) throws ObjetoNaoEncontradoException, CampoVazioException {
		Lembrete l = lembreteManager.buscarPorId(id);
		lembreteManager.atualizarLembrete(id, l.getTitulo(), l.getDescricao(), l.getDataAlerta(), mudar);
	}

	/**
	 * Método que recupera um lembrete
	 * 
	 * @param id do lembrete
	 * @return Lembrete encontrado ou null se não houver esse lembrete
	 */
	public Lembrete getLembrete(int id) {
		return lembreteManager.buscarPorId(id);
	}

	/**
	 * Método que busca os lembretes próximos da data de aviso
	 * 
	 * @return ArrayList<Lembrete> com os lembretes notificados
	 */
	public ArrayList<Lembrete> lembretesNotificados() {
		return lembreteManager.getLembreteDia();
	}

	// Metas
	/**
	 * Método para criar metas
	 * 
	 * @param descricao     Descrica da meta
	 * @param valorObjetivo Valor Objetivo
	 * @param valorAtual    Valor atual
	 * @param data          Data máxima da meta
	 * @throws ObjetoNuloException
	 * @throws CampoVazioException
	 */
	public void criarMeta(String descricao, double valorObjetivo, double valorAtual, LocalDate data)
			throws CampoVazioException, ObjetoNuloException {
		metaManager.criar(descricao, valorObjetivo, valorAtual, data);
	}

	/**
	 * Método que lista todas as metas
	 * 
	 * @return ArrayList<ValorLista> que possui o texto que será exibido na listagem
	 *         e o id do lembrete
	 */
	public ArrayList<ValorLista> inicializarMetas() {
		ArrayList<ValorLista> metas = new ArrayList<>();

		for (Meta m : metaManager.getMetas()) {
			metas.add(new ValorLista(m.getDescricao() + " - (" + m.getValorAtual() + "/" + m.getValorObjetivo() + ")",
					m.getId()));
		}

		return metas;
	}

	/**
	 * Método que remove uma meta
	 * 
	 * @param id Id da meta
	 * @throws ObjetoNaoEncontradoException
	 */
	public void removerMeta(int id) throws ObjetoNaoEncontradoException {
		metaManager.deletar(id);
	}

	/**
	 * Método que edita uma meta
	 * 
	 * @param id        ID da meta
	 * @param descricao Nova descricao
	 * @param valorOb   Novo valor de objetivo
	 * @param valorAt   Novo valor atual
	 * @param data      Nova data máxima
	 * @throws CampoVazioException
	 * @throws ObjetoNaoEncontradoException
	 */
	public void editarMeta(int id, String descricao, double valorOb, double valorAt, LocalDate data)
			throws ObjetoNaoEncontradoException, CampoVazioException {
		metaManager.editar(id, descricao, valorOb, valorAt, data);
	}

	/**
	 * Método que recupera uma meta pelo id
	 * 
	 * @param id da meta
	 * @return Meta encontrada ou null se não foi encontrada
	 */
	public Meta getMeta(int id) {
		List<Meta> metas = metaManager.getMetas();
		Meta m = metas.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		return m;
	}

	// Categorias
	/**
	 * Método que inicializa as categorias para a interface
	 * 
	 * @return ArrayList<ValorLista> que contem o texto referente a cada categoria e
	 *         o id de cada uma
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
	 * 
	 * @param nome
	 * @throws CampoVazioException
	 * @throws ObjetoJaExisteException
	 */
	public void criarCategoria(String nome) throws ObjetoJaExisteException, CampoVazioException {
		categoriaManager.criar(nome);
	}

	/**
	 * Método que edita uma categoria
	 * 
	 * @param id   da categoria
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
	 * 
	 * @param id da categoria
	 * @throws ValorNegativoException
	 * @throws ObjetoNaoEncontradoException
	 */
	public void removerCategoria(int id) throws ValorNegativoException, ObjetoNaoEncontradoException {
		categoriaManager.deletar(id);
	}

	/**
	 * Método que recupera uma categoria
	 * 
	 * @param id da categoria
	 * @return Uma categoria ou null se não existir
	 */
	public Categoria getCategoria(int v) {
		return categoriaManager.getCategoria(v);
	}

	// Limites

	/**
	 * Método que inicializa limites
	 * 
	 * @return Um ArrayList<ValorLista> com todos os textos para a listagem dos
	 *         limites e os ids dos limites
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
	 * 
	 * @param id    da categoria
	 * @param valor
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 * @throws ObjetoNuloException
	 * @throws ObjetoJaExisteException
	 */
	public void criarLimite(int idCat, double valor)
			throws ObjetoNaoEncontradoException, ValorNegativoException, ObjetoNuloException, ObjetoJaExisteException {
		limiteManager.criar(idCat, valor);
	}

	/**
	 * Método que edita um limite
	 * 
	 * @param id   do limites
	 * @param novo valor
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 * @throws IOException
	 */
	public void editarLimite(int id, double valor) throws ObjetoNaoEncontradoException, ValorNegativoException {
		limiteManager.editar(id, valor);
	}

	/**
	 * Método que remove um limite
	 * 
	 * @param id do limite
	 * @throws ObjetoNaoEncontradoException
	 * @throws ValorNegativoException
	 * @throws IOException
	 */
	public void removerLimite(int id) throws ObjetoNaoEncontradoException, ValorNegativoException {
		limiteManager.deletar(id);
	}

	/**
	 * Método que recupera um limite
	 * 
	 * @param id do limite
	 * @return Limite encontrado ou null se não for encontrado
	 */
	public Limite getLimite(int id) {
		return limiteManager.getLimite(id);
	}

	// Graficos
	/**
	 * Método que inicializa os meses para serem listados na interfaca
	 * 
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
	 * 
	 * @return GraficoSetores com os dados do mês atual
	 * @throws MesSemGastosException
	 * @throws CampoVazioException
	 */
	public GraficoSetores inicializarGraficoSetores() throws MesSemGastosException, CampoVazioException {
		LocalDate lD = LocalDate.now();
		GraficoSetores gs = null;
		gs = negocioGrafico.vizualizarGraficoSetores(new Mes(lD.getMonthValue(), lD.getYear()), categoriaManager,
				gastoManager, mensalidadeManager);
		return gs;
	}

	/**
	 * Método que inicializa um gráfico de setores para diferentes meses
	 * 
	 * @param string referente ao mês
	 * @return GraficoSetores gerado
	 * @throws MesSemGastosException
	 */
	public GraficoSetores inicializarGraficoSetoresMes(String m) throws MesSemGastosException {
		String[] valores = m.split("/");
		Mes mes = new Mes(Integer.valueOf(valores[0]), Integer.valueOf(valores[1]));
		return negocioGrafico.vizualizarGraficoSetores(mes, categoriaManager, gastoManager, mensalidadeManager);
	}

	/**
	 * Método que inicializa um gráfico de barras para diferentes categorias
	 * 
	 * @param nome da categoria
	 * @return GraficoBarras gerado
	 * @throws CampoVazioException
	 * @throws CategoriaSemGastosException
	 */
	public GraficoBarras inicializarGraficoBarrasCategoria(String nome)
			throws CampoVazioException, CategoriaSemGastosException {
		Categoria c = new Categoria(nome);
		return negocioGrafico.vizualizarGraficoBarras(c, gastoManager, negocioMes, mensalidadeManager);
	}

	// Investimentos
	/**
	 * Método único para lidar com os investimentos
	 * 
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

	// Câmbio

	public void inicializarCambio() {
		cambioNegocio = new CambioNegocio();
	}

	public ArrayList<String> getMoedasDestino() {
		return cambioNegocio.getMoedasdestino();
	}

	public double realizarCambio(double valor, String destino)
			throws URISyntaxException, IOException, LimiteDeConvesoesException, ErroAoReceberConversaoException {
		return cambioNegocio.realizarCambio(valor, destino);
	}
}