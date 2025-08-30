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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import main.Main;
import negocio.entidades.Gasto;
import negocio.exceptions.CampoVazioException;

public class EditarGastoViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Spinner<Double> spinnerValor;
	
	@FXML
	private DatePicker dateData;
	
	@FXML
	private Button btnConfirmar;

	private int idGasto;
	
	private static Fachada fachada = new Fachada();
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, 10.0, 0.1);
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
		spinnerValor.setValueFactory(valueFactory);
		spinnerValor.getEditor().setTextFormatter(textFormatter);
		spinnerValor.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gastos");
	}
	
	public void gastoEscolhido(int v) {
		idGasto = v;
		Gasto g = fachada.getGasto(v);
		txtNome.setText(g.getNome());
		spinnerValor.getValueFactory().setValue(g.getValor());
		dateData.setValue(g.getDataCriacao());
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.editarGasto(idGasto, txtNome.getText(), spinnerValor.getValue(), dateData.getValue());
			Main.mudarTela("gastos");
		} catch (CampoVazioException e1) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Erro");
			alerta.setContentText("Todos os campos devem estar preenchidos");
			alerta.showAndWait();
		}
	}
}