package negocio.entidades;
import java.util.List;
/**
 * Representa um lembrete ligado a um limite de gasto.
 * Mostra status do limite e percentual de gasto atual.
 * 
 * @author Pedro Farias
 */
public class LembreteLimite extends LembreteBase {
    private static int contadorLimite = 1;
    private Limite limite;

    public LembreteLimite(Limite limite, String descricao) {
        super(descricao, contadorLimite++);
        this.limite = limite;
    }

    public LembreteLimite(int id, int limiteId, String descricao, boolean ativo, List<Limite> limites) {
        super(id, descricao, ativo);
        if (limites != null) {
            this.limite = limites.stream().filter(l -> l.getId() == limiteId).findFirst().orElse(null);
        }
        if (id >= contadorLimite) contadorLimite = id + 1;
    }

    public Limite getLimite() { return limite; }
    public void setLimite(Limite limite) { this.limite = limite; }

    public String toArquivo() {
        int limId = (limite == null ? -1 : limite.getId());
        return id + ";" + limId + ";" + descricao.replace(";", ",") + ";" + ativo;
    }
     
    /**
     * Constrói um LembreteLimite a partir de uma linha do arquivo.
     * 
     * @param linha Linha do arquivo
     * @param limites Lista de limites existentes
     * @return Objeto LembreteLimite
     */
    public static LembreteLimite fromArquivo(String linha, List<Limite> limites) {
        String[] p = linha.split(";", 4);
        if (p.length < 4) throw new IllegalArgumentException("Formato inválido: " + linha);
        int id = Integer.parseInt(p[0].trim());
        int limId = Integer.parseInt(p[1].trim());
        String desc = p[2].trim();
        boolean ativo = Boolean.parseBoolean(p[3].trim());
        return new LembreteLimite(id, limId, desc, ativo, limites);
    }

     /**
     * Retorna uma string formatada indicando o status atual do limite.
     * 
     * @return String com notificação do limite
     */
    @Override
    public String gerarNotificacao() {
        if (limite == null) 
            return "LembreteLimite #" + id + " - (limite não encontrado) | " + descricao;

        double gastoAtual = limite.getTotalGastos();  // pega dinamicamente do limite
        double limiteValor = limite.getValorLimite();
        String cat = (limite.getCategoria() == null ? "(sem categoria)" : limite.getCategoria().getNome());

        if (limiteValor <= 0) return "Limite inválido para categoria " + cat + ".";

        double percentual = gastoAtual / limiteValor;
        String base;
        if (percentual >= 1.0) {
            base = String.format("Ultrapassou o limite de %s: R$ %.2f / R$ %.2f.", cat, gastoAtual, limiteValor);
        } else if (percentual >= 0.8) {
            base = String.format("%s em %d%% do limite (R$ %.2f / R$ %.2f).", cat, Math.round(percentual * 100), gastoAtual, limiteValor);
        } else {
            base = String.format("%s sob controle: R$ %.2f de R$ %.2f.", cat, gastoAtual, limiteValor);
        }

        return base + " | Status: " + (ativo ? "Ativo" : "Inativo");
    }

    @Override
    public String toString() { return gerarNotificacao(); }
}