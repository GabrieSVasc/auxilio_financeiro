package iu.viewController;

import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Main;
import negocio.entidades.Categoria;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ValorNegativoException;

public class EditarCategoriaViewController {
	@FXML
	private Button btnVoltar;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Button btnConfirmar;
	
	private static Fachada fachada = new Fachada();
	
	private int idCategoria;
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("categorias");
	}
	
	public void categoriaEscolhida(int v) {
		idCategoria = v;
		Categoria c = fachada.getCategoria(v);
		txtNome.setText(c.getNome());
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.editarCategoria(idCategoria, txtNome.getText());
			Main.mudarTela("categorias");
		} catch (ValorNegativoException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("O valor não deve ser negativo");
			alerta.showAndWait();
		} catch (ObjetoNaoEncontradoException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Tentando editar categoria inexistente");
			alerta.showAndWait();
		} catch (CampoVazioException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Campo do nome da categoria está vazio");
			alerta.showAndWait();
		}
	}
}