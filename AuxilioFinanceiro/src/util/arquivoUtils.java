package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** Utilitário para criação e leitura de arquivos txt que armazenam os dados criados, sempre em UTF-8 */
public class arquivoUtils {

    private static final String PASTA_DADOS = "C:\\Users\\Usuario\\Documents\\GitHub\\AuxilioFinanceiro\\src/dados/";

    /** Salva conteúdo em arquivo, append=true por padrão */
    public static void salvarEmArquivo(String nomeArquivo, String conteudo) {
        salvarEmArquivo(nomeArquivo, conteudo, true);
    }

    /** Salva conteúdo em arquivo, podendo escolher append */
    public static void salvarEmArquivo(String nomeArquivo, String conteudo, boolean append) {
        criarPastaSeNecessario();

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(PASTA_DADOS + nomeArquivo, append), "UTF-8"))) {
            writer.write(conteudo);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    /** Salva lista inteira no arquivo, sobrescrevendo (append=false) */
    public static void salvarListaEmArquivo(String nomeArquivo, List<String> linhas) {
        criarPastaSeNecessario();

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(PASTA_DADOS + nomeArquivo, false), "UTF-8"))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar lista no arquivo: " + e.getMessage());
        }
    }

    /** Lê todas as linhas de um arquivo UTF-8 */
    public static List<String> lerDoArquivo(String nomeArquivo) {
        List<String> linhas = new ArrayList<>();
        File file = new File(PASTA_DADOS + nomeArquivo);
        if (!file.exists()) return linhas;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha.replace("\uFEFF", "")); // remove BOM se existir
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler do arquivo: " + e.getMessage());
        }
        return linhas;
    }

    /** Cria pasta de dados se não existir */
    private static void criarPastaSeNecessario() {
        File pasta = new File(PASTA_DADOS);
        if (!pasta.exists()) pasta.mkdir();
    }
}
