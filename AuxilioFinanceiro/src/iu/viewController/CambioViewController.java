package iu.viewController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.DoubleStringConverter;
import main.Main;
import negocio.CambioNegocio;

public class CambioViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnConfirmar;
	
	@FXML
	private TextField txtValor;
	
	@FXML
	private ComboBox<String> cbMoeda;
	
	private CambioNegocio cambio;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			cambio = new CambioNegocio();
		} catch (IOException e) {
			e.printStackTrace();
		}
		carregarValores();
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("inicial");
	}
	@FXML
	protected void carregarValores() {
		ArrayList<String> moedas = cambio.getMoedasdestino();
		ObservableList<String> l = FXCollections.observableArrayList(moedas);
		cbMoeda.setItems(l);
		cbMoeda.setVisibleRowCount(5);
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		String destino = cbMoeda.getSelectionModel().getSelectedItem();
		double valor = new DoubleStringConverter().fromString(txtValor.getText());
		double resultado = 0;
		Alert alerta = new Alert(AlertType.INFORMATION);
		try {
			resultado = cambio.realizarCambio(valor, destino);
		} catch (URISyntaxException | IOException e1) {
			alerta.setTitle("Conversão");
			alerta.setContentText("Houve um erro ao converter");
		}
		alerta.setTitle("Valor convertido: ");
		String valorStr = String.format("%.2f",valor);
		String resultadoStr= String.format("%.2f", resultado);
		alerta.setContentText(valorStr+" BRL = "+resultadoStr+ " "+ destino);
		alerta.showAndWait();
	}
}