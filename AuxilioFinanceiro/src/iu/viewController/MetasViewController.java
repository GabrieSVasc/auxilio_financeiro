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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

public class MetasViewController implements Initializable{
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnNovaMeta;
	
	@FXML
	private TableView<ValorLista> tblMetas;

	@FXML
	private TableColumn<ValorLista, String> meta;

	@FXML
	private TableColumn<ValorLista, Void> editar;

	@FXML
	private TableColumn<ValorLista, Void> remover;

	private ArrayList<ValorLista> metas;
	private static Fachada fachada = new Fachada();
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		metas = new ArrayList<ValorLista>();
		metas = fachada.inicializarMetas();

		meta = new TableColumn<ValorLista, String>("Metas");
		ObservableList<ValorLista> dados = FXCollections.observableArrayList(metas);
		meta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		meta.setPrefWidth(855);
		meta.setStyle("-fx-font-size: 20px");
		editar = new TableColumn<ValorLista, Void>("Editar");
		editar.setPrefWidth(100);
		remover = new TableColumn<ValorLista, Void>("Remover");
		remover.setPrefWidth(100);
		tblMetas.getColumns().add(meta);
		tblMetas.getColumns().add(editar);
		tblMetas.getColumns().add(remover);
		tblMetas.setItems(dados);
	}
	
	public void inicializaValores() {
		metas = fachada.inicializarMetas();

		ObservableList<ValorLista> dados = FXCollections.observableArrayList(metas);
		meta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		tblMetas.setItems(dados);
		
		editar.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Editar");
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event ->{
					ValorLista tl = getTableView().getItems().get(getIndex());
					Main.mudarTelaEdicao("editarMeta", tl.getId(), "");
					
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
				btn.setOnAction(event ->{
					ValorLista tl = getTableView().getItems().get(getIndex());
					try {
						fachada.removerMeta(tl.getId());
					} catch (ObjetoNaoEncontradoException e) {
						Alert alerta = new Alert(AlertType.ERROR);
						alerta.setTitle("Erro");
						alerta.setContentText("Tentando remover uma meta que não está cadastrada");
						alerta.showAndWait();
					} catch (IOException e) {
						//Problemas ao manipular um arquivo
						e.printStackTrace();
					}
					inicializaValores();
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
	protected void btnNovaMetaAction(ActionEvent e) {
		Main.mudarTela("novaMeta");
	}
}