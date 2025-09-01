package negocio.entidades;

/**
 * Classe feita para facilitar a parametrização de valores para a simulação de investimentos
 * @author Maria Gabriela
 */

public class Parametros {
	private int input1, input2, tipo, numParcelas;
	private double valor, taxa, tempo;
	private String arrecadacao;

	public Parametros(int input1, int input2, double valor, double taxa, int numParcelas) {
		this.input1 = input1;
		this.input2 = input2;
		this.valor = valor;
		this.taxa = taxa;
		this.numParcelas = numParcelas;
		this.arrecadacao = "";
	}
	
	public Parametros(int input1, int input2, double valor, double taxa, double tempo, int tipo) {
		this.input1 = input1;
		this.input2 = input2;
		this.valor = valor;
		this.taxa = taxa;
		this.tempo = tempo;
		this.tipo = tipo;
		this.arrecadacao = "";
	}
	
	public String getArrecadacao() {
		return arrecadacao;
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
	public double getValor() {
		return valor;
	}
	public double getTaxa() {
		return taxa;
	}
	public int getNumParcelas() {
		return numParcelas;
	}
	public double getTempo() {
		return tempo;
	}
	public void setArrecadacao(String arrecadacao) {
		this.arrecadacao = arrecadacao;
	}
}