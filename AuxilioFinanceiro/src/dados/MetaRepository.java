package dados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import negocio.entidades.Meta;
import util.arquivoUtils;

public class MetaRepository {
	private List<Meta> metas;
    private static final String ARQUIVO = "metas.txt";
    
    public MetaRepository() {
    	metas = new ArrayList<Meta>();
    	metas = carregarMetas();
    }
    
    public boolean isEmpty() {
		return metas.isEmpty();
	}
	
	public String listar() {
		String retorno = "";
		for(Meta m: metas) {
			retorno = retorno + m.toString()+"\n";
		}
		return retorno;
	}
	
	public void criar(Meta m) {
		metas.add(m);
		salvarLista();
	}
	
	public void remover(Meta m) {
		int indice = metas.indexOf(m);
		if(indice != -1) {
			metas.remove(m);
			salvarLista();
		}
	}
	
	public Meta consultar(int id) {
		return metas.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
	}
	
	public List<Meta> getMetas(){
		return metas;
	}
	
	public void atualizar(Meta m, String descricao, double objetivo, double atual, LocalDate data) {
		int indice = metas.indexOf(m);
		if(indice != -1) {
			metas.get(indice).setDescricao(descricao);
			metas.get(indice).setValorObjetivo(objetivo);
			metas.get(indice).setValorAtual(atual);
			metas.get(indice).setDataPrazo(data);
			salvarLista();
		}
	}
	
	public boolean existe(int id) {
		boolean resultado = false;
		for(Meta m: metas) {
			if(m.getId() == id) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}
    
    /** Salva todas as metas no arquivo */
    private void salvarLista() {
        if (metas.isEmpty()) {
            arquivoUtils.salvarEmArquivo(ARQUIVO, "", false);
            return;
        }
        arquivoUtils.salvarEmArquivo(ARQUIVO, metas.get(0).toArquivo(), false);
        for (int i = 1; i < metas.size(); i++) {
            arquivoUtils.salvarEmArquivo(ARQUIVO, metas.get(i).toArquivo(), true);
        }
    }
    
    /** Carrega todas as metas do arquivo */
    private List<Meta> carregarMetas() {
        List<String> linhas = arquivoUtils.lerDoArquivo(ARQUIVO);
        for (String ln : linhas) {
            try {
                metas.add(Meta.fromArquivo(ln));
            } catch (Exception e) {
                System.out.println("Falha ao carregar meta: " + e.getMessage());
            }
        }
        return metas;
    }
}