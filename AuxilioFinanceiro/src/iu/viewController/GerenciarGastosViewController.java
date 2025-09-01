package iu.viewController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fachada.Fachada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.Main;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Setor;
import negocio.exceptions.MesSemGastosException;

public class GerenciarGastosViewController implements Initializable{
	
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;
	
	@FXML
	private Button btnResumosGastos;
	
	@FXML
	private Button btnLimites;
	
	@FXML
	private Button btnMetas;
	
	@FXML
	private Button btnGastos;
	
	@FXML
	private Button btnCategorias;
	
	@FXML
	private PieChart pcMesAtual;
	
	private Fachada fachada;
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		fachada = new Fachada();
	}
	
	@FXML
	public void criarGrafico() {
		GraficoSetores grafico = null;
		try {
			grafico = fachada.inicializarGraficoSetores();
			ArrayList<PieChart.Data> array = new ArrayList<>();
			for(Setor s : grafico.getSetores()) {
				array.add(new PieChart.Data(s.getTituloSetor(), s.getValorTotal()));
			}
			ObservableList<PieChart.Data> pcData = FXCollections.observableArrayList(array);
			pcMesAtual.setData(pcData);
			pcMesAtual.setPrefSize(435, 435);
			pcMesAtual.setTitle("Gráfico do mês: "+grafico.getMes());
		} catch (MesSemGastosException e) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Gráfico de gastos desse mês");
			alerta.setContentText("Não há gastos cadastrados nesse mês");
			alerta.showAndWait();
			if(pcMesAtual.getData().size()==0) {
				pcMesAtual.getData().add(new PieChart.Data("Vazio", 10));
				pcMesAtual.setTitle("Gráfico do mês");
			}
		}
	}
	
	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("inicial");
	}
	
	@FXML
	protected void btnResumosGastosAction(ActionEvent e) {
		Main.mudarTela("resumosGastos");
	}
	
	@FXML
	protected void btnLimitesAction(ActionEvent e) {
		Main.mudarTela("limitesGastos");
	}
	
	@FXML
	protected void btnMetasAction(ActionEvent e) {
		Main.mudarTela("metas");
	}
	
	@FXML
	protected void btnGastosAction(ActionEvent e) {
		Main.mudarTela("gastos");
	}
	
	@FXML
	protected void btnCategoriasAction(ActionEvent e) {
		Main.mudarTela("categorias");
	}
}