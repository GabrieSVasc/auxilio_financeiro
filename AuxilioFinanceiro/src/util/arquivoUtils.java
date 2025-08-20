package util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/* Utilitário para criação e leitura de arquivos txt que armazenam os dados criados*/
public class arquivoUtils {

    private static final String PASTA_DADOS = ".\\src/files/";

    // Salvar com append = true por padrão
    public static void salvarEmArquivo(String nomeArquivo, String conteudo) {
        salvarEmArquivo(nomeArquivo, conteudo, true);
    }

    public static void salvarEmArquivo(String nomeArquivo, String conteudo, boolean append) {
        // Cria a pasta caso não exista
        File pasta = new File(PASTA_DADOS);
        if (!pasta.exists()) pasta.mkdir();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASTA_DADOS + nomeArquivo, append))) {
            writer.write(conteudo);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    public static List<String> lerDoArquivo(String nomeArquivo) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PASTA_DADOS + nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler do arquivo: " + e.getMessage());
        }
        return linhas;
    }
}
