package utils;

public class ValidarValor {
    public static boolean ehInteiro(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean ehNumerico(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean ehPositivo(double valor){
        if (valor > 0) return true;
        else return false;
    }

    public static boolean opcaoValida(int menor, int maior, int atual){
        if (atual > maior || atual < menor) return false; 
        else return true;
    }
}
