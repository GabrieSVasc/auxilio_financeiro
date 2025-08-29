package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.Main;

public class PrincipalInvestimentosViewController implements Initializable	{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private ImageView imgTutoriais;

	@FXML
	private Button btnTutoriais;
	
	@FXML
	private Pane pnListaMetas;
	
	@FXML
	private Button btnInvestimento;
	
	@FXML
	private Button btnAmortizacao;
	
	@FXML
	private Button btnVariacao;
	
	@FXML
	private Button btnDesconto;
 	
	@FXML
	private Button btnVPL;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		btnTutoriais.setGraphic(imgTutoriais);
	}
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("inicial");
	}
	
	@FXML
	protected void btnInvestimentoAction(ActionEvent e) {
		Main.mudarTela("investimento");
	}
	
	@FXML
	protected void btnAmortizacaoAction(ActionEvent e) {
		Main.mudarTela("amortizacao");
	}
	
	@FXML
	protected void btnVariacaoAction(ActionEvent e) {
		Main.mudarTela("variacao");
	}
	
	@FXML
	protected void btnDescontoAction(ActionEvent e) {
		Main.mudarTela("descontoTitulo");
	}
	
	@FXML
	protected void btnVPLAction(ActionEvent e) {
		Main.mudarTela("valorPresenteLiquido");
	}
	
	@FXML
	protected void btnTutoriaisAction(ActionEvent e) {
		Main.mudarTela("tutoriais");
	}
}