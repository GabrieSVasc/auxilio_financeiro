package util;
import java.util.Scanner;
/** Utilitário para leitura segura no console. */
public final class ConsoleIO {
    private ConsoleIO() {}
    public static String readOption(Scanner sc, String prompt, String regexValid) {
        while (true) {
            System.out.print(prompt);
            String op = sc.nextLine().trim();
            if (op.matches(regexValid)) return op;
            System.out.println("Opção inválida. Tente novamente.");
        }
    }
    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Valor inválido. Digite um número inteiro."); }
        }
    }
    public static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Double.parseDouble(s.replace(",", ".")); }
            catch (NumberFormatException e) { System.out.println("Valor inválido. Digite um número."); }
        }
    }
    public static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Entrada vazia. Tente novamente.");
        }
    }
}
