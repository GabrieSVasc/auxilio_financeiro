package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.Main;

public class GerenciarGastosViewController implements Initializable{
	
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnResumosGastos;
	
	@FXML
	private Button btnLimites;
	
	@FXML
	private Button btnMetas;
	
	@FXML
	private Button btnGastos;
	
	@FXML
	private Button btnCategorias;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
	}
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("inicial");
	}
	
	@FXML
	protected void btnResumosGastosAction(ActionEvent e) {
		Main.mudarTela("resumosGastos");
	}
	
	@FXML
	protected void btnLimitesAction(ActionEvent e) {
		Main.mudarTela("limitesGastos");
	}
	
	@FXML
	protected void btnMetasAction(ActionEvent e) {
		Main.mudarTela("metas");
	}
	
	@FXML
	protected void btnGastosAction(ActionEvent e) {
		Main.mudarTela("gastos");
	}
	
	@FXML
	protected void btnCategoriasAction(ActionEvent e) {
		Main.mudarTela("categorias");
	}
}