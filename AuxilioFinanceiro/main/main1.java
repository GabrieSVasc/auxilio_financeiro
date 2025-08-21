package main;
import Excecoes.OpcaoInvalidaException;
import Excecoes.TIRImpossivelException;
import Excecoes.ValorInvalidoException;
import entidades.Amortizacao;
import entidades.Investimento;
import entidades.ValorPresenteLiquido;
import entidades.VariacaoDePreco;
import entidades.tiposamortizacao.AmortConstante;
import entidades.tiposamortizacao.AmortMisto;
import entidades.tiposamortizacao.AmortPrice;
import entidades.tiposdesconto.*;
import entidades.tiposinvestimentos.AportesPeriodicos;
import entidades.tiposinvestimentos.JurosComposto;
import entidades.tiposinvestimentos.JurosSimples;
import java.awt.event.TextListener;

public class main1 {
    public static void main(String[] args){

        // SISTEMA DE AMORTIZAÇÃO:
        try{
            System.out.println("SAC:");
            Amortizacao teste1 = new AmortConstante(3000, 1, 5);
            teste1.calcularTudo();
            System.out.print("------ ------ ------ "+teste1.getSaldoDevedor().get(0)+"\n");
            for (int i = 0; i < 5; i++) {
                System.out.print(teste1.getParcela().get(i));
                System.out.print(" "+teste1.getAmortizacao().get(i));
                System.out.print(" "+teste1.getJuros().get(i));
                System.out.println(" "+teste1.getSaldoDevedor().get(i+1));
            }

            System.out.println("\nPRICE:");
            Amortizacao teste2 = new AmortPrice(3000, 1, 5);
            teste2.calcularTudo();
            System.out.print("------ ------ ------ "+teste2.getSaldoDevedor().get(0)+"\n");
            for (int i = 0; i < 5; i++) {
                System.out.printf("%.2f ", teste2.getParcela().get(i));
                System.out.printf("%.2f ", teste2.getAmortizacao().get(i));
                System.out.printf("%.2f ",teste2.getJuros().get(i));
                System.out.printf("%.2f\n",teste2.getSaldoDevedor().get(i+1));
            }

            System.out.println("\nMISTO:");
            Amortizacao teste3 = new AmortMisto(3000, 1, 5);
            teste3.calcularTudo();
            System.out.print("------ ------ ------ "+teste3.getSaldoDevedor().get(0)+"\n");
            for (int i = 0; i < 5; i++) {
                System.out.printf("%.2f ", teste3.getParcela().get(i));
                System.out.printf("%.2f ", teste3.getAmortizacao().get(i));
                System.out.printf("%.2f ",teste3.getJuros().get(i));
                System.out.printf("%.2f\n",teste3.getSaldoDevedor().get(i+1));
            }

        } catch (ValorInvalidoException e){
            System.out.println(e.getMessage());
        }


        // DESCONTO: 
        try {
            DescontoSimples teste1 = new DescontoSimples(1000, 5, 0, 3);
            System.out.println("\n\nDesconto simples: ");
            teste1.calcularValorPresente();
            teste1.calcularDesconto();
            System.out.printf("Valor original: %.2f\nValor a ser recebido %.0f meses/anos antes do original: %.2f\nValor que será perdido: %.2f\n", teste1.getValorNominal(), teste1.getDuracao().getTempo(), teste1.getValorPresente(), teste1.getDesconto());

            DescontoComposto teste2 = new DescontoComposto(1000, 5, 0, 3);
            System.out.println("\nDesconto composto: ");
            teste2.calcularValorPresente();
            teste2.calcularDesconto();
            System.out.printf("Valor original: %.2f\nValor a ser recebido %.0f meses/anos antes do original: %.2f\nValor que será perdido: %.2f\n", teste2.getValorNominal(), teste2.getDuracao().getTempo(), teste2.getValorPresente(), teste2.getDesconto());

        } catch (ValorInvalidoException | OpcaoInvalidaException e) {
            e.printStackTrace();
        }
    
    
        // INVESITMENTO:
        try{
            System.out.println("\n\nJuros Simples:");
            Investimento teste1 = new JurosSimples(1000, 1, 0, 12);
            teste1.calcularMontante();
            System.out.println("Resultado: "+teste1.getMontante());

            System.out.println("\nJuros compostos:");
            Investimento teste2 = new JurosComposto(1000, 1, 0, 12);
            teste2.calcularMontante();
            System.out.println("Resultado: "+teste2.getMontante());

            System.out.println("\nAportes periódicos:");
            Investimento teste3 = new AportesPeriodicos(100, 1, 0, 12);
            teste3.calcularMontante();
            System.out.println("Resultado: "+teste3.getMontante());

        } catch(ValorInvalidoException | OpcaoInvalidaException e){
            e.printStackTrace();
        }


        // VPL/TIR
        try{
            ValorPresenteLiquido teste = new ValorPresenteLiquido(1000, 5, 0, 5, "100, 300, 400, 500, 600");
            System.out.println("\n\nValor presente líquido: ");
            teste.calcularVPL();
            System.out.println(teste.getVpl());

            System.out.println("\nTaxa interna de retorno: ");
            teste.calcularTIR();
            System.out.println(teste.getTir()*100);

        } catch(ValorInvalidoException | OpcaoInvalidaException | TIRImpossivelException e){
            e.printStackTrace();
        }


        // INFLACAO/DEFLACAO/TAXA REAL
        try{
            System.out.println("\n\nInflação:");
            VariacaoDePreco teste = new VariacaoDePreco(1000, 10.68);
            System.out.println(teste.calcularInflacao());
            
            System.out.println("\nDeflação:");
            System.out.println(teste.calcularDeflacao());
        } catch(ValorInvalidoException e){
            e.printStackTrace();
        }
    }
}
