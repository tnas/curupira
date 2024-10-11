package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

public class RepeatedDigitsRule implements ValidationRule {

    private final Formatter formatter;
    private final boolean isIgnoringRepeatedDigits;
    private final InvalidValue errorKey;

    public RepeatedDigitsRule(Formatter formatter, boolean isIgnoringRepeatedDigits, InvalidValue errorKey) {
        this.formatter = formatter;
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
        this.errorKey = errorKey;
    }

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(this.errorKey);
    }

    @Override
    public boolean validate(String value) {
        return this.isIgnoringRepeatedDigits || this.formatter.unformat(value).chars().distinct().count() > 1;
    }
}
