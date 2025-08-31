package iu.viewController;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import fachada.Fachada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.Main;
import negocio.entidades.Parametros;
import negocio.entidades.RetornoInvestimento;
import negocio.entidades.ValorAmortizacao;
import negocio.exceptions.FormatacaoInvalidaException;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.TIRImpossivelException;
import negocio.exceptions.ValorInvalidoException;

public class DadosAmortizacaoViewController implements Initializable {
	@FXML
	private Button btnVoltar;

	@FXML
	private Button btnConfirmar;
	
	@FXML
	private ImageView imgTutoriais;

	@FXML
	private Button btnTutoriais;

	@FXML
	private Label lblTipoAmortizacao;

	@FXML
	private Spinner<Double> spinnerTaxa;

	@FXML
	private Spinner<Double> spinnerValor;

	@FXML
	private Spinner<Integer> spinnerParcelas;

	private static Fachada fachada = new Fachada();

	private int tipoAmort;

	private String tipoAmortStr;

	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		Main.mudarTela("amortizacao");
	}

	@FXML
	protected void btnConfirmarAction(ActionEvent e) {
		Parametros p = new Parametros(4, tipoAmort, spinnerValor.getValue().doubleValue(),
				spinnerTaxa.getValue().doubleValue(), spinnerParcelas.getValue().intValue());
		try {
			RetornoInvestimento r = fachada.investimentos(p);

			Dialog dialog = new Dialog();
			dialog.setTitle(tipoAmortStr);
			ButtonType fecharBtn = new ButtonType("Fechar", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().add(fecharBtn);

			TableView<ValorAmortizacao> tbl = new TableView<>();
			tbl.setPrefHeight(300);
			tbl.setPrefWidth(400);
			TableColumn<ValorAmortizacao, Double> parcela = new TableColumn<>("Parcela");
			parcela.setPrefWidth(85);
			TableColumn<ValorAmortizacao, Double> amortizacao = new TableColumn<>("Amortização");
			amortizacao.setPrefWidth(85);
			TableColumn<ValorAmortizacao, Double> juros = new TableColumn<>("Juros");
			juros.setPrefWidth(85);
			TableColumn<ValorAmortizacao, Double> saldoDevedor = new TableColumn<>("Saldo Devedor");
			saldoDevedor.setPrefWidth(100);

			ObservableList<ValorAmortizacao> dados = FXCollections.observableArrayList(r.getValorAmortizacao());
			parcela.setCellValueFactory(new PropertyValueFactory<>("parcela"));
			amortizacao.setCellValueFactory(new PropertyValueFactory<>("amortizacao"));
			juros.setCellValueFactory(new PropertyValueFactory<>("juros"));
			saldoDevedor.setCellValueFactory(new PropertyValueFactory<>("saldoDevedor"));

			parcela.setCellFactory(c -> new TableCell<ValorAmortizacao, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
					} else {
						setText(String.format("%.2f", item));
					}
					setAlignment(Pos.CENTER);
				}
			});

			amortizacao.setCellFactory(c -> new TableCell<ValorAmortizacao, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
					} else {
						setText(String.format("%.2f", item));
					}
					setAlignment(Pos.CENTER);
				}
			});

			juros.setCellFactory(c -> new TableCell<ValorAmortizacao, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
					} else {
						setText(String.format("%.2f", item));
					}
					setAlignment(Pos.CENTER);
				}
			});

			saldoDevedor.setCellFactory(c -> new TableCell<ValorAmortizacao, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
					} else {
						setText(String.format("%.2f", item));
					}
					setAlignment(Pos.CENTER);
				}
			});

			tbl.getColumns().add(parcela);
			tbl.getColumns().add(amortizacao);
			tbl.getColumns().add(juros);
			tbl.getColumns().add(saldoDevedor);

			tbl.setItems(dados);

			VBox conteudo = new VBox(10, tbl);
			conteudo.setPrefSize(400, 300);
			dialog.getDialogPane().setContent(conteudo);
			dialog.showAndWait();

		} catch (OpcaoInvalidaException e1) {
			e1.printStackTrace();
		} catch (ValorInvalidoException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setContentText("Valores devem ser maiores que 0");
		} catch (FormatacaoInvalidaException e1) {
			e1.printStackTrace();
		} catch (TIRImpossivelException e1) {
			e1.printStackTrace();
		}
	}

	public void tipoAmortizacao(int tipo) {
		tipoAmort = tipo;
		switch (tipo) {
		case 1:
			lblTipoAmortizacao.setText("Amortização Price");
			tipoAmortStr = "Amortização Price";
			break;
		case 2:
			lblTipoAmortizacao.setText("Amortização Constante");
			tipoAmortStr = "Amortização Constante";
			break;
		case 3:
			lblTipoAmortizacao.setText("Amortização Mista");
			tipoAmortStr = "Amortização Mista";
			break;
		}
		spinnerTaxa.getValueFactory().setValue(0.0);
		spinnerValor.getValueFactory().setValue(0.0);
		spinnerParcelas.getValueFactory().setValue(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnTutoriais.setGraphic(imgTutoriais);
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,
				Double.MAX_VALUE, 0.0, 0.01);
		SpinnerValueFactory<Double> vlFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE,
				0.0, 0.01);
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

		SpinnerValueFactory<Integer> valueInt = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE,
				0);
		spinnerValor.setValueFactory(valueFactory);
		spinnerValor.getEditor().setTextFormatter(textFormatter);
		spinnerValor.setEditable(true);
		spinnerTaxa.setValueFactory(vlFactory);
		spinnerTaxa.getEditor().setTextFormatter(txtForm);
		spinnerTaxa.setEditable(true);
		valueFactory.valueProperty().bindBidirectional(textFormatter.valueProperty());
		vlFactory.valueProperty().bindBidirectional(txtForm.valueProperty());
		spinnerParcelas.setValueFactory(valueInt);
		spinnerParcelas.setEditable(true);
	}
	
	@FXML
	protected void btnTutoriaisAction(ActionEvent e) {
		Main.mudarTelaDadosInvestimentos("tutoriais", tipoAmort);
	}
}