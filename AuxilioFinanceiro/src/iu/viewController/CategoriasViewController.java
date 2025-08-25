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
import negocio.entidades.ValorLista;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ValorNegativoException;

public class CategoriasViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnNovaCategoria;
	
	@FXML
	private TableView<ValorLista> tblCategorias;

	@FXML
	private TableColumn<ValorLista, String> categoria;

	@FXML
	private TableColumn<ValorLista, Void> editar;

	@FXML
	private TableColumn<ValorLista, Void> remover;

	private ArrayList<ValorLista> categorias;
	private Fachada fachada;

	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		fachada = new Fachada();
		categorias = fachada.inicializarCategorias();
		categoria = new TableColumn<ValorLista, String>("Categorias");
		categoria.setPrefWidth(855);
		categoria.setStyle("-fx-font-size: 20px");
		ObservableList<ValorLista> dados = FXCollections.observableArrayList(categorias);
		categoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		editar = new TableColumn<ValorLista, Void>("Editar");
		editar.setPrefWidth(100);
		remover = new TableColumn<ValorLista, Void>("Remover");
		remover.setPrefWidth(100);
		tblCategorias.getColumns().add(categoria);
		tblCategorias.getColumns().add(editar);
		tblCategorias.getColumns().add(remover);
		tblCategorias.setItems(dados);
	}
	
	public void inicializaValores() {
		categorias = fachada.inicializarCategorias();
		
		ObservableList<ValorLista> dados = FXCollections.observableArrayList(categorias);
		categoria.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getStringLista()));
		tblCategorias.setItems(dados);
		
		editar.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Editar");
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event -> {
					ValorLista tl = getTableView().getItems().get(getIndex());
					Main.mudarTelaEdicao("editarCategoria", tl.getId());
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
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event -> {
					ValorLista tl = getTableView().getItems().get(getIndex());
					try {
						fachada.removerCategoria(tl.getId());
						inicializaValores();
					} catch (ValorNegativoException | ObjetoNaoEncontradoException e) {
						e.printStackTrace();
					}
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
