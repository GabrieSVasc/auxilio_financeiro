package iu.viewController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fachada.Fachada;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.Main;
import negocio.entidades.ValorLista;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;

public class LembretesViewController implements Initializable {
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;

	@FXML
	private TableView<ValorLista> tblLembretes;

	@FXML
	private TableColumn<ValorLista, Boolean> ativo;

	@FXML
	private TableColumn<ValorLista, String> lembrete;

	@FXML
	private TableColumn<ValorLista, Void> editar;

	@FXML
	private TableColumn<ValorLista, Void> remover;

	@FXML
	private Button btnNovoLembrete;

	private ArrayList<ValorLista> lembretes;
	private static Fachada fachada = new Fachada();

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		lembretes = fachada.inicializarLembretes();

		ObservableList<ValorLista> dados = FXCollections.observableArrayList(lembretes);

		ativo = new TableColumn<ValorLista, Boolean>();
		ativo.setPrefWidth(50);
		ativo.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isAtivo()));
		ativo.setEditable(true);
		
		lembrete = new TableColumn<ValorLista, String>("Lembretes");
		lembrete.setPrefWidth(805);
		lembrete.setStyle("-fx-font-size: 20px");
		lembrete.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		editar = new TableColumn<ValorLista, Void>("Editar");
		editar.setPrefWidth(100);
		remover = new TableColumn<ValorLista, Void>("Remover");
		remover.setPrefWidth(100);

		tblLembretes.getColumns().add(ativo);
		tblLembretes.getColumns().add(lembrete);
		tblLembretes.getColumns().add(editar);
		tblLembretes.getColumns().add(remover);

		tblLembretes.setItems(dados);

	}

	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("inicial");
	}

	@FXML
	protected void btnNovoLembreteAction(ActionEvent e) {
		Main.mudarTela("novoLembrete");
	}

	public void inicializaValores() {
		lembretes = fachada.inicializarLembretes();

		ObservableList<ValorLista> dados = FXCollections.observableArrayList(lembretes);
		lembrete.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStringLista()));
		tblLembretes.setItems(dados);
		
		ativo.setCellFactory(col -> new TableCell<>() {
			private final CheckBox btn = new CheckBox();
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				btn.setAlignment(Pos.CENTER);
				btn.setOnAction(event -> {
					ValorLista tl = getTableView().getItems().get(getIndex());
					try {
						fachada.setLembreteAtivo(tl.getId(), !tl.isAtivo());
						tl.setAtivo(!tl.isAtivo());
					} catch (ObjetoNaoEncontradoException e) {
						Alert alerta = new Alert(AlertType.ERROR);
						alerta.setTitle("ERRO");
						alerta.setContentText(e.getTipoObjeto()+ " não encontrado");
						alerta.showAndWait();
						e.printStackTrace();
					}catch (CampoVazioException e) {
						e.printStackTrace();
					}
				});
			}

			@Override
			protected void updateItem(Boolean ativoC, boolean empty) {
				super.updateItem(ativoC, empty);
				if (empty) {
					setGraphic(null);
				} else {
					btn.setSelected(ativoC != null && ativoC);
					setGraphic(btn);
				}
			}
		});

		editar.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Editar");
			{
				btn.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(btn, Priority.ALWAYS);
				btn.setOnAction(event -> {
					ValorLista tl = getTableView().getItems().get(getIndex());
					Main.mudarTelaEdicao("editarLembrete", tl.getId(), "");
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
						fachada.removerLembrete(tl.getId());
					} catch (ObjetoNaoEncontradoException e) {
						Alert alerta = new Alert(AlertType.ERROR);
						alerta.setTitle("ERRO");
						alerta.setContentText(e.getTipoObjeto()+ " não encontrado");
						alerta.showAndWait();
						e.printStackTrace();
					}
					inicializaValores();
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
}