package negocio;

import java.io.IOException;

import negocio.exceptions.CampoVazioException;
import negocio.exceptions.ObjetoNaoEncontradoException;
import negocio.exceptions.ObjetoNuloException;

/** 
 * Interface que padroniza um menu CRUD no console.
 * 
 * @author Pedro Farias
 */
public interface CrudMenu { void menu() throws CampoVazioException, IOException, ObjetoNuloException, ObjetoNaoEncontradoException; 
}