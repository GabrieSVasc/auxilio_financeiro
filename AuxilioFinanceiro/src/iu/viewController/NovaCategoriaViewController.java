package iu.viewController;

import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import negocio.exceptions.CampoVazioException;

public class NovaCategoriaViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Button btnConfirmar;
	
	private static Fachada fachada = new Fachada();
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("categorias");
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.criarCategoria(txtNome.getText());
			Main.mudarTela("categorias");
		} catch (CampoVazioException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Campo do nome da categoria está vazio");
			alerta.showAndWait();
		}
	}
	
	public void atualizandoTela() {
		txtNome.setText("");
	}
}