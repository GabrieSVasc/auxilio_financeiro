package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.Main;

public class TutoriaisViewController implements Initializable {
	@FXML
	private ImageView imgVoltar;

	@FXML
	private Button btnVoltar;

	@FXML
	private Label lblTitle;

	@FXML
	private Label lblText;

	private String anterior;

	private int input;

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		btnVoltar.setGraphic(imgVoltar);
	}

	public void atualizandoTela(String ant, int input) {
		this.input = input;
		anterior = ant;

		switch (anterior) {
		case "investimento":
			lblTitle.setText("Investimentos");
			lblText.setText(
					"É o tipo de cálculo financeiro mais comum que existe, nele o indivíduo simplesmente irá adicionar um dinheiro e, baseando-se em uma porcentagem por determinado período, será retornado uma quantidade maior de dinheiro.\r\n");
			break;
		case "descontoTitulo":
			lblTitle.setText("Desconto");
			lblText.setText(
					"Supondo um investimento que tenha feito para ser recebido daqui 5 anos, entretanto na metade do período, qualquer que seja o motivo, o investidor está cogitando antecipar o retorno, ou seja, ao invés de receber daqui 5 anos, receber em 2.5, quanto que o investidor receberia? quanto que ele deixaria de ganhar por causa disso? é o que o desconto calcula.");
			break;
		case "amortizacao":
			lblTitle.setText("Amortização");
			lblText.setText(
					"Ao parcelar um pagamento, o indivíduo deve pagar uma quantia todo mês, ou seja, uma parcela mensal. Além disso, acontecerá um juros que será acrescido nessa parcela ou seja, uma quantia a mais que deve ser paga, devido ao parcelamento do pagamento. Entretanto, há diversos métodos para calcular essa parcela mensal, a quantia de juros a ser paga, etc. como os 3 escolhidos para o programa.");
			break;
		case "variacao":
			lblTitle.setText("Variação de preço");
			lblText.setText(
					"Com o tempo, o valor das coisas é alterado naturalmente, seja para valer mais ou valer menos e é o que a função calcula, baseando-se em uma porcentagem, quanto que esse dinheiro está valendo.");
			break;
		case "dadosInvestimento":
			switch (input) {
			case 1:
				lblTitle.setText("Juros Simples");
				lblText.setText(
						"Não é tão usado quanto juros compostos, mas possui uma boa utilidade para prazos mais curtos, por exemplo, juros compostos sempre será menor que juros simples em prazos com menos de uma unidade, como 0,5 meses. Para fazer uso dele, é necessário colocar o dinheiro a ser investido, a taxa de juros e o tempo de duração, em anos ou meses.");
				break;
			case 2:
				lblTitle.setText("Juros Compostos");
				lblText.setText(
						"Método de cálculo de investimento mais utilizado em instituições financeiras, o seu cálculo é feito em cima do montante e não do investimento inicial, diferente do juros simples, então a longo prazo, juros compostos será muito maior que o simples. Para fazer uso dele, é necessário colocar o dinheiro a ser investido, a taxa de juros e o tempo de duração, em anos ou meses.");
				break;
			case 3:
				lblTitle.setText("Aportes Periódicos");
				lblText.setText(
						"Método de cálculo de investimento utilizado para aqueles que não querem investir uma quantidade de dinheiro fixa inicialmente, mas sim aplicar uma certa quantia todo mês durante determinado período, é uma forma mais flexível e segura que as outras e de grande recomendação. Para fazer uso dele, é necessário colocar o dinheiro a ser investido periodicamente (seja anualmente ou mensalmente), a taxa de juros e o tempo de duração, em anos ou meses.");
				break;
			}
			break;
		case "dadosDesconto":
			switch (input) {
			case 1:
				lblTitle.setText("Desconto Simples");
				lblText.setText(
						"Caso o usuário tenha feito um investimento em juros simples, esse deveria ser o método a ser calculado o retorno ou desconto do investimento. Para fazer uso dele, é necessário colocar o valor nominal, isto é, o dinheiro que seria recebido ao fim do investimento, a taxa de desconto do investimento e o tempo que será antecipado (supondo que seja colocado 3 anos, quer dizer que você vai receber o investimento 3 anos antes do esperado)\r\n");
				break;
			case 2:
				lblTitle.setText("Desconto Composto");
				lblText.setText(
						"Caso o usuário tenha feito um investimento em juros compostos, esse deveria ser o método a ser calculado o retorno ou desconto do investimento. Para fazer uso dele, é necessário colocar o valor nominal, isto é, o dinheiro que seria recebido ao fim do investimento, a taxa de desconto do investimento e o tempo que será antecipado (supondo que seja colocado 3 anos, quer dizer que você vai receber o investimento 3 anos antes do esperado)");
				break;
			}
			break;
		case "dadosAmortizacao":
			switch (input) {
			case 1:
				lblTitle.setText("Amortização Price");
				lblText.setText(
						"Método de pagamento mais utilizado no brasil, nele é pago uma quantia por parcela fixa, com os juros em cima dessa parcela sendo reduzidos ao longo do tempo, enquanto a amortização (dinheiro que reduz o saldo da dívida) irá aumentar. É mais simples de calcular por causa das parcelas constantes e dentre os 3 escolhidos é o sistema que, ao final, é pago a maior quantidade de juros. Para fazer uso dele, é necessário colocar o valor total da dívida, a taxa de juros e o número de parcelas que foi escolhido.");
				break;
			case 2:
				lblTitle.setText("Amortização Constante");
				lblText.setText(
						"Neste método é pago uma quantia por parcela decrescente, enquanto a amortização (dinheiro que reduz o saldo da dívida) nunca é alterada. Nesse caso, as parcelas vão começar bem maiores que o método PRICE, mas com o tempo irá reduzir até chegar ao ponto de ser bem menos a ser pago. É o método que, ao final dos pagamentos, é pago a menor quantia de juros. Para fazer uso dele, é necessário colocar o valor total da dívida, a taxa de juros e o número de parcelas que foi escolhido.");
				break;
			case 3:
				lblTitle.setText("Amortização Mista");
				lblText.setText(
						"É o caso menos utilizado, mas é uma mistura entre o método PRICE e a amortização constante. De forma simplória é uma média feita entre ambos os métodos. Consequentemente, não possui parcelas fixas como o PRICE, mas também não possui parcelas altíssimas como o método constante. A ideia é ser mais gradativa a redução, ou seja, ele vai decrescer, assim como o método constante, mas não irá reduzir na mesma velocidade do método constante. Para fazer uso dele, é necessário colocar o valor total da dívida, a taxa de juros e o número de parcelas que foi escolhido.");
				break;
			}
			break;
		case "dadosTaxaInternaRetorno":
			lblTitle.setText("Taxa Interna de Retorno");
			lblText.setText(
					"É uma forma de calcular qual a taxa de juros necessária para que um investimento deixe de dar prejuízo, baseando-se em um custo inicial e expectativa de recebimento, ou seja, é buscado um VPL de no mínimo zero. Pode acontecer de ser impossível ter um retorno baseado nos dados informados, então a TIR seria simplesmente impossível de ser feita. Para fazer uso dele, é necessário colocar o custo inicial do investimento, a duração em meses ou anos e a quantia que é esperado de se receber em cada espaço de tempo, supondo que o tempo seja 5 meses. É necessário colocar 5 valores.\r\n");
			break;
		case "dadosVPLPadrao":
			lblTitle.setText("Valor Presente Líquido");
			lblText.setText(
					"É uma forma de calcular a viabilidade de um investimento. Nele o usuário irá colocar um investimento inicial e qual é a expectativa de receber por cada parcela, a partir disso o calculo irá analisar, baseando-se numa situação definida pelo usuário baseada na taxa de desconto escolhida, se esse investimento vale a pena. Caso o resultado for positivo, quer dizer que sim. Caso seja zero, é indiferente. Caso seja negativo, não vale a pena. Para fazer uso dele, é necessário colocar o custo inicial do investimento, a taxa de desconto, a duração em meses ou anos e a quantia que é esperado de se receber em cada espaço de tempo, supondo que o tempo seja 5 meses. É necessário colocar 5 valores.");
			break;
		case "dadosVariacao":
			switch (input) {
			case 1:
				lblTitle.setText("Inflação");
				lblText.setText(
						"Aqui é o caso de que o dinheiro está se desvalorizando com o passar do tempo, então algo que custava 1000 reais, agora pode custar 1050 reais.");
				break;
			case 2:
				lblTitle.setText("Deflação");
				lblText.setText(
						"Aqui é o caso de que o dinheiro está se valorizando com o passar do tempo, então algo que custava 1000 reais, agora pode custar 950 reais.");
				break;
			}
			break;
		}
	}

	@FXML
	protected void btnVoltarAction(ActionEvent e) {
		if (input == 0) {
			Main.mudarTela(anterior);
		}else {
			Main.mudarTelaDadosInvestimentos(anterior, input);
		}
	}
}