package entidades.tiposinvestimentos;

import entidades.Duracao;
import entidades.Investimento;

public class AportesPeriodicos extends Investimento {
    public AportesPeriodicos(double capital, double taxa, Duracao duracao){
        super(capital, taxa, duracao);
    }
    public AportesPeriodicos(double capital, double taxa, int tipo, double tempo){
        super(capital, taxa, tipo, tempo);
    }
    public AportesPeriodicos(){
        super();
    }

    @Override
    public void calcularMontante(){
        double taxa = getTaxa();
        if (taxa >= 1) taxa = taxa/100;

        double capital = getCapital();
        double tempo = this.getDuracao().getTempo();

        this.montante = (1 + taxa)*capital * (Math.pow(1+taxa, tempo) - 1)/taxa;
    }
}

