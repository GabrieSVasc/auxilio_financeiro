package util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
/** 
 * Utilitário para leitura segura no console.
 * 
 * @author Pedro Farias 
 */
public final class ConsoleIO {
    private ConsoleIO() {}

    /**
     * Lê uma opção do usuário e valida com uma expressão regular.
     * 
     * @param sc Scanner para leitura do console
     * @param prompt mensagem exibida ao usuário
     * @param regexValid expressão regular que a entrada deve corresponder
     * @return string válida digitada pelo usuário
     */
    public static String readOption(Scanner sc, String prompt, String regexValid) {
        while (true) {
            System.out.print(prompt);
            String op = sc.nextLine().trim();
            if (op.matches(regexValid)) return op;
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    /**
     * Lê um número inteiro do usuário.
     * Repetirá a solicitação até que uma entrada válida seja fornecida.
     * 
     * @param sc Scanner para leitura do console
     * @param prompt mensagem exibida ao usuário
     * @return valor inteiro digitado
     */
    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Valor inválido. Digite um número inteiro."); }
        }
    }

     /**
     * Lê um número decimal (double) do usuário.
     * Substitui vírgula por ponto e valida a entrada.
     *
     */
    public static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Double.parseDouble(s.replace(",", ".")); }
            catch (NumberFormatException e) { System.out.println("Valor inválido. Digite um número."); }
        }
    }

    /**
     * Lê uma string não vazia do usuário.
     * Repetirá a solicitação até que uma entrada válida seja fornecida.
     *
     */  
    public static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Entrada vazia. Tente novamente.");
        }
    }

     /**
     * Lê uma data no formato "dd-MM-yyyy" ou permite entrada vazia.
     *
     */ 
    public static LocalDate readLocalDate(Scanner sc, String msg) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    while (true) {
        System.out.print(msg);
        String input = sc.nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return LocalDate.parse(input, fmt);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido. Use dd-MM-yyyy ou deixe vazio.");
        }
    }
    }
}