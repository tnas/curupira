package br.com.tnas.curupira.validation.error;

import br.com.tnas.curupira.validators.InvalidValue;

/**
 * Representa possíveis erros na validação do Renavam
 * 
 * @author Rafael Carvalho
 */
public enum RenavamError implements InvalidValue {

    INVALID_FORMAT, INVALID_CHECK_DIGIT, INVALID_DIGITS
}
