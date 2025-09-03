package iu.viewController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;
import negocio.entidades.Lembrete;

public class InicialViewController implements Initializable {
	@FXML
	private Button btnCambio;
	@FXML
	private Label lblTitle;
	@FXML
	private Button btnGastos;
	@FXML
	private Button btnLembretes;
	@FXML
	private Button btnCalcularInvestimentos;
	@FXML
	private Button btnExLembrete1;
	@FXML
	private Button btnExLembrete2;
	@FXML
	private Button btnExLembrete3;
	@FXML
	private Button btnExLembrete4;
	@FXML
	private Button btnExLembrete5;

	private static Fachada fachada = new Fachada();

	@FXML
	protected void btnCambioAction(ActionEvent e) {
		Main.mudarTela("cambio");
	}

	@FXML
	protected void btnGastosAction(ActionEvent e) {
		Main.mudarTela("gerenciarGastos");
	}

	@FXML
	protected void btnLembretesAction(ActionEvent e) {
		Main.mudarTela("lembretes");
	}

	@FXML
	protected void btnCalcularInvestimentosAction(ActionEvent e) {
		Main.mudarTela("principalInvestimentos");
	}

	public void inicializarTela() {
		ArrayList<Lembrete> l = fachada.lembretesNotificados();
		switch (l.size()) {
		case 0:
			btnExLembrete1.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete2.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete3.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete4.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete5.setText("Nenhum lembrete para ser mostrado");
			break;
		case 1:
			btnExLembrete1.setText(l.get(0).gerarNotificacao());
			btnExLembrete2.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete3.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete4.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete5.setText("Nenhum lembrete para ser mostrado");
			break;
		case 2:
			btnExLembrete1.setText(l.get(0).gerarNotificacao());
			btnExLembrete2.setText(l.get(1).gerarNotificacao());
			btnExLembrete3.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete4.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete5.setText("Nenhum lembrete para ser mostrado");
			break;
		case 3:
			btnExLembrete1.setText(l.get(0).gerarNotificacao());
			btnExLembrete2.setText(l.get(1).gerarNotificacao());
			btnExLembrete3.setText(l.get(2).gerarNotificacao());
			btnExLembrete4.setText("Nenhum lembrete para ser mostrado");
			btnExLembrete5.setText("Nenhum lembrete para ser mostrado");
			break;
		case 4:
			btnExLembrete1.setText(l.get(0).gerarNotificacao());
			btnExLembrete2.setText(l.get(1).gerarNotificacao());
			btnExLembrete3.setText(l.get(2).gerarNotificacao());
			btnExLembrete4.setText(l.get(3).gerarNotificacao());
			btnExLembrete5.setText("Nenhum lembrete para ser mostrado");
			break;
		default:
			btnExLembrete1.setText(l.get(0).gerarNotificacao());
			btnExLembrete2.setText(l.get(1).gerarNotificacao());
			btnExLembrete3.setText(l.get(2).gerarNotificacao());
			btnExLembrete4.setText(l.get(3).gerarNotificacao());
			btnExLembrete5.setText(l.get(4).gerarNotificacao());
			break;

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializarTela();
	}
}