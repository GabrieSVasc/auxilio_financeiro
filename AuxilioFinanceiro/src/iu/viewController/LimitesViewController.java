package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import fachada.TransferindoListas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import main.Main;

public class LimitesViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private TableView<TransferindoListas> tblLimites;
	
	@FXML
	private TableColumn<TransferindoListas, String> limite;
	
	@FXML
	private TableColumn<TransferindoListas, Void> editar;
	
	@FXML
	private TableColumn<TransferindoListas, Void> remover;
	
	
	@FXML
	private Button btnNovoLimite;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gerenciarGastos");
	}
	
	@FXML
	protected void btnNovoLimiteAction(ActionEvent e) {
		Main.mudarTela("novoLimite");
	}
}