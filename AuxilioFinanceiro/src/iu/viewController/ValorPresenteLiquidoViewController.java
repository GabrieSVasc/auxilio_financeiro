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
 * Classe ligada ao fxml da tela de HUB do VPL
 * 
 * @author Maria Gabriela
 */

public class ValorPresenteLiquidoViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnPadrao;
	
	@FXML
	private Button btnTaxaInternaRetorno;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("principalInvestimentos");
	}
	
	@FXML
	protected void btnPadraoAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("dadosVPLPadrao", 0);
	}
	
	@FXML
	protected void btnTaxaInternaRetornoAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("dadosTaxaInternaRetorno", 0);
	}
}