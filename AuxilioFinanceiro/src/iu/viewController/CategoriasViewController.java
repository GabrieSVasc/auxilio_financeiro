package iu.viewController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fachada.Fachada;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import main.Main;

public class CategoriasViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnNovaCategoria;
	
	@FXML
	private TableView tblCategorias;

	@FXML
	private TableColumn<String, String> categoria;

	@FXML
	private TableColumn editar;

	@FXML
	private TableColumn remover;

	private ArrayList<String> categorias;
	private Fachada fachada;

	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		fachada = new Fachada();
		categorias = fachada.inicializarCategorias();
		categoria = new TableColumn();
		categoria.setPrefWidth(872);
		categoria.setStyle("-fx-font-size: 20px");
		ObservableList<String> dados = FXCollections.observableArrayList(categorias);
		categoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
		tblCategorias.getColumns().add(categoria);
		tblCategorias.setItems(dados);
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gerenciarGastos");
	}
	
	@FXML
	protected void btnNovaCategoriaAction(ActionEvent e) {
		Main.mudarTela("novaCategoria");
	}
}
