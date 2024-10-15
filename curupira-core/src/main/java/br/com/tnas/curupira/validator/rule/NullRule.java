package br.com.tnas.curupira.validator.rule;

import java.util.Objects;

import br.com.tnas.curupira.validator.error.ValidationError;

public class NullRule extends ValidationRule {
	
	public NullRule() {
		super(ValidationError.INVALID_DIGITS);
	}
	
    @Override
    public boolean validate(String value) {
        return Objects.nonNull(value);
    }
    
}
