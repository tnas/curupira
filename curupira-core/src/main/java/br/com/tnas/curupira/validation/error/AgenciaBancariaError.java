package br.com.tnas.curupira.validation.error;

import br.com.tnas.curupira.validators.InvalidValue;

public enum AgenciaBancariaError implements InvalidValue {
	INVALID_CHECK_DIGIT, CHECK_DIGIT_NOT_FOUND, INVALID_FORMAT
}
