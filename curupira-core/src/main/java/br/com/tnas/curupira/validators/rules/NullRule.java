package br.com.tnas.curupira.validators.rules;

import java.util.Objects;

import br.com.tnas.curupira.validators.InvalidValue;

public class NullRule extends ValidationRule {

	public NullRule(InvalidValue errorKey) {
        this.errorKey = errorKey;
    }
	
    @Override
    public boolean validate(String value) {
        return Objects.nonNull(value);
    }
}
