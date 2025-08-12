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

public class LembretesViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Pane pnListaLembretes;
	
	@FXML
	private Button btnNovoLembrete;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("inicial");
	}
	
	@FXML
	protected void btnNovoLembreteAction(ActionEvent e) {
		Main.mudarTela("novoLembrete");
	}

}