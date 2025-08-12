package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.Main;

public class EditarGastoViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnNovaCategoria;
	
	@FXML
	private ImageView imgMais;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnNovaCategoria.setGraphic(imgMais);
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gastos");
	}
	
	@FXML
	protected void btnNovaCategoriaAction(ActionEvent e) {
		Main.mudarTela("novaCategoria");
	}
}