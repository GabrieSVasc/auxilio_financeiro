package negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import negocio.exceptions.ErroAoReceberConversaoException;
import negocio.exceptions.LimiteDeConvesoesException;
/**
 * Classe que lida com a conversão de moedas
 * @author Maria Gabriela
 */
public class CambioNegocio {
	private static final ArrayList<String> MOEDASDESTINO = new ArrayList<String>();
	private static final String KEY = "KEY DO EXCHANGERATE";

	public CambioNegocio() throws IOException {
		String caminhoArquivo = "files/Valores.txt";
		String arquivo = "";
		arquivo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
		JSONObject obj = new JSONObject(arquivo);
		JSONObject currencies = obj.getJSONObject("currencies");
		Iterator<String> keys = currencies.keys();
		while (keys.hasNext()) {
			String key = keys.next().toString();
			MOEDASDESTINO.add(key);
		}
	}

	/**
	 * Método que recupera todas as moedas destino possíveis
	 * @return Um arraylist de strings com as moedas destino
	 */
	public ArrayList<String> getMoedasdestino() {
		return MOEDASDESTINO;
	}

	/**
	 * Método que realiza a conversão
	 * @param valor
	 * @param moedaEscolhida
	 * @return resultado da conversão
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws LimiteDeConvesoesException
	 * @throws ErroAoReceberConversaoException
	 */
	public double realizarCambio(double valor, String moedaEscolhida) throws URISyntaxException, IOException, LimiteDeConvesoesException, ErroAoReceberConversaoException {
		String urlStr = "http://api.exchangerate.host/convert?access_key=" + KEY + "&from=BRL&to=" + moedaEscolhida
				+ "&amount=" + valor;
		URI uri = new URI(urlStr);
		URL url = uri.toURL();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder json = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			json.append(line);
		}
		reader.close();
		JSONObject obj = new JSONObject(json.toString());
		if (obj.getBoolean("success")) {
			return obj.getDouble("result");
		}else if(obj.getJSONObject("error").getInt("code")==104) {
			throw new LimiteDeConvesoesException("O limite de conversões mensais foi atingido");
		}else {
			throw new ErroAoReceberConversaoException("Houve um erro ao tentar converter o valor");
		}
	}
}