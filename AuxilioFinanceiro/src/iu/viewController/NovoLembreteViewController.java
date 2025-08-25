package iu.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Main;

public class NovoLembreteViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("lembretes");
	}
	public void atualizandoTela() {
		//TODO ajustar o que esse metodo faz
	}
}