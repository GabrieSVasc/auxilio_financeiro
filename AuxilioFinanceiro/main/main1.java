package main;
import entidades.Desconto;
import entidades.Investimento;
import entidades.tiposdesconto.*;
import entidades.tiposinvestimentos.JurosComposto;
import entidades.tiposinvestimentos.JurosSimples;

public class main1 {
    public static void main(String[] args) {
        JurosSimples teste1 = new JurosSimples(100, 1, 1, 10);
        teste1.calcularMontante();
        System.out.println(teste1.getMontante());

        JurosComposto teste2 = new JurosComposto(100, 1, 1, 10.5);
        teste2.calcularMontante();
        System.out.println(teste2.getMontante());

        DescontoSimples desc1 = new DescontoSimples(teste1.getMontante(), teste1.getTaxa(), 1, 5);
        desc1.calcularDesconto();
        System.out.println(desc1.getDesconto());
    }
}
