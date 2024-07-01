package br.com.tnas.curupira.validation.error;

import br.com.tnas.curupira.validators.InvalidValue;

/**
 * 
 * Representa possíveis erros do Titulo de Eleitor.
 * 
 * @author Leonardo Bessa
 */
public enum TituloEleitoralError implements InvalidValue {
    INVALID_CHECK_DIGITS, INVALID_FORMAT,INVALID_DIGITS, INVALID_CODIGO_DE_ESTADO
}
