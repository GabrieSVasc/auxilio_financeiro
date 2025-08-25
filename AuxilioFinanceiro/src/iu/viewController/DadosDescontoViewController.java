package iu.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;

public class DadosDescontoViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Label lblTipoDesconto;
	
	private int tipoDesconto;
	private String tipoDescontoStr;
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("descontoTitulo");
	}
	
	public void tipoDesconto(int tipo) {
		tipoDesconto = tipo;
		if(tipo == 1) {
			tipoDescontoStr = "Desconto Simples";
		}else if(tipo == 2) {
			tipoDescontoStr = "Desconto Composto";
		}
		lblTipoDesconto.setText(tipoDescontoStr);
	}
}