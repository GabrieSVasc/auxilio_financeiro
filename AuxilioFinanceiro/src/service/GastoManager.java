package service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.CampoVazioException;
import negocio.entidades.Categoria;
import negocio.entidades.Gasto;
import negocio.exceptions.MesSemGastosException;
import repository.GastoRepository;

/**
 * Classe de serviço que gerencia as operações de negócios para os gastos.
 * Interage com o GastoRepository para persistir e recuperar dados.
 */
public class GastoManager {
    /**
     * Adiciona um novo gasto, carregando todos os gastos existentes e salvando a lista atualizada.
     * @param gasto O objeto Gasto a ser adicionado.
     */
    public void adicionarGasto(Gasto gasto) throws IOException, CampoVazioException {
        List<Gasto> gastos = GastoRepository.carregar();
        gastos.add(gasto);
        GastoRepository.salvar(gastos);
    }

    /**
     * Edita um gasto existente com base no ID.
     * Cria um novo objeto Gasto se a categoria for alterada, ou atualiza os setters se não for.
     * @return true se o gasto foi encontrado e editado, false caso contrário.
     */
    public boolean editarGasto(int id, String novoNome, Double novoValor, LocalDate novaData, Categoria novaCategoria) 
            throws IOException, CampoVazioException {
        
        List<Gasto> gastos = GastoRepository.carregar();
        boolean encontrado = false;

        for (Gasto gasto : gastos) {
            if (gasto.getId() == id) {
                // Lógica para substituir o objeto se a categoria for alterada (já que a categoria é final).
                if (novaCategoria != null) {
                    Gasto novoGasto = new Gasto(
                        novoNome != null ? novoNome : gasto.getNome(),
                        novoValor != null ? novoValor : gasto.getValor(),
                        novaData != null ? novaData.format(Gasto.getDateFormatter()) : gasto.getData().format(Gasto.getDateFormatter()),
                        novaCategoria
                    );
                    gastos.set(gastos.indexOf(gasto), novoGasto);
                } else {
                    // Atualiza os atributos usando os setters se a categoria não mudar.
                    if (novoNome != null) gasto.setNome(novoNome);
                    if (novoValor != null) gasto.setValor(novoValor);
                    if (novaData != null) gasto.setData(novaData);
                }
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            GastoRepository.salvar(gastos);
        }
        return encontrado;
    }

    /**
     * Remove um gasto do repositório com base no ID.
     * @param id O ID do gasto a ser removido.
     * @return true se o gasto foi removido, false caso contrário.
     */
    public boolean removerGasto(int id) throws IOException {
        List<Gasto> gastos = GastoRepository.carregar();
        boolean removido = gastos.removeIf(gasto -> gasto.getId() == id);
        
        if (removido) {
            GastoRepository.salvar(gastos);
        }
        return removido;
    }

    /**
     * Carrega e retorna a lista completa de gastos do repositório.
     * @return Uma lista de objetos Gasto.
     */
    public List<Gasto> listarGastos() throws IOException {
        return GastoRepository.carregar();
    }
    
    public ArrayList<Gasto> getGastoByMes(int m, int a) throws IOException{
    	List<Gasto> gastos = GastoRepository.carregar();
    	ArrayList<Gasto> gastosMes= new ArrayList<Gasto>();
    	for(Gasto g: gastos) {
    		if(g.getData().getMonthValue() == m && g.getData().getYear() == a) {
    			gastosMes.add(g);
    		}
    	}
    	return gastosMes;
    }
    
    //Buscando entre os gastos aqueles associados a uma categoria específica em um mês de um ano específico
    public ArrayList<Gasto> getGastosByCategoria(Categoria c, int m, int a) throws IOException, MesSemGastosException, CategoriaSemGastosException{
    	List<Gasto> gastos=this.getGastoByMes(m, a);
    	if(!categoriaTemGastos(c)) {
    		throw new CategoriaSemGastosException();
    	}
    	if(gastos.size()==0) {
    		throw new MesSemGastosException();
    	}
    	ArrayList<Gasto> gastosCategoria = new ArrayList<Gasto>();
    	for(Gasto g: gastos) {
    		if(g.getCategoria().getNome().equals(c.getNome()) && g.getData().getMonthValue()==m && g.getData().getYear()==a) {
    			gastosCategoria.add(g);
    		}
    	}
    	return gastosCategoria;
    }
    
    public boolean categoriaTemGastos(Categoria c) throws IOException {
    	List<Gasto> gastos = GastoRepository.carregar();
    	boolean tem=false;
    	for(Gasto g: gastos) {
    		if(g.getCategoria().getNome().equals(c.getNome())) {
    			tem = true;
    			break;
    		}
    	}
    	return tem;
    }

}