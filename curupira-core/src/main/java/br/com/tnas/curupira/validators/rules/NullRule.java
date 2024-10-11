package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.validation.error.CNPJError;

import java.util.Objects;

public class NullRule implements ValidationRule {

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(CNPJError.INVALID_DIGITS);
    }

    @Override
    public boolean validate(String value) {
        return Objects.nonNull(value);
    }
}
