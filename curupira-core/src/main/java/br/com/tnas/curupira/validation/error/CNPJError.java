package br.com.tnas.curupira.validation.error;

import br.com.tnas.curupira.validators.InvalidValue;

/**
 * 
 * Representa possíveis erros do CNPJ
 * 
 * @author Leonardo Bessa
 * @author Fabio Kung
 */
public enum CNPJError implements InvalidValue {
    INVALID_CHECK_DIGITS, INVALID_DIGITS, REPEATED_DIGITS, INVALID_FORMAT
}
