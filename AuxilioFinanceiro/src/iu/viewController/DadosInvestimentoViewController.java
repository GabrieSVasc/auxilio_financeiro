package iu.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;

public class DadosInvestimentoViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Label lblTipoInvestimento;
	
	private int tipoInvestimento;
	private String tipoInvestimentoStr;
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("investimento");
	}
	
	public void tipoInvestimento(int tipo) {
		tipoInvestimento = tipo;
		switch(tipo) {
		case 1:
			tipoInvestimentoStr = "Juros Simples";
			break;
		case 2:
			tipoInvestimentoStr = "Juros Compostos";
			break;
		case 3:
			tipoInvestimentoStr = "Aportes Periodicos";
			break;
		}
		lblTipoInvestimento.setText(tipoInvestimentoStr);
		
	}
}