package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.Main;

public class ResumosGastosViewController implements Initializable{
	
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
	}
	
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gerenciarGastos");
	}
}
