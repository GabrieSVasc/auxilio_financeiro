package iu.viewController;

import java.io.IOException;
import java.net.URISyntaxException;
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
import main.Main;
import negocio.exceptions.ErroAoReceberConversaoException;
import negocio.exceptions.LimiteDeConvesoesException;

/**
 * Classe ligada ao fxml da tela de conversão de moedas
 * 
 * @author Maria Gabriela
 */

public class CambioViewController implements Initializable {
	@FXML
	private Button btnVoltar;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Spinner<Double> spinnerValor;

	@FXML
	private ComboBox<String> cbMoeda;

	private Fachada fachada;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,
				Double.MAX_VALUE, 0.0, 0.01);
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
		spinnerValor.setValueFactory(valueFactory);
		spinnerValor.getEditor().setTextFormatter(textFormatter);
		spinnerValor.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());
		fachada = new Fachada();
		fachada.inicializarCambio();
		carregarValores();
	}

	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("inicial");
	}

	@FXML
	protected void carregarValores() {
		ArrayList<String> moedas = fachada.getMoedasDestino();
		ObservableList<String> l = FXCollections.observableArrayList(moedas);
		cbMoeda.setItems(l);
		cbMoeda.setVisibleRowCount(5);
	}

	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		String destino = cbMoeda.getSelectionModel().getSelectedItem();
		double valor = spinnerValor.getValue().doubleValue();
		double resultado = 0;
		Alert alerta = new Alert(AlertType.INFORMATION);
		try {
			resultado = fachada.realizarCambio(valor, destino);
			alerta.setTitle("Valor convertido: ");
			String valorStr = String.format("%.2f", valor);
			String resultadoStr = String.format("%.2f", resultado);
			alerta.setContentText(valorStr + " BRL = " + resultadoStr + " " + destino);
			alerta.showAndWait();
		} catch (URISyntaxException | IOException | ErroAoReceberConversaoException e1) {
			alerta.setTitle("Conversão");
			alerta.setContentText("Houve um erro ao converter");
		} catch (LimiteDeConvesoesException e1) {
			alerta.setTitle("Conversão");
			alerta.setContentText("O limite de conversões mensais foi atingido");
		}
	}
}