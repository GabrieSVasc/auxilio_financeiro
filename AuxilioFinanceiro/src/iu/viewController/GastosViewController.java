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

/**
 * Classe ligada ao fxml da tela de listagem de gastos
 * 
 * @author Maria Gabriela
 */

public class GastosViewController implements Initializable {
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;

	@FXML
	private Button btnNovoGasto;

	@FXML
	private TableView<ValorLista> tblGastos;

	@FXML
	private TableColumn<ValorLista, String> gasto;

	@FXML
	private TableColumn<ValorLista, Void> editar;

	@FXML
	private TableColumn<ValorLista, Void> remover;

	private ArrayList<ValorLista> gastos;
	private static Fachada fachada = new Fachada();

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		gastos = fachada.inicializarGastos();

		ObservableList<ValorLista> dados = FXCollections.observableArrayList(gastos);
		gasto = new TableColumn<ValorLista, String>("Gastos");
		gasto.setPrefWidth(855);
		gasto.setStyle("-fx-font-size: 20px");
		gasto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		editar = new TableColumn<ValorLista, Void>("Editar");
		editar.setPrefWidth(100);
		remover = new TableColumn<ValorLista, Void>("Remover");
		remover.setPrefWidth(100);

		tblGastos.getColumns().add(gasto);
		tblGastos.getColumns().add(editar);
		tblGastos.getColumns().add(remover);

		tblGastos.setItems(dados);
	}

	public void inicializaValores() {
		gastos = fachada.inicializarGastos();

		ObservableList<ValorLista> dados = FXCollections.observableArrayList(gastos);
		gasto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		tblGastos.setItems(dados);

		editar.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Editar");
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event -> {
					ValorLista tl = getTableView().getItems().get(getIndex());
					Main.mudarTelaEdicao("editarGasto", tl.getId(), tl.getStringLista());
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
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
						fachada.removerGasto(tl.getId(), tl.getStringLista());
						inicializaValores();
					} catch (ObjetoNaoEncontradoException e) {
						Alert alerta = new Alert(AlertType.ERROR);
						alerta.setTitle("ERRO");
						alerta.setContentText("Tentando remover gasto inexistente");
						alerta.showAndWait();
					}
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
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