package iu.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;

public class InicialViewController {
	@FXML
	private Button btnCambio;
	@FXML
	private Label lblTitle;
	@FXML
	private Button btnGastos;
	@FXML
	private Button btnLembretes;
	@FXML
	private Button btnCalcularInvestimentos;
	@FXML
	private Button btnExLembrete1;
	@FXML
	private Button btnExLembrete2;
	
	@FXML
	protected void btnCambioAction(ActionEvent e) {
		Main.mudarTela("cambio");
	}
	
	@FXML
	protected void btnGastosAction(ActionEvent e) {
		Main.mudarTela("gerenciarGastos");
	}
	
	@FXML
	protected void btnLembretesAction(ActionEvent e) {
		Main.mudarTela("lembretes");
	}
	
	@FXML
	protected void btnCalcularInvestimentosAction(ActionEvent e) {
		Main.mudarTela("principalInvestimentos");
	}
	
}