package main;

import iu.viewController.CategoriasViewController;
import iu.viewController.EditarGastoViewController;
import iu.viewController.EditarMetaViewController;
import iu.viewController.GastosViewController;
import iu.viewController.GerenciarGastosViewController;
import iu.viewController.MetasViewController;
import iu.viewController.NovaMetaViewController;
import iu.viewController.NovoGastoViewController;
import iu.viewController.ResumosGastosViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			Parent inicial = FXMLLoader.load(getClass().getResource("/iu/view/Inicial.fxml"));
			cenaInicial = new Scene(inicial);

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

			Parent limites = FXMLLoader.load(getClass().getResource("/iu/view/Limites.fxml"));
			cenaLimites = new Scene(limites);

			Parent lembretes = FXMLLoader.load(getClass().getResource("/iu/view/Lembretes.fxml"));
			cenaLembretes = new Scene(lembretes);

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

			Parent novoLembrete = FXMLLoader.load(getClass().getResource("/iu/view/NovoLembrete.fxml"));
			cenaNovoLembrete = new Scene(novoLembrete);

			FXMLLoader loaderNovoGasto = new FXMLLoader(getClass().getResource("/iu/view/NovoGasto.fxml"));
			Parent novoGasto = loaderNovoGasto.load();
			cenaNovoGasto = new Scene(novoGasto);
			ngVC = loaderNovoGasto.getController();

			Parent novoLimite = FXMLLoader.load(getClass().getResource("/iu/view/NovoLimite.fxml"));
			cenaNovoLimite = new Scene(novoLimite);
			
			FXMLLoader loaderNovaMeta = new FXMLLoader(getClass().getResource("/iu/view/NovaMeta.fxml"));
			Parent novaMeta = loaderNovaMeta.load();
			cenaNovaMeta = new Scene(novaMeta);
			nmVC = loaderNovaMeta.getController();

			Parent novaCategoria = FXMLLoader.load(getClass().getResource("/iu/view/NovaCategoria.fxml"));
			cenaNovaCategoria = new Scene(novaCategoria);

			Parent editarLembrete = FXMLLoader.load(getClass().getResource("/iu/view/EditarLembrete.fxml"));
			cenaEditarLembrete = new Scene(editarLembrete);
			
			FXMLLoader loaderEditarGasto = new FXMLLoader(getClass().getResource("/iu/view/EditarGasto.fxml"));
			Parent editarGasto = loaderEditarGasto.load();
			cenaEditarGasto = new Scene(editarGasto);
			egVC = loaderEditarGasto.getController();

			Parent editarLimite = FXMLLoader.load(getClass().getResource("/iu/view/EditarLimite.fxml"));
			cenaEditarLimite = new Scene(editarLimite);

			FXMLLoader loaderEditarMeta = new FXMLLoader(getClass().getResource("/iu/view/EditarMeta.fxml"));
			Parent editarMeta = loaderEditarMeta.load();
			cenaEditarMeta = new Scene(editarMeta);
			emVC = loaderEditarMeta.getController();

			Parent editarCategoria = FXMLLoader.load(getClass().getResource("/iu/view/EditarCategoria.fxml"));
			cenaEditarCategoria = new Scene(editarCategoria);

			Parent tutoriais = FXMLLoader.load(getClass().getResource("/iu/view/Tutoriais.fxml"));
			cenaTutoriais = new Scene(tutoriais);

			Parent principalInvestimentos = FXMLLoader
					.load(getClass().getResource("/iu/view/PrincipalInvestimentos.fxml"));
			cenaPrincipalInvestimentos = new Scene(principalInvestimentos);

			Parent investimentos = FXMLLoader.load(getClass().getResource("/iu/view/Investimentos.fxml"));
			cenaInvestimentos = new Scene(investimentos);

			Parent dadosInvestimento = FXMLLoader.load(getClass().getResource("/iu/view/DadosInvestimento.fxml"));
			cenaDadosInvestimento = new Scene(dadosInvestimento);

			Parent amortizacao = FXMLLoader.load(getClass().getResource("/iu/view/Amortizacao.fxml"));
			cenaAmortizacao = new Scene(amortizacao);

			Parent dadosAmortizacao = FXMLLoader.load(getClass().getResource("/iu/view/DadosAmortizacao.fxml"));
			cenaDadosAmortizacao = new Scene(dadosAmortizacao);

			Parent variacao = FXMLLoader.load(getClass().getResource("/iu/view/Variacao.fxml"));
			cenaVariacao = new Scene(variacao);

			Parent dadosVariacao = FXMLLoader.load(getClass().getResource("/iu/view/DadosVariacao.fxml"));
			cenaDadosVariacao = new Scene(dadosVariacao);

			Parent valorPresenteLiquido = FXMLLoader.load(getClass().getResource("/iu/view/ValorPresenteLiquido.fxml"));
			cenaValorPresenteLiquido = new Scene(valorPresenteLiquido);

			Parent dadosTaxaInternaRetorno = FXMLLoader
					.load(getClass().getResource("/iu/view/DadosTaxaInternaRetorno.fxml"));
			cenaDadosTaxaInternaRetorno = new Scene(dadosTaxaInternaRetorno);

			Parent dadosVPLPadrao = FXMLLoader.load(getClass().getResource("/iu/view/DadosVPLPadrao.fxml"));
			cenaDadosVPLPadrao = new Scene(dadosVPLPadrao);

			Parent descontoTitulo = FXMLLoader.load(getClass().getResource("/iu/view/DescontoTitulo.fxml"));
			cenaDescontoTitulo = new Scene(descontoTitulo);

			Parent dadosDesconto = FXMLLoader.load(getClass().getResource("/iu/view/DadosDesconto.fxml"));
			cenaDadosDesconto = new Scene(dadosDesconto);

			primaryStage.setScene(cenaInicial);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mudarTela(String nome) {
		switch (nome) {
		case "inicial":
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
			stage.setScene(cenaLimites);
			break;
		case "lembretes":
			stage.setScene(cenaLembretes);
			break;
		case "metas":
			stage.setScene(cenaMetas);
			mVC.inicializaValores();
			break;
		case "gastos":
			stage.setScene(cenaGastos);
			gVC.inicializaValores();
			break;
		case "categorias":
			stage.setScene(cenaCategorias);
			cVC.inicializaValores();
			break;
		case "novoLembrete":
			stage.setScene(cenaNovoLembrete);
			break;
		case "novoGasto":
			stage.setScene(cenaNovoGasto);
			ngVC.atualizandoTela();
			break;
		case "novoLimite":
			stage.setScene(cenaNovoLimite);
			break;
		case "novaMeta":
			stage.setScene(cenaNovaMeta);
			nmVC.atualizandoTela();
			break;
		case "novaCategoria":
			stage.setScene(cenaNovaCategoria);
			break;
		case "tutoriais":
			stage.setScene(cenaTutoriais);
			break;
		case "principalInvestimentos":
			stage.setScene(cenaPrincipalInvestimentos);
			break;
		case "investimento":
			stage.setScene(cenaInvestimentos);
			break;
		case "dadosInvestimento":
			stage.setScene(cenaDadosInvestimento);
			break;
		case "amortizacao":
			stage.setScene(cenaAmortizacao);
			break;
		case "dadosAmortizacao":
			stage.setScene(cenaDadosAmortizacao);
			break;
		case "variacao":
			stage.setScene(cenaVariacao);
			break;
		case "dadosVariacao":
			stage.setScene(cenaDadosVariacao);
			break;
		case "valorPresenteLiquido":
			stage.setScene(cenaValorPresenteLiquido);
			break;
		case "dadosTaxaInternaRetorno":
			stage.setScene(cenaDadosTaxaInternaRetorno);
			break;
		case "dadosVPLPadrao":
			stage.setScene(cenaDadosVPLPadrao);
			break;
		case "descontoTitulo":
			stage.setScene(cenaDescontoTitulo);
			break;
		case "dadosDesconto":
			stage.setScene(cenaDadosDesconto);
			break;
		default:
			System.out.println("Tela " + nome + " inexistente");
		}
	}

	public static void mudarTelaEdicao(String nome, int v) {
		switch (nome) {
		case "editarLembrete":
			stage.setScene(cenaEditarLembrete);
			break;
		case "editarGasto":
			stage.setScene(cenaEditarGasto);
			egVC.gastoEscolhido(v);
			break;
		case "editarLimite":
			stage.setScene(cenaEditarLimite);
			break;
		case "editarMeta":
			stage.setScene(cenaEditarMeta);
			emVC.metaEscolhida(v);
			break;
		case "editarCategoria":
			stage.setScene(cenaEditarCategoria);
			break;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}