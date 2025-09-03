package iu.viewController;

import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import main.Main;
import negocio.exceptions.ObjetoNuloException;

public class NovoLembreteViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	private TextField txtTitulo;
	
	@FXML
	private TextField txtDescricao;
	
	@FXML
	private DatePicker dtpData;
	
	@FXML
	private Button btnConfirmar;
	
	private static Fachada fachada = new Fachada();
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("lembretes");
	}
	public void atualizandoTela() {
		txtTitulo.setText("");
		txtDescricao.setText("");
		dtpData.setValue(null);
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.criarLembrete(txtTitulo.getText(), txtDescricao.getText(), dtpData.getValue());
			Main.mudarTela("lembretes");
		} catch (ObjetoNuloException e1) {
			e1.printStackTrace();
		}
	}
}