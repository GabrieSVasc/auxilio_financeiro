package service;

import java.io.IOException;

import exceptions.CampoVazioException;
import exceptions.ObjetoNaoEncontradoException;
import exceptions.ObjetoNuloException;

/** 
 * Interface que padroniza um menu CRUD no console.
 * 
 * @author Pedro Farias
 */
public interface CrudMenu { void menu() throws CampoVazioException, IOException, ObjetoNuloException, ObjetoNaoEncontradoException; 
}
