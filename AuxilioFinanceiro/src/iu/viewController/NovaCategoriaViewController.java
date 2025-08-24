package iu.viewController;

import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Main;

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
		fachada.criarCategoria(txtNome.getText());
		Main.mudarTela("categorias");
	}
}