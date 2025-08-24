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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.Main;

public class CategoriasViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnNovaCategoria;
	
	@FXML
	private TableView<String> tblCategorias;

	@FXML
	private TableColumn<String, String> categoria;

	@FXML
	private TableColumn<String, Void> editar;

	@FXML
	private TableColumn<String, Void> remover;

	private ArrayList<String> categorias;
	private Fachada fachada;

	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		fachada = new Fachada();
		categorias = fachada.inicializarCategorias();
		categoria = new TableColumn<String, String>("Categorias");
		categoria.setPrefWidth(855);
		categoria.setStyle("-fx-font-size: 20px");
		ObservableList<String> dados = FXCollections.observableArrayList(categorias);
		categoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
		editar = new TableColumn<String, Void>("Editar");
		editar.setPrefWidth(100);
		remover = new TableColumn<String, Void>("Remover");
		remover.setPrefWidth(100);
		tblCategorias.getColumns().add(categoria);
		tblCategorias.getColumns().add(editar);
		tblCategorias.getColumns().add(remover);
		tblCategorias.setItems(dados);
	}
	
	public void inicializaValores() {
		categorias = fachada.inicializarCategorias();
		
		ObservableList<String> dados = FXCollections.observableArrayList(categorias);
		categoria.setCellValueFactory(data-> new SimpleStringProperty(data.getValue()));
		tblCategorias.setItems(dados);
		
		editar.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Editar");
			private final HBox container = new HBox(btn);
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event -> {
				});
			}
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if(empty) {
					setGraphic(null);
				}else {
					setGraphic(btn);
				}
			}
		});
		
		remover.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Remover");
			private final HBox container = new HBox(btn);
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event -> {
				});
			}
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if(empty) {
					setGraphic(null);
				}else {
					setGraphic(btn);
				}
			}
		});
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
