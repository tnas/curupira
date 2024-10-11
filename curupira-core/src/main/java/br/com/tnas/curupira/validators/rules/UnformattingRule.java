package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.CNPJError;

public class UnformattingRule implements ValidationRule {

    private final Formatter formatter;
    private final int unformattedSize;
    private final String unformattedPattern;

    public UnformattingRule(Formatter formatter, int unformattedSize, String unformattedPattern) {
        this.formatter = formatter;
        this.unformattedSize = unformattedSize;
        this.unformattedPattern = unformattedPattern;
    }

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(CNPJError.INVALID_DIGITS);
    }

    @Override
    public boolean validate(String value) {
        try {
            var unformattedValue = this.formatter.unformat(value);
            return unformattedValue.length() == this.unformattedSize && unformattedValue.matches(this.unformattedPattern);
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
