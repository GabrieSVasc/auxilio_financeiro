package iu.viewController;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import fachada.Fachada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import main.Main;
import negocio.entidades.ValorLista;
import negocio.exceptions.ObjetoJaExisteException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;
import negocio.exceptions.ValorNegativoException;

/**
 * Classe ligada ao fxml da tela de criação de limites
 * 
 * @author Maria Gabriela
 */

public class NovoLimiteViewController implements Initializable {
	@FXML
	private Button btnVoltar;

	@FXML
	private Button btnNovaCategoria;

	@FXML
	private ImageView imgMais;

	@FXML
	private Button btnConfirmar;
	
	@FXML
	private Spinner<Double> spinnerValorLimite;

	@FXML
	private ComboBox<ValorLista> cbCategoria;
	
	private static Fachada fachada = new Fachada();

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnNovaCategoria.setGraphic(imgMais);

		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,
				Double.MAX_VALUE, 10.0, 0.1);
		DecimalFormat format = new DecimalFormat("#.##");
		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
			if (newText.isEmpty())
				return change;
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parseObject(newText, parsePosition);
			if (object == null || parsePosition.getIndex() < newText.length()) {
				return null;
			} else {
				return change;
			}
		};
		TextFormatter<Double> textFormatter = new TextFormatter<>(new javafx.util.StringConverter<>() {
			@Override
			public String toString(Double value) {
				return value == null ? "" : format.format(value);
			}

			@Override
			public Double fromString(String text) {
				try {
					return format.parse(text).doubleValue();
				} catch (Exception e) {
					return 0.0;
				}
			}
		}, 10.0, filter);
		spinnerValorLimite.setValueFactory(valueFactory);
		spinnerValorLimite.getEditor().setTextFormatter(textFormatter);
		spinnerValorLimite.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());

		ArrayList<ValorLista> categoria = fachada.inicializarCategorias();

		ObservableList<ValorLista> listaC = FXCollections.observableArrayList(categoria);
		cbCategoria.setItems(listaC);
		cbCategoria.setVisibleRowCount(5);
		cbCategoria.getSelectionModel().select(0);
	}

	public void atualizandoTela() {
		spinnerValorLimite.getValueFactory().setValue(10.0);
		cbCategoria.getSelectionModel().select(0);
		
		ArrayList<ValorLista> categoria = fachada.inicializarCategorias();
		
		ObservableList<ValorLista> listaC = FXCollections.observableArrayList(categoria);
		cbCategoria.setItems(listaC);
		cbCategoria.getSelectionModel().select(0);
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("limitesGastos");
	}

	@FXML
	protected void btnNovaCategoriaAction(ActionEvent e) {
		Main.mudarTela("novaCategoria");
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.criarLimite(cbCategoria.getSelectionModel().getSelectedItem().getId(), spinnerValorLimite.getValue().doubleValue());
			Main.mudarTela("limitesGastos");
		} catch (ObjetoNaoEncontradoException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Tentando criar limite para uma categoria inexistente");
			alerta.showAndWait();
		} catch (ValorNegativoException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("O valor não pode ser negativo");
			alerta.showAndWait();
		} catch (ObjetoNuloException e1) {
			e1.printStackTrace();
		} catch (ObjetoJaExisteException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERRO");
			alerta.setContentText("Já existe um limite definido para a categoria "+cbCategoria.getSelectionModel().getSelectedItem().getStringLista());
			e1.printStackTrace();
		}
	}
}
