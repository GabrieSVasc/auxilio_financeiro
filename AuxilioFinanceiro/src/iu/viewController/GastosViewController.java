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

public class GastosViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Pane pnListaGastos;
	
	@FXML
	private Button btnNovoGasto;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
	}
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gerenciarGastos");
	}
	@FXML
	protected void btnNovoGastoAction(ActionEvent e) {
		Main.mudarTela("novoGasto");
	}
}