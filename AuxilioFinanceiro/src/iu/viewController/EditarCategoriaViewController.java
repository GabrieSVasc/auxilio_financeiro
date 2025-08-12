package iu.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Main;

public class EditarCategoriaViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("categorias");
	}
}