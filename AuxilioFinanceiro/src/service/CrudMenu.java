package service;

import exceptions.CampoVazioException;

/** 
 * Interface que padroniza um menu CRUD no console.
 * 
 * @author Pedro Farias
 */
public interface CrudMenu { void menu() throws CampoVazioException; 
}
