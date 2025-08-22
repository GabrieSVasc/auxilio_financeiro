package negocio.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import exceptions.CampoVazioException;

/**
 * Representa um gasto.
 * Contém informações como nome, valor, data e categoria.
 */
public class Gasto{
    // Contador estático para gerar IDs únicos para cada gasto.
    private static int contadorId = 1;
    protected int id;
    private String nome;
    private double valor;
    private LocalDate data;
    // A categoria é final, não pode ser alterada após a criação.
    private final Categoria categoria;
    // Formatador de data padrão para a classe.
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Construtor para criar um gasto com data do tipo LocalDate.
     * @param nome A descrição do gasto.
     * @param valor O valor do gasto.
     * @param categoria A categoria do gasto.
     * @param data A data do gasto.
     * @throws CampoVazioException Se a categoria for nula.
     */
    public Gasto(String nome, double valor, Categoria categoria, LocalDate data) throws CampoVazioException {
        if (categoria == null) {
            throw new CampoVazioException("Categoria do gasto");
        }
        this.id = contadorId++;
        setNome(nome);
        setValor(valor);
        this.data = data;
        this.categoria = categoria;
    }

    /**
     * Construtor para criar um gasto com a data como String.
     * @param nome A descrição do gasto.
     * @param valor O valor do gasto.
     * @param data A data em formato String "dd-MM-yyyy".
     * @param categoria A categoria do gasto.
     * @throws CampoVazioException Se a categoria for nula ou o nome for vazio.
     */
    public Gasto(String nome, double valor, String data, Categoria categoria) throws CampoVazioException {
        this(nome, valor, categoria, LocalDate.parse(data, DATE_FORMATTER));
    }

    // Métodos de acesso (getters) para os atributos.
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public Categoria getCategoria() { return categoria; }
    public static DateTimeFormatter getDateFormatter() { return DATE_FORMATTER; }

    /**
     * Define o nome do gasto, com validação.
     * @param nome O novo nome.
     * @throws CampoVazioException Se o nome for nulo ou vazio.
     */
    public void setNome(String nome) throws CampoVazioException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new CampoVazioException("Nome do gasto");
        }
        this.nome = nome.trim();
    }

    /**
     * Define o valor do gasto, com validação.
     * @param valor O novo valor.
     * @throws IllegalArgumentException Se o valor for menor ou igual a zero.
     */
    public void setValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do gasto deve ser maior que zero");
        }
        this.valor = valor;
    }

    /**
     * Define a data do gasto.
     * @param data A nova data.
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Converte o objeto para uma string formatada para salvar em arquivo.
     * @return String no formato "id;nome;valor;data;categoria".
     */
    public String toFileString() {
        return String.join(";",
            String.valueOf(id),
            nome.replace(";", ","),
            String.format("%.2f", valor),
            data.format(DATE_FORMATTER),
            categoria.getNome().replace(";", ",")
        );
    }

    /**
     * Método estático para criar um objeto Gasto a partir de uma linha de arquivo.
     * @param linha A linha de texto do arquivo.
     * @return O objeto Gasto reconstruído.
     */
    public static Gasto fromFileString(String linha) {
        String[] partes = linha.split(";", 5);
        if (partes.length != 5) {
            throw new IllegalArgumentException("Formato de linha inválido");
        }
        Gasto gasto = null;
        try {
            int id = Integer.parseInt(partes[0].trim());
            String nome = partes[1].trim();
            double valor = Double.parseDouble(partes[2].trim().replace(",", "."));
            String dataStr = partes[3].trim();
            Categoria categoria = new Categoria(partes[4].trim());

            gasto = new Gasto(nome, valor, categoria, LocalDate.parse(dataStr, DATE_FORMATTER));
            gasto.id = id;
            if (id >= contadorId) {
                contadorId = id + 1;
            }
            return gasto;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor numérico inválido: " + e.getMessage());
        } catch (CampoVazioException e) {
            throw new IllegalArgumentException("Dados inválidos: " + e.getMessage());
        } catch (negocio.exceptions.CampoVazioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gasto;
    }

    /**
     * Reseta o contador de IDs. Útil para testes.
     */
    public static void resetarContador() {
        contadorId = 1;
    }

    /**
     * Sobrescreve toString() para uma representação amigável do objeto.
     * @return Uma string formatada com os detalhes do gasto.
     */
    @Override
    public String toString() {
        return String.format("Gasto #%d - %s (R$ %.2f em %s) - %s",
            id, nome, valor, data.format(DATE_FORMATTER), categoria.getNome());
    }
    
    /**
     * Retorna o nome do gasto como sua descrição.
     * @return O nome do gasto.
     */
    public String getDescricao() {
        return nome;
    }
}