package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.Main;

/**
 * Classe ligada ao fxml do HUB de amortização
 * 
 * @author Maria Gabriela
 */

public class AmortizacaoViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private ImageView imgTutoriais;

	@FXML
	private Button btnTutoriais;
	
	@FXML
	private Button btnAmortPrice;
	
	@FXML
	private Button btnAmortMista;
	
	@FXML
	private Button btnAmortConstante;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		btnTutoriais.setGraphic(imgTutoriais);
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("principalInvestimentos");
	}
	
	@FXML
	protected void btnTutoriaisAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("tutoriais", 0);
	}
	
	@FXML
	protected void btnAmortPriceAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("dadosAmortizacao", 1);
	}
	
	@FXML
	protected void btnAmortConstanteAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("dadosAmortizacao", 2);
	}
	
	@FXML
	protected void btnAmortMistaAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("dadosAmortizacao", 3);
	}
}