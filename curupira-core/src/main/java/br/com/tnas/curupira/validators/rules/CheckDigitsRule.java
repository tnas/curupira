package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.CNPJError;

import java.util.function.UnaryOperator;

public class CheckDigitsRule implements ValidationRule {

    private final Formatter formatter;
    private final int numberOfCheckDigits;
    private final UnaryOperator<String> digitsCalculator;

    public CheckDigitsRule(Formatter formatter, int numberOfCheckDigits, UnaryOperator<String> digitsCalculator) {
        this.formatter = formatter;
        this.numberOfCheckDigits = numberOfCheckDigits;
        this.digitsCalculator = digitsCalculator;
    }

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(CNPJError.INVALID_CHECK_DIGITS);
    }

    @Override
    public boolean validate(String value) {
        var unformattedValue = this.formatter.unformat(value);
        String noDigitsValue = unformattedValue.substring(0, unformattedValue.length() - this.numberOfCheckDigits);
        String checkDigits = unformattedValue.substring(unformattedValue.length() - this.numberOfCheckDigits);
        return checkDigits.equals(this.digitsCalculator.apply(noDigitsValue));
    }
}
