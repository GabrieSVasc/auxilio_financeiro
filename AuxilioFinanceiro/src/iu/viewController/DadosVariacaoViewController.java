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
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import main.Main;
import negocio.entidades.Parametros;
import negocio.entidades.RetornoInvestimento;
import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.TIRImpossivelException;
import negocio.exceptions.ValorInvalidoException;

public class DadosVariacaoViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private ImageView imgTutoriais;

	@FXML
	private Button btnTutoriais;
	
	@FXML
	private Spinner<Double> spinnerValor;
	
	@FXML
	private Spinner<Double> spinnerInflacao;
	
	@FXML
	private Label lblTipoVariacao;
	
	@FXML
	private Button btnConfirmar;
	
	private int tipoVariacao;
	private String tipoVariacaoStr;
	
	private static Fachada fachada= new Fachada();
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("variacao");
	}
	
	public void tipoVariacao(int tipo) {
		tipoVariacao = tipo;
		if(tipo == 1) {
			tipoVariacaoStr = "Inflação";
		}else if(tipo == 2) {
			tipoVariacaoStr = "Deflação";
		}
		lblTipoVariacao.setText(tipoVariacaoStr);
		spinnerValor.getValueFactory().setValue(0.0);
		spinnerInflacao.getValueFactory().setValue(0.0);
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		Parametros p = new Parametros(3, tipoVariacao, spinnerValor.getValue().doubleValue(), spinnerInflacao.getValue().doubleValue(), 0, 0);
		try {
			RetornoInvestimento r = fachada.investimentos(p);
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle(tipoVariacaoStr);
			alerta.setContentText("O valor do investimento simulado é: "+ String.format("%.2f", r.getMontanteCalculado()));
			alerta.showAndWait();
		} catch (OpcaoInvalidaException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("A opção é inválida");
			alerta.showAndWait();
		} catch (ValorInvalidoException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("O valor é inválido");
			alerta.showAndWait();
		} catch (FormatacaoInvalidaException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("A formatação é inválida");
			alerta.showAndWait();
		} catch (TIRImpossivelException e1) {
			//Essa exceção não acontece para este tipo de simulação
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnTutoriais.setGraphic(imgTutoriais);
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
		TextFormatter<Double> txtFormatt = new TextFormatter<>(
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
		
		SpinnerValueFactory<Double> value = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, 0.0, 0.01);
		spinnerValor.setValueFactory(valueFactory);
		spinnerValor.getEditor().setTextFormatter(textFormatter);
		spinnerValor.setEditable(true);
		spinnerInflacao.setValueFactory(vlFactory);
		spinnerInflacao.getEditor().setTextFormatter(txtForm);
		spinnerInflacao.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());
		vlFactory.valueProperty().bindBidirectional(txtForm.valueProperty());
	}
	
	@FXML
	protected void btnTutoriaisAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("tutoriais", tipoVariacao);
	}
}