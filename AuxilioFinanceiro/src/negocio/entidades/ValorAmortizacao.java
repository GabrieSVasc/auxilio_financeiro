package negocio.entidades;

/**
 * Classe feita para facilitar a criação da tabela gerada ao calcular a amortização
 * 
 * Cada objeto representa uma linha desta tabela
 * 
 * @author Maria Gabriela
 */
public class ValorAmortizacao {
	private double parcela, amortizacao, juros, saldoDevedor;
	
	public ValorAmortizacao(double parcela, double amortizacao, double juros, double saldoDevedor) {
		super();
		this.parcela = parcela;
		this.amortizacao = amortizacao;
		this.juros = juros;
		this.saldoDevedor = saldoDevedor;
	}

	public double getParcela() {
		return parcela;
	}

	public double getAmortizacao() {
		return amortizacao;
	}

	public double getJuros() {
		return juros;
	}

	public double getSaldoDevedor() {
		return saldoDevedor;
	}
}