package main;
import Excecoes.TIRImpossivelException;
import Excecoes.ValorInvalidoException;
import entidades.Amortizacao;
import entidades.Investimento;
import entidades.ValorPresenteLiquido;
import entidades.VariacaoDePreco;
import entidades.tiposamortizacao.AmortConstante;
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
        

        Investimento apo1 = new AportesPeriodicos(100, 1, 0, 10);
        apo1.calcularMontante();
        System.out.println(apo1.getMontante());
        

        Investimento teste2 = new JurosComposto(100, 1,  0, 10.5);
        teste2.calcularMontante();
        System.out.println(teste2.getMontante());

        Investimento teste1 = new JurosSimples(100, 1, 0, 10);
        teste1.calcularMontante();
        System.out.println(teste1.getMontante());

        try{
            VariacaoDePreco teste3 = new VariacaoDePreco(2, 2);
        } catch (ValorInvalidoException e){
            System.out.println(e.getMessage());
        }


        ValorPresenteLiquido cat = new ValorPresenteLiquido(100000, 5, 0, 3);
        try {
            cat.calcularTIR();
            System.out.println(cat.getTir()*100);
        } catch(TIRImpossivelException e){
            System.out.println(e.getMessage());
        }
        
        cat.calcularVPL();
        System.out.println(cat.getVpl());
        */
        try{
            Amortizacao testee = new AmortConstante(3000, 1, 5);
            testee.calcularTudo();
            for (int i = 0; i < 5; i++) {
                System.out.print(testee.getAmortizacao().get(i));
                System.out.print(" "+testee.getParcela().get(i));
                System.out.println(" "+testee.getJuros().get(i));
            }
        } catch (ValorInvalidoException e){
            System.out.println(e.getMessage());
        }

    }
}
