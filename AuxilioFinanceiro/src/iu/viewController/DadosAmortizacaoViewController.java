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
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import main.Main;
import negocio.Excecoes.FormatacaoInvalidaException;
import negocio.Excecoes.OpcaoInvalidaException;
import negocio.Excecoes.TIRImpossivelException;
import negocio.Excecoes.ValorInvalidoException;
import negocio.entidades.entidades.Parametros;

public class DadosAmortizacaoViewController implements Initializable{
	@FXML
	private Button btnVoltar;

	@FXML
	private Button btnConfirmar;
	
	@FXML
	private Label lblTipoAmortizacao;
	
	@FXML
	private Spinner<Double> spinnerTaxa;
	
	@FXML
	private Spinner<Double> spinnerValor;

	@FXML
	private Spinner<Integer> spinnerParcelas;
	
	private static Fachada fachada = new Fachada();

	private int tipoAmort;
	
	private String tipoAmortStr;

	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("amortizacao");
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		Parametros p = new Parametros(4, tipoAmort, spinnerValor.getValue().doubleValue(), spinnerTaxa.getValue().doubleValue(), spinnerParcelas.getValue().intValue());
		try {
			fachada.investimentos(p);
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Resultado");
			alerta.setContentText("Resultado da "+this.tipoAmortStr+": ");
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
	
	public void tipoAmortizacao(int tipo) {
		tipoAmort = tipo;
		switch (tipo) {
		case 1:
			lblTipoAmortizacao.setText("Amortização Price");
			tipoAmortStr = "Amortização Price";
			break;
		case 2:
			lblTipoAmortizacao.setText("Amortização Constante");
			tipoAmortStr = "Amortização Constante";
			break;
		case 3:
			lblTipoAmortizacao.setText("Amortização Mista");
			tipoAmortStr = "Amortização Mista";
			break;
		}
		spinnerTaxa.getValueFactory().setValue(0.0);
		spinnerValor.getValueFactory().setValue(0.0);
		spinnerParcelas.getValueFactory().setValue(0);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, 0.0, 0.01);
		SpinnerValueFactory<Double> vlFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, 0.0, 0.01);
		DecimalFormat format = new DecimalFormat("#.##");
		UnaryOperator<TextFormatter.Change> filter = change->{
			String newText = change.getControlNewText();
			if(newText.isEmpty()) return change;
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parseObject(newText, parsePosition);
			if(object == null || parsePosition.getIndex()<newText.length()) {
				return null;
			}else {
				return change;
			}
		};
		TextFormatter<Double> textFormatter = new TextFormatter<>(
				new javafx.util.StringConverter<>() {
					@Override
					public String toString(Double value) {
						return value==null? "":format.format(value);
					}
					
					@Override
					public Double fromString(String text) {
						try {
							return format.parse(text).doubleValue();
						}catch(Exception e) {
							return 0.0;
						}
					}
				}, 10.0, filter);
		TextFormatter<Double> txtForm = new TextFormatter<>(
				new javafx.util.StringConverter<>() {
					@Override
					public String toString(Double value) {
						return value==null? "":format.format(value);
					}
					
					@Override
					public Double fromString(String text) {
						try {
							return format.parse(text).doubleValue();
						}catch(Exception e) {
							return 0.0;
						}
					}
				}, 10.0, filter);
		
		SpinnerValueFactory<Integer> valueInt = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
		spinnerValor.setValueFactory(valueFactory);
		spinnerValor.getEditor().setTextFormatter(textFormatter);
		spinnerValor.setEditable(true);
		spinnerTaxa.setValueFactory(vlFactory);
		spinnerTaxa.getEditor().setTextFormatter(txtForm);
		spinnerTaxa.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());
		vlFactory.valueProperty().bindBidirectional(txtForm.valueProperty());
		spinnerParcelas.setValueFactory(valueInt);
		spinnerParcelas.setEditable(true);
	}
}