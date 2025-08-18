package entidades.tiposVPL;

import entidades.Duracao;
import entidades.ValorPresenteLiquido;

public class TaxaInternaRetorno extends ValorPresenteLiquido {
    public TaxaInternaRetorno(double custoInicial, Duracao duracao) {
        super(custoInicial, 0, duracao);
    }
    public TaxaInternaRetorno(double custoInicial, int tipo, double tempo){
        super(custoInicial, 0, tipo, tempo);
    }
    public TaxaInternaRetorno(){
        super();
    }

    public void calcularTIR() {
        this.taxa = 0.0;
        double tolerancia = 0.0001;
        int numRepeticoes = 1000;

        for (int iter = 0; iter < numRepeticoes; iter++) {
            double vplAtual = -1*custoInicial;
            double derivada = 0;

            for (int t = 0; t < arrecadacao.size(); t++) {
                double ft = arrecadacao.get(t);
                vplAtual += ft / Math.pow(1 + this.taxa, t + 1);
                derivada += - (t + 1) * ft / Math.pow(1 + this.taxa, t + 2);
            }

            if (Math.abs(vplAtual) < tolerancia) {
                return;
            }
            this.taxa = this.taxa - vplAtual / derivada;
        }
        System.out.println("Num deu.");
    }
}
