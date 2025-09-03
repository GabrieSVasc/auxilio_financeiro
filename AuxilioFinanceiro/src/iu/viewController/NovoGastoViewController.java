package iu.viewController;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import main.Main;
import negocio.entidades.ValorLista;
import negocio.exceptions.CampoVazioException;

public class NovoGastoViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnNovaCategoria;
	
	@FXML
	private ImageView imgMais;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Spinner<Double> spinnerValor;
	
	@FXML
	private DatePicker dateData;
	
	@FXML
	private ComboBox<String> cbCategoria;
	
	@FXML
	private Button btnConfirmar;
	
	private static Fachada fachada = new Fachada();
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnNovaCategoria.setGraphic(imgMais);
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
		
		ArrayList<ValorLista> categoria = fachada.inicializarCategorias();
		
		ArrayList<String> nC = new ArrayList<>();
		
		for(ValorLista tl: categoria) {
			nC.add(tl.getStringLista());
		}
		
		ObservableList<String> listaC = FXCollections.observableArrayList(nC);
		cbCategoria.setItems(listaC);
		cbCategoria.setVisibleRowCount(5);
		cbCategoria.getSelectionModel().select(0);
	}
	
	public void atualizandoTela() {
		txtNome.setText("");
		spinnerValor.getValueFactory().setValue(10.0);
		dateData.setValue(null);
		cbCategoria.getSelectionModel().select(0);
		
		ArrayList<ValorLista> categoria = fachada.inicializarCategorias();
		
		ArrayList<String> nC = new ArrayList<>();
		
		for(ValorLista tl: categoria) {
			nC.add(tl.getStringLista());
		}
		
		ObservableList<String> listaC = FXCollections.observableArrayList(nC);
		cbCategoria.setItems(listaC);

	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gastos");
	}
	
	@FXML
	protected void btnNovaCategoriaAction(ActionEvent e) {
		Main.mudarTela("novaCategoria");
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		Optional<String> resultado = Optional.ofNullable("");
		if(cbCategoria.getSelectionModel().getSelectedItem().equals("Mensal")) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Recorrencia");
			dialog.setHeaderText("Digite a recorrência: ");
			dialog.setContentText("Recorrencia: ");
			 resultado = dialog.showAndWait();
		}
		try {
			fachada.criarGasto(txtNome.getText(), spinnerValor.getValue().doubleValue(), dateData.getValue(), cbCategoria.getSelectionModel().getSelectedItem().toString(), resultado.get());
			Main.mudarTela("gastos");
		} catch (CampoVazioException e1) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Erro");
			alerta.setContentText("Todos os campos devem estar preenchidos");
			alerta.showAndWait();
		}
	}
}