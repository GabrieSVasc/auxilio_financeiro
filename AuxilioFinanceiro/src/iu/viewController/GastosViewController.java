package iu.viewController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fachada.Fachada;
import fachada.TransferindoListas;
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
import negocio.exceptions.CampoVazioException;

public class GastosViewController implements Initializable {
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;

	@FXML
	private Button btnNovoGasto;

	@FXML
	private TableView<TransferindoListas> tblGastos;

	@FXML
	private TableColumn<TransferindoListas, String> gasto;

	@FXML
	private TableColumn<TransferindoListas, Void> editar;

	@FXML
	private TableColumn<TransferindoListas, Void> remover;

	private ArrayList<TransferindoListas> gastos;
	private Fachada fachada;

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		fachada = new Fachada();
		gastos = fachada.inicializarGastos();

		
		ObservableList<TransferindoListas> dados = FXCollections.observableArrayList(gastos);
		gasto = new TableColumn<TransferindoListas, String>("Gastos");
		gasto.setPrefWidth(855);
		gasto.setStyle("-fx-font-size: 20px");
		gasto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		editar = new TableColumn<TransferindoListas, Void>("Editar");
		editar.setPrefWidth(100);
		remover = new TableColumn<TransferindoListas, Void>("Remover");
		remover.setPrefWidth(100);
		
		tblGastos.getColumns().add(gasto);
		tblGastos.getColumns().add(editar);
		tblGastos.getColumns().add(remover);
		
		tblGastos.setItems(dados);
	}
	
	public void inicializaValores() {
		gastos = fachada.inicializarGastos();
		
		ObservableList<TransferindoListas> dados = FXCollections.observableArrayList(gastos);
		gasto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		tblGastos.setItems(dados);
		
		editar.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Editar");
			private final HBox container = new HBox(btn);
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event -> {
					TransferindoListas tl = getTableView().getItems().get(getIndex());
					Main.mudarTelaEdicao("editarGasto", tl.getId());
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
					TransferindoListas tl = getTableView().getItems().get(getIndex());
					try {
						fachada.removerGasto(tl.getId());
						inicializaValores();
					} catch (IOException | CampoVazioException e) {
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
	protected void btnNovoGastoAction(ActionEvent e) {
		Main.mudarTela("novoGasto");
	}
}