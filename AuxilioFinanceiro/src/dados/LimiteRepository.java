package dados;

import java.util.ArrayList;
import java.util.List;

import negocio.CategoriaManager;
import negocio.entidades.Categoria;
import negocio.entidades.Limite;
import negocio.exceptions.ValorNegativoException;
import util.arquivoUtils;

public class LimiteRepository {
	private List<Limite> limites;
	
	public LimiteRepository(CategoriaManager cm) {
		limites = new ArrayList<Limite>();
		limites = carregarLimites(cm);
	}
	
	public boolean isEmpty() {
		return limites.isEmpty();
	}
	
	public String listar() {
		String retorno = "";
		for(Limite l: limites) {
			retorno = retorno + l.exibir() + "\n";
		}
		return retorno;
	}
	
	public void criar(Limite l) {
		limites.add(l);
		salvarTodos();
	}
	
	public void remover(Limite l) {
		int indice = limites.indexOf(l);
		if(indice != -1) {
			limites.remove(indice);
			salvarTodos();
		}
	}
	
	public Limite consultar(int id) {
		return limites.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
	}
	
	public List<Limite> getLimites(){
		return limites;
	}
	
	public void atualizar(Limite l, double novoValor) throws ValorNegativoException {
		int indice = limites.indexOf(l);
		if(indice != -1) {
			limites.get(indice).setValor(novoValor);
			salvarTodos();
		}
	}
	
	public boolean existe(String cat) {
		boolean resultado = false;
		for(Limite l: limites) {
			if(l.getCategoria().getNome().equals(cat)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}
	
	// Recarrega limites do arquivo
	private List<Limite> carregarLimites(CategoriaManager cm) {
		List<Categoria> categorias = cm.getCategorias();
        List<Limite> limites = new ArrayList<>();
        List<String> linhas = arquivoUtils.lerDoArquivo("limites.txt");

        for (String linha : linhas) {
            try {
                String[] partes = linha.split(";");
                String nomeCategoria = partes[0];
                double valor = Double.parseDouble(partes[1]);

                Categoria cat = categorias.stream()
                        .filter(c -> c.getNome().equals(nomeCategoria))
                        .findFirst().orElse(null);

                if (cat != null) {
                    limites.add(new Limite(cat, valor));
                }
            } catch (Exception e) {
                System.out.println("Erro ao carregar limite: " + e.getMessage());
            }
        }
        return limites;
    }
	
	// Para salvar toda a lista ao editar/deletar
    private void salvarTodos() {
        List<String> linhas = new ArrayList<>();
        for (Limite l : limites) {
            linhas.add(l.getCategoria().getNome() + ";" + l.getValorLimite());
        }
        arquivoUtils.salvarListaEmArquivo("limites.txt", linhas);
    }
}