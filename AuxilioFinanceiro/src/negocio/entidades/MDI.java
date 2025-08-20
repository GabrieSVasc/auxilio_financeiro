package negocio.entidades;

public class MDI {
	private int input1, input2, tipo;
	private double montante, taxa, numParcelas, valorNominal, tempo, capital, custoInicial;
	private Duracao duracao;
	public MDI(int input1, int input2, int tipo, double montante, double taxa, double numParcelas, double valorNominal,
			double tempo, double capital, double custoInicial, Duracao duracao) {
		super();
		this.input1 = input1;
		this.input2 = input2;
		this.tipo = tipo;
		this.montante = montante;
		this.taxa = taxa;
		this.numParcelas = numParcelas;
		this.valorNominal = valorNominal;
		this.tempo = tempo;
		this.capital = capital;
		this.custoInicial = custoInicial;
		this.duracao = duracao;
	}
	public int getInput1() {
		return input1;
	}
	public int getInput2() {
		return input2;
	}
	public int getTipo() {
		return tipo;
	}
	public double getMontante() {
		return montante;
	}
	public double getTaxa() {
		return taxa;
	}
	public double getNumParcelas() {
		return numParcelas;
	}
	public double getValorNominal() {
		return valorNominal;
	}
	public double getTempo() {
		return tempo;
	}
	public double getCapital() {
		return capital;
	}
	public double getCustoInicial() {
		return custoInicial;
	}
	public Duracao getDuracao() {
		return duracao;
	}
}