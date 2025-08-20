package dados;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

import negocio.entidades.Categoria;
import negocio.entidades.Gastos;

public class ArquivoGastosManager {
    private static final String CAMINHO_ARQUIVO = "../dados/gastos.txt";

    private static void garantirArquivo() throws IOException {
        Path path = Paths.get(CAMINHO_ARQUIVO);
        Path dir = path.getParent();
        if (dir != null && !Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }
    
    public static void salvar(List<Gastos> lista) throws IOException {
        garantirArquivo();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO))) {
            for (Gastos gasto : lista) {
                writer.write(gasto.toArquivo());
                writer.newLine();
            }
        }
    }

    public static List<Gastos> carregar() throws IOException {
        garantirArquivo();
        List<Gastos> lista = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
            String linha;
            int maxId = 0;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                try {
                    Gastos gasto = Gastos.fromArquivo(linha); 
                    lista.add(gasto);
                    if (gasto.getId() > maxId) {
                        maxId = gasto.getId();
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao ler gasto: " + linha + " (" + e.getMessage() + ")");
                }
            }
            Gastos.ajustarProximoId(maxId + 1);
        }

        return lista;
    }

    public static void adicionar(Gastos gasto) throws IOException {
        List<Gastos> lista = carregar();
        lista.add(gasto);
        salvar(lista);
    }

    public static boolean editarCampos(int id, String novoNome, double novoValor, LocalDate novaData, Categoria novaCategoria) throws IOException {
        List<Gastos> lista = carregar();
        boolean encontrado = false;

        for (Gastos gasto : lista) {
            if (gasto.getId() == id) {
                if (novoNome != null) gasto.setNome(novoNome);
                if (novoValor != 0) gasto.setValor(novoValor);
                if (novaData != null) gasto.setData(novaData);
                if (novaCategoria != null) gasto.setCategoria(novaCategoria);
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            salvar(lista);
        }
        return encontrado;
    }

    public static boolean editar(Gastos atualizado) throws IOException {
        if (atualizado == null) return false;

        List<Gastos> lista = carregar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                salvar(lista);
                return true;
            }
        }
        return false;
    }

    public static boolean removerPorId(int id) throws IOException {
        List<Gastos> lista = carregar();
        boolean removido = lista.removeIf(gasto -> gasto.getId() == id);
        if (removido) {
            salvar(lista);
        }
        return removido;
    }

    public static List<Gastos> listar() throws IOException {
        return carregar();
    }
}
