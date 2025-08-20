package negocio.entidades;
import java.util.ArrayList;
import java.util.List;

import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ValorNegativoException;
import util.arquivoUtils;

/** Representa uma meta financeira. */
public class Meta extends ObjetivoFinanceiro {
    private String descricao;
    private static int contadorMeta = 1; // contador próprio da classe

    public Meta(double valor, String descricao) throws CampoVazioException, ValorNegativoException {
        super(valor, contadorMeta++);
        setDescricao(descricao);
        setValor(valor);
        // Salva no arquivo
        arquivoUtils.salvarEmArquivo("metas.txt", descricao + ";" + valor, true);
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) throws CampoVazioException {
        if (descricao == null || descricao.trim().isEmpty())
            throw new CampoVazioException("Descrição da meta");
        this.descricao = descricao.trim();
    }

    @Override
    public void setValor(double valor) throws ValorNegativoException {
        if (valor <= 0) throw new ValorNegativoException("Valor da meta");
        this.valor = valor;
    }

    @Override
    public String exibir() {
        return "Meta #" + id + " - " + descricao + ": R$" + String.format("%.2f", valor);
    }

    // Recarrega metas do arquivo
    public static List<Meta> carregarMetas() {
        List<Meta> metas = new ArrayList<>();
        List<String> linhas = arquivoUtils.lerDoArquivo("metas.txt");

        for (String linha : linhas) {
            try {
                String[] partes = linha.split(";");
                String descricao = partes[0];
                double valor = Double.parseDouble(partes[1]);
                metas.add(new Meta(valor, descricao));
            } catch (Exception e) {
                System.out.println("Erro ao carregar meta: " + e.getMessage());
            }
        }
        return metas;
    }
}
