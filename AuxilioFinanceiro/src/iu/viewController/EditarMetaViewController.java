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
import negocio.entidades.Meta;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;

/**
 * Classe ligada ao fxml da tela de edição de meta
 * 
 * @author Maria Gabriela
 */

public class EditarMetaViewController implements Initializable{
	@FXML
	private Button btnVoltar;

	@FXML
	private TextField txtDescricao;

	@FXML
	private Spinner<Double> spinnerValorObjetivo;

	@FXML
	private Spinner<Double> spinnerValorAtual;

	@FXML
	private DatePicker dtpPrazo;

	@FXML
	private Button btnConfirmar;

	private static Fachada fachada = new Fachada();

	private int idMeta;

	public int getIdMeta() {
		return idMeta;
	}

	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("metas");
	}

	public void metaEscolhida(int meta) {
		Meta m = fachada.getMeta(meta);
		idMeta = m.getId();
		txtDescricao.setText(m.getDescricao());
		dtpPrazo.setValue(m.getDataPrazo());
		spinnerValorObjetivo.getValueFactory().setValue(m.getValorObjetivo());
		spinnerValorAtual.getValueFactory().setValue(m.getValorAtual());
	}

	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		try {
			fachada.editarMeta(idMeta, txtDescricao.getText(), spinnerValorObjetivo.getValue().doubleValue(),
					spinnerValorAtual.getValue().doubleValue(), dtpPrazo.getValue());
			Main.mudarTela("metas");
		} catch (ObjetoNaoEncontradoException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Tentando editar uma meta que não existe" + e1.getTipoObjeto());
			alerta.showAndWait();
		} catch (CampoVazioException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Todos os campos devem estar preenchidos");
			alerta.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,
				Double.MAX_VALUE, 0.0, 0.1);
		SpinnerValueFactory<Double> vlFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE,
				0.0, 0.1);
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
		TextFormatter<Double> txtForm = new TextFormatter<>(new javafx.util.StringConverter<>() {
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
		spinnerValorObjetivo.setValueFactory(valueFactory);
		spinnerValorObjetivo.getEditor().setTextFormatter(textFormatter);
		spinnerValorObjetivo.setEditable(true);
		spinnerValorAtual.setValueFactory(vlFactory);
		spinnerValorAtual.getEditor().setTextFormatter(txtForm);
		spinnerValorAtual.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());
	}
}