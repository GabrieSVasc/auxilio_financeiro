package iu.viewController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.json.JSONObject;

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

public class CambioViewController implements Initializable{
	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnConfirmar;
	
	@FXML
	private TextField txtValor;
	
	@FXML
	private ComboBox<String> cbMoeda;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		carregarValores();
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("inicial");
	}
	@FXML
	protected void carregarValores() {
		//TODO: Mudar a parte de carregar valores para a fachada
		String nome = "src/files/Valores.txt";
		String arq="";
		try {
			arq = new String(Files.readAllBytes(Paths.get(nome)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject(arq);
		JSONObject currencies = obj.getJSONObject("currencies");
		Iterator<String> keys = currencies.keys();
		while(keys.hasNext()) {
			String key=keys.next().toString();
			cbMoeda.getItems().add(key);
		}
		cbMoeda.setVisibleRowCount(5);
		cbMoeda.getSelectionModel().selectFirst();
	}
	
	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		//TODO: mudar essa execução para a regra de negócios
		String key = "KEY DO EXCHANGERATE";
		String destino = cbMoeda.getSelectionModel().getSelectedItem();
		double valor = new DoubleStringConverter().fromString(txtValor.getText());
		String urlStr = "http://api.exchangerate.host/convert?access_key="+key+"&from=BRL&to="+destino+"&amount="+valor;
		try{
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder json = new StringBuilder();
			String line;
			while((line = reader.readLine())!=null) {
				json.append(line);
			}
			reader.close();
			JSONObject obj = new JSONObject(json.toString());
			double resultado = obj.getDouble("result");
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Valor convertido: ");
			String valorStr = String.format("%.2f",valor);
			String resultadoStr= String.format("%.2f", resultado);
			alerta.setContentText(valorStr+" BRL = "+resultadoStr+ " "+ destino);
			alerta.showAndWait();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}