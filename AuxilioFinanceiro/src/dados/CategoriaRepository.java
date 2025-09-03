package dados;

import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Categoria;
import negocio.exceptions.CampoVazioException;
import util.arquivoUtils;

public class CategoriaRepository {
	private List<Categoria> categorias;
	
	public CategoriaRepository() {
		categorias = new ArrayList<Categoria>();
		categorias = carregarCategorias();
	}
	
	public boolean isEmpty() {
		return categorias.isEmpty();
	}
	
	public String listar() {
		String retorno = "";
		for(Categoria c: categorias) {
			retorno = retorno + c.exibir()+"\n";
		}
		return retorno;
	}
	
	public void criar(Categoria c) {
		categorias.add(c);
		salvarTodas();
	}
	
	public void remover(Categoria c) {
		int indice = categorias.indexOf(c);
		if(indice != -1) {
			categorias.remove(c);
			salvarTodas();
		}
	}
	
	public Categoria consultar(int id) {
		return categorias.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
	}
	
	public List<Categoria> getCategorias(){
		return categorias;
	}
	
	public void atualizar(Categoria c, String novoNome) throws CampoVazioException {
		int indice = categorias.indexOf(c);
		if(indice != -1) {
			categorias.get(indice).setNome(novoNome);
			salvarTodas();
		}
	}
	
	public boolean existe(String nome) {
		boolean resultado = false;
		for(Categoria c: categorias) {
			if(c.getNome().equals(nome)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}
	
	private List<Categoria> carregarCategorias() {
		List<String> linhas = arquivoUtils.lerDoArquivo("categorias.txt");

		if (linhas.isEmpty()) {
			// Criar categorias padrão
			String[] padrao = { "Comida", "Transporte", "Lazer", "Aluguel", "Saúde", "Educação", "Mensal"};
			for (String nome : padrao) {
				try {
					categorias.add(new Categoria(nome));
				} catch (CampoVazioException e) {
					System.out.println("Erro ao criar categoria padrão: " + e.getMessage());
				}
			}
			salvarTodas();
			return categorias;
		}

		for (String linha : linhas) {
			try {
				String[] partes = linha.split(";");
				int id = Integer.parseInt(partes[0]);
				String nome = partes[1];
				categorias.add(new Categoria(id, nome));
			} catch (Exception e) {
				System.out.println("Erro ao carregar categoria: " + e.getMessage());
			}
		}
		return categorias;
	}
	private void salvarTodas() {
		List<String> linhas = new ArrayList<>();
		for (Categoria c : categorias) {
			linhas.add(c.getId() + ";" + c.getNome());
		}
		arquivoUtils.salvarListaEmArquivo("categorias.txt", linhas);
	}
}