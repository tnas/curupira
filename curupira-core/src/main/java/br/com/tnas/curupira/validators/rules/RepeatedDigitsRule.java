package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.CNPJError;

public class RepeatedDigitsRule implements ValidationRule {

    private final Formatter formatter;
    private final boolean isIgnoringRepeatedDigits;

    public RepeatedDigitsRule(Formatter formatter, boolean isIgnoringRepeatedDigits) {
        this.formatter = formatter;
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
    }

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(CNPJError.REPEATED_DIGITS);
    }

    @Override
    public boolean validate(String value) {
        return this.isIgnoringRepeatedDigits || this.formatter.unformat(value).chars().distinct().count() > 1;
    }
}
