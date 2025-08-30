package iu.viewController;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import main.Main;
import negocio.entidades.Limite;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ValorNegativoException;

public class EditarLimiteViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Spinner<Double> spinnerValorLimite;
	
	@FXML
	private Button btnConfirmar;
	
	private static Fachada fachada = new Fachada();
	private int idLimite;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
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
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("limitesGastos");
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.editarLimite(idLimite, spinnerValorLimite.getValue().doubleValue());
			Main.mudarTela("limitesGastos");
		} catch (ObjetoNaoEncontradoException e1) {
			e1.printStackTrace();
		} catch (ValorNegativoException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void limiteEscolhido(int v) {
		idLimite = v;
		Limite l = fachada.getLimite(v);
		spinnerValorLimite.getValueFactory().setValue(l.getValor());
	}
}