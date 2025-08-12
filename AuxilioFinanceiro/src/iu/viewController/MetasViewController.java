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

public class MetasViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Pane pnListaMetas;
	
	@FXML
	private Button btnNovaMeta;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
	}
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gerenciarGastos");
	}
	@FXML
	protected void btnNovaMetaAction(ActionEvent e) {
		Main.mudarTela("novaMeta");
	}
}