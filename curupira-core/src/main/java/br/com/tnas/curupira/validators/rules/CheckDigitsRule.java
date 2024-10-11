package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

import java.util.function.UnaryOperator;

public class CheckDigitsRule implements ValidationRule {

    private final Formatter formatter;
    private final int numberOfCheckDigits;
    private final UnaryOperator<String> digitsCalculator;
    private final InvalidValue errorKey;

    public CheckDigitsRule(Formatter formatter, int numberOfCheckDigits, UnaryOperator<String> digitsCalculator, InvalidValue errorKey) {
        this.formatter = formatter;
        this.numberOfCheckDigits = numberOfCheckDigits;
        this.digitsCalculator = digitsCalculator;
        this.errorKey = errorKey;
    }

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(this.errorKey);
    }

    @Override
    public boolean validate(String value) {
        var unformattedValue = this.formatter.unformat(value);
        String noDigitsValue = unformattedValue.substring(0, unformattedValue.length() - this.numberOfCheckDigits);
        String checkDigits = unformattedValue.substring(unformattedValue.length() - this.numberOfCheckDigits);
        return checkDigits.equals(this.digitsCalculator.apply(noDigitsValue));
    }
}
