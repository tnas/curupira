package br.com.tnas.curupira.validation.error;

import br.com.tnas.curupira.validators.InvalidValue;

/**
 * 
 * Representa possíveis erros durante validação de uma inscrição estadual
 * 
 * @author Leonardo Bessa
 */
public enum IEError implements InvalidValue {
    INVALID_CHECK_DIGITS, INVALID_DIGITS, INVALID_FORMAT, UNDEFINED_STATE
}
