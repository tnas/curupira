package br.com.tnas.curupira.validation.error;

import br.com.tnas.curupira.validators.InvalidValue;

/**
 * Representa possíveis erros na validação do CPF
 * 
 * @author Leonardo Bessa
 * @author Fabio Kung
 */
public enum CPFError implements InvalidValue {
    INVALID_CHECK_DIGITS, INVALID_DIGITS, REPEATED_DIGITS, INVALID_FORMAT
}
