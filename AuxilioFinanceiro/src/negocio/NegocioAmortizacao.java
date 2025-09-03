package negocio;

import negocio.entidades.Amortizacao;
import negocio.entidades.RetornoInvestimento;
import negocio.entidades.tiposamortizacao.AmortConstante;
import negocio.entidades.tiposamortizacao.AmortMisto;
import negocio.entidades.tiposamortizacao.AmortPrice;
import negocio.exceptions.OpcaoInvalidaException;
import negocio.exceptions.ValorInvalidoException;

/**
 * Menu que lida com a parte de amortização
 * 
 * @author Divancy Bruno
 */

public class NegocioAmortizacao {
	public RetornoInvestimento inputMenuAmortizacao(int input2, double montante, double taxa, int numParcelas)
			throws OpcaoInvalidaException, ValorInvalidoException {
		Amortizacao amort;
		switch (input2) {
		case 1: {
			amort = new AmortPrice(montante, taxa, numParcelas);
			break;
		}
		case 2: {
			amort = new AmortConstante(montante, taxa, numParcelas);
			break;
		}
		case 3: {
			amort = new AmortMisto(montante, taxa, numParcelas);
			break;
		}
		default:
			throw new OpcaoInvalidaException();
		}
		amort.calcularTudo();
		RetornoInvestimento r = new RetornoInvestimento(amort.getParcela(), amort.getAmortizacao(), amort.getJuros(),
				amort.getSaldoDevedor());
		return r;
	}
}
