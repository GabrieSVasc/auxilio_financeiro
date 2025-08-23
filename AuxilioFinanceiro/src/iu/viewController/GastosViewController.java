package iu.viewController;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import main.Main;

public class GastosViewController implements Initializable {
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;

	@FXML
	private Button btnNovoGasto;

	@FXML
	private TableView tblGastos;

	@FXML
	private TableColumn<String, String> gasto;

	@FXML
	private TableColumn editar;

	@FXML
	private TableColumn remover;

	private ArrayList<TransferindoListas> gastos;
	private Fachada fachada;

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		fachada = new Fachada();
		gastos = fachada.inicializarGastos();

		ArrayList<String> str = new ArrayList<>();

		for (TransferindoListas g : gastos) {
			str.add(g.getStringLista());
		}
		
		ObservableList<String> dados = FXCollections.observableArrayList(str);
		gasto = new TableColumn();
		gasto.setPrefWidth(872);
		gasto.setStyle("-fx-font-size: 20px");
		gasto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
		

		tblGastos.getColumns().add(gasto);

		tblGastos.setItems(dados);
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