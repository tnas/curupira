package br.com.tnas.curupira.validators.rules;

import java.util.Objects;

import br.com.tnas.curupira.validation.error.ValidationError;

public class NullRule extends ValidationRule {

    @Override
    public boolean validate(String value) {
        return Objects.nonNull(value);
    }
    
    @Override
	public ValidationError getValidationError() {
		return ValidationError.INVALID_DIGITS;
	}
}
