package iu.viewController;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import main.Main;
import negocio.entidades.Parametros;
import negocio.entidades.RetornoInvestimento;
import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.TIRImpossivelException;
import negocio.exceptions.ValorInvalidoException;

public class DadosTaxaInternaRetornoViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private CheckBox chMeses;

	@FXML
	private CheckBox chAnos;

	@FXML
	private TextArea txtArrecadacao;
	
	@FXML
	private Spinner<Double> spinnerTempo;

	@FXML
	private Spinner<Double> spinnerCusto;
	
	@FXML
	private Button btnConfirmar;
	
	private static Fachada fachada = new Fachada();
	
	public void inicializandoTela() {
		spinnerCusto.getValueFactory().setValue(0.0);
		spinnerTempo.getValueFactory().setValue(0.0);
		chMeses.setSelected(true);
		chAnos.setSelected(false);
		txtArrecadacao.setText("");
		txtArrecadacao.setPromptText("Ex: (100, 200, 300, 400, 500)");
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("valorPresenteLiquido");
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		int tipoDuracao = 0;
		if(chAnos.isSelected()) {
			tipoDuracao = 1;
		}
		Parametros p = new Parametros(5, 2, spinnerCusto.getValue().doubleValue(), spinnerTempo.getValue().doubleValue(),spinnerTempo.getValue().doubleValue(), tipoDuracao);
		p.setArrecadacao(txtArrecadacao.getText());
		try {
			RetornoInvestimento r = fachada.investimentos(p);
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Taxa Interna de Retorno");
			alerta.setContentText("O resultado da simulação foi: "+ String.format("%.2f", r.getTIR()*100));
			alerta.showAndWait();
		} catch (OpcaoInvalidaException e1) {
			e1.printStackTrace();
		} catch (ValorInvalidoException e1) {
			e1.printStackTrace();
		} catch (FormatacaoInvalidaException e1) {
			e1.printStackTrace();
		} catch (TIRImpossivelException e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
		TextFormatter<Double> txtFormatt = new TextFormatter<>(new javafx.util.StringConverter<>() {
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

		SpinnerValueFactory<Double> value = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE,
				0.0, 0.01);
		spinnerCusto.setValueFactory(valueFactory);
		spinnerCusto.getEditor().setTextFormatter(textFormatter);
		spinnerCusto.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());
		spinnerTempo.setValueFactory(value);
		spinnerTempo.getEditor().setTextFormatter(txtFormatt);
		spinnerTempo.setEditable(true);
		value.valueProperty().bindBidirectional(txtFormatt.valueProperty());
	}
	
	@FXML
	protected void chMesesAction(ActionEvent e) {
		chAnos.setSelected(false);
	}
	
	@FXML
	protected void chAnosAction(ActionEvent e) {
		chMeses.setSelected(false);
	}
}