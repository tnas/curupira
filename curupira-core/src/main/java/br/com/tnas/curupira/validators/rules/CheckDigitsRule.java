package br.com.tnas.curupira.validators.rules;

import java.util.function.UnaryOperator;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

public class CheckDigitsRule extends ValidationRule {

    private final UnaryOperator<String> digitsCalculator;

    public CheckDigitsRule(Formatter formatter, UnaryOperator<String> digitsCalculator, InvalidValue errorKey) {
        this.formatter = formatter;
        this.digitsCalculator = digitsCalculator;
        this.errorKey = errorKey;
    }

    @Override
    public boolean validate(String value) {
        var unformattedValue = this.formatter.unformat(value);
        String noDigitsValue = unformattedValue.substring(0, unformattedValue.length() - this.formatter.getNumberOfCheckDigits());
        String checkDigits = unformattedValue.substring(unformattedValue.length() - this.formatter.getNumberOfCheckDigits());
        return checkDigits.equals(this.digitsCalculator.apply(noDigitsValue));
    }
}
