package application;
import entidades.Investimento;

public class main1 {
    public static void main(String[] args) {
        Investimento teste1 = new Investimento(100, 1, 0, 10);
        System.out.println(teste1.getMontante());
    }
}
