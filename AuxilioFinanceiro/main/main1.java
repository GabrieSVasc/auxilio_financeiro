package main;
import entidades.ValorPresenteLiquido;
import entidades.tiposVPL.TaxaInternaRetorno;
import entidades.tiposdesconto.*;
import entidades.tiposinvestimentos.AportesPeriodicos;
import entidades.tiposinvestimentos.JurosComposto;
import entidades.tiposinvestimentos.JurosSimples;

public class main1 {
    public static void main(String[] args){
        /*
        DescontoSimples desc1 = new DescontoSimples(teste1.getMontante(), teste1.getTaxa(), 1, 5);
        desc1.calcularDesconto();
        System.out.println(desc1.getDesconto());

        DescontoComposto desc2 = new DescontoComposto(teste2.getMontante(), teste2.getTaxa(), 1, 5);
        desc2.calcularDesconto();
        System.out.println(desc2.getDesconto());
        System.out.println(desc2.getValorPresente());
        */  
        AportesPeriodicos apo1 = new AportesPeriodicos(100, 1, 0, 10);
        apo1.calcularMontante();
        System.out.println(apo1.getMontante());
        

        JurosComposto teste2 = new JurosComposto(100, 1,  0, 10.5);
        teste2.calcularMontante();
        System.out.println(teste2.getMontante());

        JurosSimples teste1 = new JurosSimples(100, 1, 0, 10);
        teste1.calcularMontante();
        System.out.println(teste1.getMontante());

        TaxaInternaRetorno gato = new TaxaInternaRetorno(1000, 0, 3);
        gato.calcularTIR();
        System.out.printf("%.2f%%\n", gato.getTaxa()*100);

        ValorPresenteLiquido cat = new ValorPresenteLiquido(1000, 5, 0, 3);
        cat.calcularVPL();
        System.out.println(cat.getVpl());
    }
}
