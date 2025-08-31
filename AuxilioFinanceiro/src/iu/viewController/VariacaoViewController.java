package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.Main;

public class VariacaoViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private ImageView imgTutoriais;

	@FXML
	private Button btnTutoriais;
	
	@FXML
	private Button btnInflacao;
	
	@FXML
	private Button btnDeflacao;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		btnTutoriais.setGraphic(imgTutoriais);
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("principalInvestimentos");
	}
	
	@FXML
	protected void btnTutoriaisAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("tutoriais", 0);
	}
	
	@FXML
	protected void btnInflacaoAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("dadosVariacao", 1);
	}
	
	@FXML
	protected void btnDeflacaoAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("dadosVariacao", 2);
	}
}