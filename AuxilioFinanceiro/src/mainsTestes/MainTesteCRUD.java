package mainsTestes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import dados.ArquivoGastosManager;
import negocio.entidades.Categoria;
import negocio.entidades.Gastos;

public class MainTesteCRUD {
    public static void main(String[] args) {
        try {
            // ---- TESTE DE EDIÇÃO ----
            System.out.println("\n=== TESTANDO EDIÇÃO ===");
            
            // 1. Lista gastos antes da edição
            System.out.println("\nGastos ANTES da edição:");
            List<Gastos> gastosAntes = ArquivoGastosManager.listar();
            gastosAntes.forEach(System.out::println);

            // 2. Edita o gasto com ID 1 (mudando nome e valor)
            boolean editou = ArquivoGastosManager.editarCampos(
                1,                         // ID do gasto a editar
                "Almoço Atualizado",        // Novo nome
                25.00,                     // Novo valor
                LocalDate.now(),           // Nova data (hoje)
                new Categoria(1, "Alimentação") // Nova categoria
            );

            if (editou) {
                System.out.println("\nGasto editado com sucesso!");
            } else {
                System.out.println("\nGasto não encontrado para edição.");
            }

            // 3. Lista gastos após edição
            System.out.println("\nGastos DEPOIS da edição:");
            List<Gastos> gastosDepois = ArquivoGastosManager.listar();
            gastosDepois.forEach(System.out::println);

            // ---- TESTE DE EXCLUSÃO ----
            System.out.println("\n=== TESTANDO EXCLUSÃO ===");
            
            // 1. Remove o gasto com ID 2
            boolean removeu = ArquivoGastosManager.removerPorId(2);
            
            if (removeu) {
                System.out.println("\nGasto removido com sucesso!");
            } else {
                System.out.println("\nGasto não encontrado para exclusão.");
            }

            // 2. Lista gastos após exclusão
            System.out.println("\nGastos DEPOIS da exclusão:");
            List<Gastos> gastosFinais = ArquivoGastosManager.listar();
            gastosFinais.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Erro durante operação CRUD: " + e.getMessage());
        }
    }
}