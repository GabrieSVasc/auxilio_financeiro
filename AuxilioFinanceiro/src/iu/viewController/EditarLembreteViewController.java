package iu.viewController;

import java.io.IOException;

import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import main.Main;
import negocio.entidades.Lembrete;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;

public class EditarLembreteViewController {
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
	
	private int idLembrete;
	
	private static Fachada fachada = new Fachada();
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("lembretes");
	}
	
	public void lembreteEscolhido(int v) {
		idLembrete = v;
		Lembrete l = fachada.getLembrete(v);
		txtTitulo.setText(l.getTitulo());
		txtDescricao.setText(l.getDescricao());
		dtpData.setValue(l.getDataAlerta());
	}
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.atualizarLembrete(idLembrete, txtTitulo.getText(), txtDescricao.getText(), dtpData.getValue());
			Main.mudarTela("lembretes");
		} catch (ObjetoNaoEncontradoException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Tentando editar lembrete inexistente");
			alerta.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
			//Problema ao tentar ler um arquivo
		} catch (CampoVazioException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Todos os campos devem estar preenchidos");
			alerta.showAndWait();
		}
	}
}