package negocio.entidades;

import java.util.ArrayList;

public class RetornoInvestimento {
	private double unico, valorPresente, desconto;
	private ArrayList<ValorAmortizacao>vA;
	
	public RetornoInvestimento(double unico) {
		this.unico = unico;
	}
	
	public RetornoInvestimento(ArrayList<Double> parcela, ArrayList<Double> amortizacao, ArrayList<Double> juros, ArrayList<Double> saldoDevedor) {
		vA = new ArrayList<>();
		vA.add(new ValorAmortizacao(0, 0, 0, saldoDevedor.get(0)));
		saldoDevedor.remove(0);
		saldoDevedor.add(0.0);
		for(int i=0; i<parcela.size(); i++) {
			vA.add(new ValorAmortizacao(parcela.get(i), amortizacao.get(i), juros.get(i), saldoDevedor.get(i)));
		}
	}
	
	public RetornoInvestimento(double valorPresente, double desconto) {
		this.valorPresente = valorPresente;
		this.desconto = desconto;
	}
	
	public double getMontanteCalculado() {
		return unico;
	}
	
	public double getInflacao() {
		return unico;
	}
	
	public double getDeflacao() {
		return unico;
	}
	
	public double getVPL() {
		return unico;
	}
	
	public double getTIR() {
		return unico;
	}

	public double getValorPresente() {
		return valorPresente;
	}

	public double getDesconto() {
		return desconto;
	}

	public ArrayList<ValorAmortizacao> getValorAmortizacao() {
		return vA;
	}
}