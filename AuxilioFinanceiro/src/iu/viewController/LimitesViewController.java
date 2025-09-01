package iu.viewController;

import java.io.IOException;
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

public class LimitesViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private TableView<ValorLista> tblLimites;
	
	@FXML
	private TableColumn<ValorLista, String> limite;
	
	@FXML
	private TableColumn<ValorLista, Void> editar;
	
	@FXML
	private TableColumn<ValorLista, Void> remover;
	
	private ArrayList<ValorLista> limites;
	private static Fachada fachada = new Fachada();
	
	
	@FXML
	private Button btnNovoLimite;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		limites = fachada.inicializarLimites();
		
		ObservableList<ValorLista> dados = FXCollections.observableArrayList(limites);
		limite = new TableColumn<ValorLista, String>("Limites");
		limite.setPrefWidth(855);
		limite.setStyle("-fx-font-size: 20px");
		limite.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		editar = new TableColumn<ValorLista, Void>("Editar");
		editar.setPrefWidth(100);
		remover = new TableColumn<ValorLista, Void>("Remover");
		remover.setPrefWidth(100);
		
		tblLimites.getColumns().add(limite);
		tblLimites.getColumns().add(editar);
		tblLimites.getColumns().add(remover);
		
		tblLimites.setItems(dados);
	}
	
	public void inicializaValores() {
		limites = fachada.inicializarLimites();
		
		ObservableList<ValorLista> dados = FXCollections.observableArrayList(limites);
		limite.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		tblLimites.setItems(dados);
		
		editar.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Editar");
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event -> {
					ValorLista tl = getTableView().getItems().get(getIndex());
					Main.mudarTelaEdicao("editarLimite", tl.getId(), "");
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
						fachada.removerLimite(tl.getId());
						inicializaValores();
					} catch (ObjetoNaoEncontradoException e) {
						e.printStackTrace();
					} catch (ValorNegativoException e) {
						e.printStackTrace();
					} catch (IOException e) {
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
	protected void btnNovoLimiteAction(ActionEvent e) {
		Main.mudarTela("novoLimite");
	}
}