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
import negocio.exceptions.ObjetoJaExisteException;

public class NovaCategoriaViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Button btnConfirmar;
	
	private static Fachada fachada = new Fachada();
	private String anterior;
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela(anterior);
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.criarCategoria(txtNome.getText());
			Main.mudarTela(anterior);
		} catch (CampoVazioException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Campo do nome da categoria está vazio");
			alerta.showAndWait();
		} catch (ObjetoJaExisteException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERRO");
			alerta.setContentText("Já existe uma categoria com o nome "+txtNome.getText());
			alerta.showAndWait();
		}
	}
	
	public void atualizandoTela(String telaAnterior) {
		anterior = telaAnterior;
		txtNome.setText("");
	}
}