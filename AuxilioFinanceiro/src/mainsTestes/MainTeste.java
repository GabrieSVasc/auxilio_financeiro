package mainsTestes;
import java.util.ArrayList;
import java.util.List;

import dados.ArquivoGastosManager;
import negocio.entidades.Categoria;
import negocio.entidades.Gastos;
import negocio.exceptions.CampoVazioException;

public class MainTeste {
    public static void main(String[] args) throws CampoVazioException {
        List<Gastos> listaGastos = new ArrayList<>();
        List<Categoria> listaCategorias = new ArrayList<>();

        // Criando categorias
        Categoria alimentacao = new Categoria("Alimentação");
        Categoria transporte = new Categoria("Transporte");
        listaCategorias.add(transporte);
        listaCategorias.add(alimentacao);

        // Criando gastos
        Gastos g1 = new Gastos("Almoço", 20.00, "21-11-2022", alimentacao);
        Gastos g2 = new Gastos("Uber", 15.00, "20-12-2012", transporte);

        // Adicionando à lista
        listaGastos.add(g1);
        listaGastos.add(g2);

        // Imprimindo lista
        System.out.println("Lista de Gastos Inicial:");
        for (Gastos g : listaGastos) {
            System.out.println(g);
        }

        ArquivoGastosManager.salvarTodos(listaGastos);
		System.out.println("Gastos salvos no arquivo!");

        // Lendo do arquivo
        try {
            List<Gastos> listaDoArquivo = ArquivoGastosManager.carregarTodos(listaCategorias);
            System.out.println("\nLista de Gastos do Arquivo:");
            for (Gastos g : listaDoArquivo) {
                System.out.println(g);
            }
        } catch (Exception e) {
            System.out.println("nd");
        }
    }
}