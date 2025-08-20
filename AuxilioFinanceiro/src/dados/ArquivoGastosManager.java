package dados;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Categoria;
import negocio.entidades.Gastos;

public class ArquivoGastosManager {
    private static final String CAMINHO_ARQUIVO = "dados/gastos.txt";

    // Garante que o arquivo exista
    private static void garantirArquivo() {
        try {
            Path path = Paths.get(CAMINHO_ARQUIVO);
            Path dir = path.getParent();
            if (dir != null && !Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo de gastos: " + e.getMessage());
        }
    }

    // Salva toda a lista de gastos no arquivo
    public static void salvarTodos(List<Gastos> lista) {
        garantirArquivo();
        List<String> linhas = new ArrayList<>();
        for (Gastos g : lista) {
            linhas.add(g.toArquivo());
        }
        try {
            Files.write(Paths.get(CAMINHO_ARQUIVO), linhas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar gastos: " + e.getMessage());
        }
    }

    // Carrega todos os gastos do arquivo com a lista de categorias já carregadas
    public static List<Gastos> carregarTodos(List<Categoria> categorias) {
        garantirArquivo();
        List<Gastos> lista = new ArrayList<>();
        try {
            List<String> linhas = Files.readAllLines(Paths.get(CAMINHO_ARQUIVO));
            int maxId = 0;
            for (String linha : linhas) {
                if (linha.isBlank()) continue;
                try {
                    Gastos gasto = Gastos.fromArquivo(linha, categorias);
                    lista.add(gasto);
                    if (gasto.getId() > maxId) maxId = gasto.getId();
                } catch (Exception e) {
                    System.err.println("Erro ao carregar gasto: " + e.getMessage());
                }
            }
            Gastos.ajustarProximoId(maxId + 1);
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de gastos: " + e.getMessage());
        }
        return lista;
    }

    public static void adicionar(Gastos gasto, List<Categoria> categorias) {
        List<Gastos> lista = carregarTodos(categorias);
        lista.add(gasto);
        salvarTodos(lista);
    }

    public static boolean editarCampos(int id, String novoNome, double novoValor, LocalDate novaData, Categoria novaCategoria, List<Categoria> categorias) {
        List<Gastos> lista = carregarTodos(categorias);
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
            salvarTodos(lista);
        }
        return encontrado;
    }

    public static boolean editar(Gastos atualizado, List<Categoria> categorias) {
        if (atualizado == null) return false;

        List<Gastos> lista = carregarTodos(categorias);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                salvarTodos(lista);
                return true;
            }
        }
        return false;
    }

    public static boolean removerPorId(int id, List<Categoria> categorias) {
        List<Gastos> lista = carregarTodos(categorias);
        boolean removido = lista.removeIf(gasto -> gasto.getId() == id);
        if (removido) {
            salvarTodos(lista);
        }
        return removido;
    }

    public static List<Gastos> listar(List<Categoria> categorias) {
        return carregarTodos(categorias);
    }
}