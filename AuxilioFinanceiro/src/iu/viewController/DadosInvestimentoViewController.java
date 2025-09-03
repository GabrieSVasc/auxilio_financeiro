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
import javafx.scene.control.CheckBox;
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

/**
 * Classe ligada ao fxml da tela de dados de investimento
 * 
 * @author Maria Gabriela
 */

public class DadosInvestimentoViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Label lblTipoInvestimento;
	
	@FXML
	private ImageView imgTutoriais;

	@FXML
	private Button btnTutoriais;
	
	@FXML
	private Spinner<Double> spinnerValor;
	
	@FXML
	private Spinner<Double> spinnerTaxa;
	
	@FXML
	private Spinner<Double> spinnerTempo;
	
	@FXML
	private CheckBox chMeses;
	
	@FXML
	private CheckBox chAnos;
	
	@FXML
	private CheckBox chInflacao;
	
	@FXML
	private Button btnConfirmar;
	
	private int tipoInvestimentoInt;
	private String tipoInvestimentoStr;
	
	private static Fachada fachada = new Fachada();
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("investimento");
	}
	
	public void tipoInvestimento(int tipo) {
		tipoInvestimentoInt = tipo;
		switch(tipo) {
		case 1:
			tipoInvestimentoStr = "Juros Simples";
			break;
		case 2:
			tipoInvestimentoStr = "Juros Compostos";
			break;
		case 3:
			tipoInvestimentoStr = "Aportes Periodicos";
			break;
		}
		lblTipoInvestimento.setText(tipoInvestimentoStr);
		
		spinnerValor.getValueFactory().setValue(0.0);
		spinnerTaxa.getValueFactory().setValue(0.0);
		spinnerTempo.getValueFactory().setValue(0.0);
		chInflacao.setSelected(false);;
		chMeses.setSelected(true);
		chAnos.setSelected(false);
		
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		int tipoDuracao = 0;
		if(chAnos.isSelected()) {
			tipoDuracao = 1;
		}
		Parametros p = new Parametros(1, tipoInvestimentoInt, spinnerValor.getValue().doubleValue(), spinnerTaxa.getValue().doubleValue(), spinnerTempo.getValue().doubleValue(), tipoDuracao);
		try {
			RetornoInvestimento r = fachada.investimentos(p);
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle(tipoInvestimentoStr);
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
			alerta.setContentText("O Valor é inválido");
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
		spinnerTaxa.setValueFactory(vlFactory);
		spinnerTaxa.getEditor().setTextFormatter(txtForm);
		spinnerTaxa.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());
		vlFactory.valueProperty().bindBidirectional(txtForm.valueProperty());
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
	
	@FXML
	protected void btnTutoriaisAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("tutoriais", tipoInvestimentoInt);
	}
}