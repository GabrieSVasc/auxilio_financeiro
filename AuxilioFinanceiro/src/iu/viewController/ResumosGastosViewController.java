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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import main.Main;
import negocio.entidades.Barra;
import negocio.entidades.GraficoBarras;
import negocio.entidades.GraficoSetores;
import negocio.entidades.Setor;
import negocio.exceptions.CampoVazioException;
import negocio.exceptions.CategoriaSemGastosException;
import negocio.exceptions.MesSemGastosException;

public class ResumosGastosViewController implements Initializable {

	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;

	@FXML
	private ComboBox<String> cbCategorias;

	@FXML
	private ComboBox<String> cbMeses;

	@FXML
	private PieChart pcPorMes;

	@FXML
	private Button btnVerMudancaMes;

	@FXML
	private BarChart bcPorCategoria;

	@FXML
	private Button btnVerMudancaCategoria;

	private Fachada fachada;

	private ArrayList<String> meses;

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
		fachada = new Fachada();
		carregarValores();
	}

	@FXML
	protected void carregarValores() {
		meses = fachada.inicializarMeses();
		ObservableList<String> lista = FXCollections.observableArrayList(meses);
		cbMeses.setItems(lista);
		cbMeses.setVisibleRowCount(5);
		cbMeses.getSelectionModel().select(0);

		ArrayList<String> categorias = fachada.inicializarCategorias();
		ObservableList<String> listaC = FXCollections.observableArrayList(categorias);
		cbCategorias.setItems(listaC);
		cbCategorias.setVisibleRowCount(5);
		cbCategorias.getSelectionModel().select(0);
	}

	@FXML
	public void criarGraficos() {
		criarGraficoSetores();
		criarGraficoBarras();
	}

	@FXML
	protected void criarGraficoSetores() {
		GraficoSetores grafico = null;
		try {
			grafico = fachada.inicializarGraficoSetoresMes(cbMeses.getSelectionModel().getSelectedItem());
			ArrayList<PieChart.Data> array = new ArrayList<>();
			for (Setor s : grafico.getSetores()) {
				array.add(new PieChart.Data(s.getTituloSetor(), s.getValorTotal()));
			}
			ObservableList<PieChart.Data> pcData = FXCollections.observableArrayList(array);
			pcPorMes.setData(pcData);
			pcPorMes.setPrefSize(300, 300);
			pcPorMes.setTitle("Gráfico do mês: " + grafico.getMes());
		} catch (MesSemGastosException e) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Gráfico de gastos do mês");
			alerta.setContentText("Não há gastos cadastrados nesse mês");
			alerta.showAndWait();
			cbMeses.getSelectionModel().select(0);
		} catch (CampoVazioException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void criarGraficoBarras() {
		GraficoBarras grafico = null;
		try {
			grafico = fachada.inicializarGraficoBarrasCategoria(cbCategorias.getSelectionModel().getSelectedItem());
			XYChart.Series categoria = new XYChart.Series<>();
			categoria.setName(grafico.getCategoria().getNome());
			int qtBarras = 0;
			for (Barra b : grafico.getBarras()) {
				categoria.getData().add(new XYChart.Data(b.getMes().toString(), b.getValorTotal()));
				qtBarras++;
			}
			for (qtBarras = qtBarras; qtBarras < 12; qtBarras++) {
				categoria.getData().add(new XYChart.Data<>(meses.get(qtBarras), 0));
			}
			if (bcPorCategoria.getData().size() > 0) {
				bcPorCategoria.getData().remove(0);
			}
			bcPorCategoria.getData().add(categoria);
		} catch (CategoriaSemGastosException e) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Gráfico de categoria");
			alerta.setContentText("Não há gastos cadastrados nessa categoria");
			alerta.showAndWait();
			cbCategorias.getSelectionModel().select(0);
		} catch (CampoVazioException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("gerenciarGastos");
	}

	@FXML
	protected void btnVerMudancaMesAction(ActionEvent e) {
		criarGraficoSetores();
	}

	@FXML
	protected void btnVerMudancaCategoriaAction(ActionEvent e) {
		criarGraficoBarras();
	}
}