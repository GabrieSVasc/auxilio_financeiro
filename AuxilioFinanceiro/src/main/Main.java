package main;

import iu.viewController.CategoriasViewController;
import iu.viewController.DadosAmortizacaoViewController;
import iu.viewController.DadosDescontoViewController;
import iu.viewController.DadosInvestimentoViewController;
import iu.viewController.DadosTaxaInternaRetornoViewController;
import iu.viewController.DadosVPLPadraoViewController;
import iu.viewController.DadosVariacaoViewController;
import iu.viewController.EditarCategoriaViewController;
import iu.viewController.EditarGastoViewController;
import iu.viewController.EditarLembreteViewController;
import iu.viewController.EditarLimiteViewController;
import iu.viewController.EditarMetaViewController;
import iu.viewController.GastosViewController;
import iu.viewController.GerenciarGastosViewController;
import iu.viewController.InicialViewController;
import iu.viewController.LembretesViewController;
import iu.viewController.LimitesViewController;
import iu.viewController.MetasViewController;
import iu.viewController.NovaCategoriaViewController;
import iu.viewController.NovaMetaViewController;
import iu.viewController.NovoGastoViewController;
import iu.viewController.NovoLembreteViewController;
import iu.viewController.NovoLimiteViewController;
import iu.viewController.ResumosGastosViewController;
import iu.viewController.TutoriaisViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main do sistema, realiza a troca entre as telas
 * 
 * @author Maria Gabriela
 */

public class Main extends Application {
	private static Stage stage;
	private static Scene cenaInicial;
	private static Scene cenaCambio;
	private static Scene cenaGerenciarGastos;
	private static Scene cenaResumosGastos;
	private static Scene cenaLimites;
	private static Scene cenaLembretes;
	private static Scene cenaMetas;
	private static Scene cenaGastos;
	private static Scene cenaCategorias;
	private static Scene cenaNovoLembrete;
	private static Scene cenaNovoGasto;
	private static Scene cenaNovoLimite;
	private static Scene cenaNovaMeta;
	private static Scene cenaNovaCategoria;
	private static Scene cenaEditarLembrete;
	private static Scene cenaEditarGasto;
	private static Scene cenaEditarLimite;
	private static Scene cenaEditarMeta;
	private static Scene cenaEditarCategoria;
	private static Scene cenaTutoriais;
	private static Scene cenaPrincipalInvestimentos;
	private static Scene cenaInvestimentos;
	private static Scene cenaDadosInvestimento;
	private static Scene cenaAmortizacao;
	private static Scene cenaDadosAmortizacao;
	private static Scene cenaVariacao;
	private static Scene cenaDadosVariacao;
	private static Scene cenaValorPresenteLiquido;
	private static Scene cenaDadosTaxaInternaRetorno;
	private static Scene cenaDadosVPLPadrao;
	private static Scene cenaDescontoTitulo;
	private static Scene cenaDadosDesconto;

	private static GerenciarGastosViewController ggVC;
	private static ResumosGastosViewController rgVC;
	private static MetasViewController mVC;
	private static EditarMetaViewController emVC;
	private static GastosViewController gVC;
	private static NovoGastoViewController ngVC;
	private static NovaMetaViewController nmVC;
	private static EditarGastoViewController egVC;
	private static CategoriasViewController cVC;
	private static EditarCategoriaViewController ecVC;
	private static NovaCategoriaViewController ncVC;
	private static NovoLimiteViewController nlVC;
	private static NovoLembreteViewController nlembreteVC;
	private static LimitesViewController lgVC;
	private static LembretesViewController lVC;
	private static EditarLembreteViewController eLembreteVC;
	private static EditarLimiteViewController eLimiteVC;
	private static DadosAmortizacaoViewController daVC;
	private static DadosDescontoViewController ddVC;
	private static DadosVariacaoViewController dvVC;
	private static DadosInvestimentoViewController diVC;
	private static TutoriaisViewController tVC;
	private static InicialViewController iVC;
	private static DadosVPLPadraoViewController pvplVC;
	private static DadosTaxaInternaRetornoViewController tirVC;

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			FXMLLoader loaderInicial = new FXMLLoader(getClass().getResource("/iu/view/inicial.fxml"));
			Parent inicial = loaderInicial.load();
			cenaInicial = new Scene(inicial);
			iVC = loaderInicial.getController();

			Parent cambio = FXMLLoader.load(getClass().getResource("/iu/view/Câmbio.fxml"));
			cenaCambio = new Scene(cambio);

			FXMLLoader loaderGerenciarGastos = new FXMLLoader(getClass().getResource("/iu/view/GerenciarGastos.fxml"));
			Parent gerenciarGastos = loaderGerenciarGastos.load();
			cenaGerenciarGastos = new Scene(gerenciarGastos);
			ggVC = loaderGerenciarGastos.getController();

			FXMLLoader loaderResumosGastos = new FXMLLoader(getClass().getResource("/iu/view/ResumosGastos.fxml"));
			Parent resumosGastos = loaderResumosGastos.load();
			cenaResumosGastos = new Scene(resumosGastos);
			rgVC = loaderResumosGastos.getController();

			FXMLLoader loaderLimites = new FXMLLoader(getClass().getResource("/iu/view/Limites.fxml"));
			Parent limites = loaderLimites.load();
			cenaLimites = new Scene(limites);
			lgVC = loaderLimites.getController();

			FXMLLoader loaderLembretes = new FXMLLoader(getClass().getResource("/iu/view/Lembretes.fxml"));
			Parent lembretes = loaderLembretes.load();
			cenaLembretes = new Scene(lembretes);
			lVC = loaderLembretes.getController();

			FXMLLoader loaderMetas = new FXMLLoader(getClass().getResource("/iu/view/Metas.fxml"));
			Parent metas = loaderMetas.load();
			cenaMetas = new Scene(metas);
			mVC = loaderMetas.getController();

			FXMLLoader loaderGastos = new FXMLLoader(getClass().getResource("/iu/view/Gastos.fxml"));
			Parent gastos = loaderGastos.load();
			cenaGastos = new Scene(gastos);
			gVC = loaderGastos.getController();

			FXMLLoader loaderCategorias = new FXMLLoader(getClass().getResource("/iu/view/Categorias.fxml"));
			Parent categorias = loaderCategorias.load();
			cenaCategorias = new Scene(categorias);
			cVC = loaderCategorias.getController();

			FXMLLoader loaderNovoLembrete = new FXMLLoader(getClass().getResource("/iu/view/NovoLembrete.fxml"));
			Parent novoLembrete = loaderNovoLembrete.load();
			cenaNovoLembrete = new Scene(novoLembrete);
			nlembreteVC = loaderNovoLembrete.getController();

			FXMLLoader loaderNovoGasto = new FXMLLoader(getClass().getResource("/iu/view/NovoGasto.fxml"));
			Parent novoGasto = loaderNovoGasto.load();
			cenaNovoGasto = new Scene(novoGasto);
			ngVC = loaderNovoGasto.getController();

			FXMLLoader loaderNovoLimite = new FXMLLoader(getClass().getResource("/iu/view/NovoLimite.fxml"));
			Parent novoLimite = loaderNovoLimite.load();
			cenaNovoLimite = new Scene(novoLimite);
			nlVC = loaderNovoLimite.getController();

			FXMLLoader loaderNovaMeta = new FXMLLoader(getClass().getResource("/iu/view/NovaMeta.fxml"));
			Parent novaMeta = loaderNovaMeta.load();
			cenaNovaMeta = new Scene(novaMeta);
			nmVC = loaderNovaMeta.getController();

			FXMLLoader loaderNovaCategoria = new FXMLLoader(getClass().getResource("/iu/view/NovaCategoria.fxml"));
			Parent novaCategoria = loaderNovaCategoria.load();
			cenaNovaCategoria = new Scene(novaCategoria);
			ncVC = loaderNovaCategoria.getController();

			FXMLLoader loaderEditarLembrete = new FXMLLoader(getClass().getResource("/iu/view/EditarLembrete.fxml"));
			Parent editarLembrete = loaderEditarLembrete.load();
			cenaEditarLembrete = new Scene(editarLembrete);
			eLembreteVC = loaderEditarLembrete.getController();

			FXMLLoader loaderEditarGasto = new FXMLLoader(getClass().getResource("/iu/view/EditarGasto.fxml"));
			Parent editarGasto = loaderEditarGasto.load();
			cenaEditarGasto = new Scene(editarGasto);
			egVC = loaderEditarGasto.getController();

			FXMLLoader loaderEditarLimite = new FXMLLoader(getClass().getResource("/iu/view/EditarLimite.fxml"));
			Parent editarLimite = loaderEditarLimite.load();
			cenaEditarLimite = new Scene(editarLimite);
			eLimiteVC = loaderEditarLimite.getController();

			FXMLLoader loaderEditarMeta = new FXMLLoader(getClass().getResource("/iu/view/EditarMeta.fxml"));
			Parent editarMeta = loaderEditarMeta.load();
			cenaEditarMeta = new Scene(editarMeta);
			emVC = loaderEditarMeta.getController();

			FXMLLoader loaderEditarCategoria = new FXMLLoader(getClass().getResource("/iu/view/EditarCategoria.fxml"));
			Parent editarCategoria = loaderEditarCategoria.load();
			cenaEditarCategoria = new Scene(editarCategoria);
			ecVC = loaderEditarCategoria.getController();

			FXMLLoader loaderTutoriais = new FXMLLoader(getClass().getResource("/iu/view/Tutoriais.fxml"));
			Parent tutoriais = loaderTutoriais.load();
			cenaTutoriais = new Scene(tutoriais);
			tVC = loaderTutoriais.getController();

			Parent principalInvestimentos = FXMLLoader
					.load(getClass().getResource("/iu/view/PrincipalInvestimentos.fxml"));
			cenaPrincipalInvestimentos = new Scene(principalInvestimentos);

			Parent investimentos = FXMLLoader.load(getClass().getResource("/iu/view/Investimentos.fxml"));
			cenaInvestimentos = new Scene(investimentos);

			FXMLLoader loaderDadosInvestimento = new FXMLLoader(
					getClass().getResource("/iu/view/DadosInvestimento.fxml"));
			Parent dadosInvestimento = loaderDadosInvestimento.load();
			cenaDadosInvestimento = new Scene(dadosInvestimento);
			diVC = loaderDadosInvestimento.getController();

			Parent amortizacao = FXMLLoader.load(getClass().getResource("/iu/view/Amortizacao.fxml"));
			cenaAmortizacao = new Scene(amortizacao);

			FXMLLoader loaderDadosAmortizacao = new FXMLLoader(
					getClass().getResource("/iu/view/DadosAmortizacao.fxml"));
			Parent dadosAmortizacao = loaderDadosAmortizacao.load();
			cenaDadosAmortizacao = new Scene(dadosAmortizacao);
			daVC = loaderDadosAmortizacao.getController();

			Parent variacao = FXMLLoader.load(getClass().getResource("/iu/view/Variacao.fxml"));
			cenaVariacao = new Scene(variacao);

			FXMLLoader loaderDadosVariacao = new FXMLLoader(getClass().getResource("/iu/view/DadosVariacao.fxml"));
			Parent dadosVariacao = loaderDadosVariacao.load();
			cenaDadosVariacao = new Scene(dadosVariacao);
			dvVC = loaderDadosVariacao.getController();

			Parent valorPresenteLiquido = FXMLLoader.load(getClass().getResource("/iu/view/ValorPresenteLiquido.fxml"));
			cenaValorPresenteLiquido = new Scene(valorPresenteLiquido);

			FXMLLoader loaderTIR = new FXMLLoader(getClass().getResource("/iu/view/DadosTaxaInternaRetorno.fxml"));
			Parent dadosTaxaInternaRetorno = loaderTIR.load();
			cenaDadosTaxaInternaRetorno = new Scene(dadosTaxaInternaRetorno);
			tirVC = loaderTIR.getController();

			FXMLLoader loaderVPLPadrao = new FXMLLoader(getClass().getResource("/iu/view/DadosVPLPadrao.fxml"));
			Parent dadosVPLPadrao = loaderVPLPadrao.load();
			cenaDadosVPLPadrao = new Scene(dadosVPLPadrao);
			pvplVC = loaderVPLPadrao.getController();

			Parent descontoTitulo = FXMLLoader.load(getClass().getResource("/iu/view/DescontoTitulo.fxml"));
			cenaDescontoTitulo = new Scene(descontoTitulo);

			FXMLLoader loaderDadosDesconto = new FXMLLoader(getClass().getResource("/iu/view/DadosDesconto.fxml"));
			Parent dadosDesconto = loaderDadosDesconto.load();
			cenaDadosDesconto = new Scene(dadosDesconto);
			ddVC = loaderDadosDesconto.getController();

			primaryStage.setScene(cenaInicial);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String telaAtual;

	public static void mudarTela(String nome) {
		switch (nome) {
		case "inicial":
			iVC.inicializarTela();
			stage.setScene(cenaInicial);
			break;
		case "cambio":
			stage.setScene(cenaCambio);
			break;
		case "gerenciarGastos":
			stage.setScene(cenaGerenciarGastos);
			ggVC.criarGrafico();
			break;
		case "resumosGastos":
			stage.setScene(cenaResumosGastos);			
			rgVC.criarGraficos();
			rgVC.carregarValores();
			break;
		case "limitesGastos":
			lgVC.inicializaValores();
			stage.setScene(cenaLimites);
			break;
		case "lembretes":
			lVC.inicializaValores();
			stage.setScene(cenaLembretes);
			break;
		case "metas":
			mVC.inicializaValores();
			stage.setScene(cenaMetas);
			break;
		case "gastos":
			gVC.inicializaValores();
			stage.setScene(cenaGastos);
			break;
		case "categorias":
			cVC.inicializaValores();
			stage.setScene(cenaCategorias);
			break;
		case "novoLembrete":
			nlembreteVC.atualizandoTela();
			stage.setScene(cenaNovoLembrete);
			break;
		case "novoGasto":
			ngVC.atualizandoTela();
			stage.setScene(cenaNovoGasto);
			break;
		case "novoLimite":
			nlVC.atualizandoTela();
			stage.setScene(cenaNovoLimite);
			break;
		case "novaMeta":
			nmVC.atualizandoTela();
			stage.setScene(cenaNovaMeta);
			break;
		case "novaCategoria":
			ncVC.atualizandoTela(telaAtual);
			stage.setScene(cenaNovaCategoria);
			break;
		case "principalInvestimentos":
			stage.setScene(cenaPrincipalInvestimentos);
			break;
		case "investimento":
			stage.setScene(cenaInvestimentos);
			break;
		case "amortizacao":
			stage.setScene(cenaAmortizacao);
			break;
		case "variacao":
			stage.setScene(cenaVariacao);
			break;
		case "valorPresenteLiquido":
			stage.setScene(cenaValorPresenteLiquido);
			break;
		case "descontoTitulo":
			stage.setScene(cenaDescontoTitulo);
			break;
		default:
			System.out.println("Tela " + nome + " inexistente");
		}
		telaAtual = nome;
	}

	public static void mudarTelaEdicao(String nome, int v, String Gasto) {
		switch (nome) {
		case "editarLembrete":
			eLembreteVC.lembreteEscolhido(v);
			stage.setScene(cenaEditarLembrete);
			break;
		case "editarGasto":
			egVC.gastoEscolhido(v, Gasto);
			stage.setScene(cenaEditarGasto);
			break;
		case "editarLimite":
			eLimiteVC.limiteEscolhido(v);
			stage.setScene(cenaEditarLimite);
			break;
		case "editarMeta":
			emVC.metaEscolhida(v);
			stage.setScene(cenaEditarMeta);
			break;
		case "editarCategoria":
			ecVC.categoriaEscolhida(v);
			stage.setScene(cenaEditarCategoria);
			break;
		default:
			System.out.println("Tela " + nome + " inexistente");
		}
		telaAtual = nome;
	}

	public static void mudarTelaDadosInvestimentos(String nome, int input) {
		switch (nome) {
		case "dadosInvestimento":
			diVC.tipoInvestimento(input);
			stage.setScene(cenaDadosInvestimento);
			break;
		case "dadosAmortizacao":
			daVC.tipoAmortizacao(input);
			stage.setScene(cenaDadosAmortizacao);
			break;
		case "dadosVariacao":
			dvVC.tipoVariacao(input);
			stage.setScene(cenaDadosVariacao);
			break;
		case "dadosTaxaInternaRetorno":
			tirVC.inicializandoTela();
			stage.setScene(cenaDadosTaxaInternaRetorno);
			break;
		case "dadosVPLPadrao":
			pvplVC.inicializandoTela();
			stage.setScene(cenaDadosVPLPadrao);
			break;
		case "dadosDesconto":
			ddVC.tipoDesconto(input);
			stage.setScene(cenaDadosDesconto);
			break;
		case "tutoriais":
			tVC.atualizandoTela(telaAtual, input);
			stage.setScene(cenaTutoriais);
			break;
		default:
			System.out.println("Tela " + nome + " inexistente");
		}
		telaAtual = nome;
	}

	public static void main(String[] args) {
		launch(args);
	}
}