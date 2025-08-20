package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			Parent inicial = FXMLLoader.load(getClass().getResource("/iu/view/Inicial.fxml"));
			cenaInicial = new Scene(inicial);
			
			Parent cambio = FXMLLoader.load(getClass().getResource("/iu/view/Câmbio.fxml"));
			cenaCambio = new Scene(cambio);
			
			Parent gerenciarGastos = FXMLLoader.load(getClass().getResource("/iu/view/GerenciarGastos.fxml"));
			cenaGerenciarGastos = new Scene(gerenciarGastos);
			
			Parent resumosGastos = FXMLLoader.load(getClass().getResource("/iu/view/ResumosGastos.fxml"));
			cenaResumosGastos = new Scene(resumosGastos);
			
			Parent limites = FXMLLoader.load(getClass().getResource("/iu/view/Limites.fxml"));
			cenaLimites = new Scene(limites);
			
			Parent lembretes = FXMLLoader.load(getClass().getResource("/iu/view/Lembretes.fxml"));
			cenaLembretes = new Scene(lembretes);
			
			Parent metas = FXMLLoader.load(getClass().getResource("/iu/view/Metas.fxml"));
			cenaMetas = new Scene(metas);
			
			Parent gastos = FXMLLoader.load(getClass().getResource("/iu/view/Gastos.fxml"));
			cenaGastos = new Scene(gastos);

			Parent categorias = FXMLLoader.load(getClass().getResource("/iu/view/Categorias.fxml"));
			cenaCategorias = new Scene(categorias);
			
			Parent novoLembrete = FXMLLoader.load(getClass().getResource("/iu/view/NovoLembrete.fxml"));
			cenaNovoLembrete = new Scene(novoLembrete);
			
			Parent novoGasto = FXMLLoader.load(getClass().getResource("/iu/view/NovoGasto.fxml"));
			cenaNovoGasto = new Scene(novoGasto);

			Parent novoLimite = FXMLLoader.load(getClass().getResource("/iu/view/NovoLimite.fxml"));
			cenaNovoLimite = new Scene(novoLimite);
			
			Parent novaMeta = FXMLLoader.load(getClass().getResource("/iu/view/NovaMeta.fxml"));
			cenaNovaMeta = new Scene(novaMeta);
			
			Parent novaCategoria = FXMLLoader.load(getClass().getResource("/iu/view/NovaCategoria.fxml"));
			cenaNovaCategoria = new Scene(novaCategoria);
			
			Parent editarLembrete = FXMLLoader.load(getClass().getResource("/iu/view/EditarLembrete.fxml"));
			cenaEditarLembrete = new Scene(editarLembrete);
			
			Parent editarGasto = FXMLLoader.load(getClass().getResource("/iu/view/EditarGasto.fxml"));
			cenaEditarGasto = new Scene(editarGasto);
			
			Parent editarLimite = FXMLLoader.load(getClass().getResource("/iu/view/EditarLimite.fxml"));
			cenaEditarLimite= new Scene(editarLimite);
			
			Parent editarMeta = FXMLLoader.load(getClass().getResource("/iu/view/EditarMeta.fxml"));
			cenaEditarMeta= new Scene(editarMeta);
			
			Parent editarCategoria = FXMLLoader.load(getClass().getResource("/iu/view/EditarCategoria.fxml"));
			cenaEditarCategoria= new Scene(editarCategoria);
			
			Parent tutoriais = FXMLLoader.load(getClass().getResource("/iu/view/Tutoriais.fxml"));
			cenaTutoriais = new Scene(tutoriais);
			
			Parent principalInvestimentos = FXMLLoader.load(getClass().getResource("/iu/view/PrincipalInvestimentos.fxml"));
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
			
			Parent dadosTaxaInternaRetorno = FXMLLoader.load(getClass().getResource("/iu/view/DadosTaxaInternaRetorno.fxml"));
			cenaDadosTaxaInternaRetorno = new Scene(dadosTaxaInternaRetorno);
			
			Parent dadosVPLPadrao = FXMLLoader.load(getClass().getResource("/iu/view/DadosVPLPadrao.fxml"));
			cenaDadosVPLPadrao = new Scene(dadosVPLPadrao);
			
			Parent descontoTitulo = FXMLLoader.load(getClass().getResource("/iu/view/DescontoTitulo.fxml"));
			cenaDescontoTitulo = new Scene(descontoTitulo);
			
			Parent dadosDesconto = FXMLLoader.load(getClass().getResource("/iu/view/DadosDesconto.fxml"));
			cenaDadosDesconto = new Scene(dadosDesconto);
			
			primaryStage.setScene(cenaInicial);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void mudarTela(String nome) {
		switch(nome) {
			case "inicial":
				stage.setScene(cenaInicial);
				break;
			case "cambio":
				stage.setScene(cenaCambio);
				break;
			case "gerenciarGastos":
				stage.setScene(cenaGerenciarGastos);
				break;
			case "resumosGastos":
				stage.setScene(cenaResumosGastos);
				break;
			case "limitesGastos":
				stage.setScene(cenaLimites);
				break;
			case "lembretes":
				stage.setScene(cenaLembretes);
				break;
			case "metas":
				stage.setScene(cenaMetas);
				break;
			case "gastos":
				stage.setScene(cenaGastos);
				break;
			case "categorias":
				stage.setScene(cenaCategorias);
				break;
			case "novoLembrete":
				stage.setScene(cenaNovoLembrete);
				break;
			case "novoGasto":
				stage.setScene(cenaNovoGasto);
				break;
			case "novoLimite":
				stage.setScene(cenaNovoLimite);
				break;
			case "novaMeta":
				stage.setScene(cenaNovaMeta);
				break;
			case "novaCategoria":
				stage.setScene(cenaNovaCategoria);
				break;
			case "editarLembrete":
				stage.setScene(cenaEditarLembrete);
				break;
			case "editarGasto":
				stage.setScene(cenaEditarGasto);
				break;
			case "editarLimite":
				stage.setScene(cenaEditarLimite);
				break;
			case "editarMeta":
				stage.setScene(cenaEditarMeta);
				break;
			case "editarCategoria":
				stage.setScene(cenaEditarCategoria);
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
				System.out.println("Tela "+nome+" inexistente");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
