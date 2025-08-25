package iu.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;

public class DadosVariacaoViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Label lblTipoVariacao;
	
	private int tipoVariacao;
	private String tipoVariacaoStr;
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("variacao");
	}
	
	public void tipoVariacao(int tipo) {
		tipoVariacao = tipo;
		if(tipo == 1) {
			tipoVariacaoStr = "Inflação";
		}else if(tipo == 2) {
			tipoVariacaoStr = "Deflação";
		}
		lblTipoVariacao.setText(tipoVariacaoStr);
	}
}