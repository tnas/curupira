package br.com.tnas.curupira.validators.rules;

import java.util.function.UnaryOperator;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validation.error.ValidationError;

public class CheckDigitsRule extends ValidationRule {

    private final UnaryOperator<String> digitsCalculator;

    public CheckDigitsRule(Formatter formatter, Validatable validatable, UnaryOperator<String> digitsCalculator) {
        super(formatter, validatable);
        this.digitsCalculator = digitsCalculator;
    }

    @Override
    public boolean validate(String value) {
        var unformattedValue = this.formatter.unformat(value);
        String noDigitsValue = unformattedValue.substring(0, unformattedValue.length() - this.formatter.getNumberOfCheckDigits());
        String checkDigits = unformattedValue.substring(unformattedValue.length() - this.formatter.getNumberOfCheckDigits());
        return checkDigits.equals(this.digitsCalculator.apply(noDigitsValue));
    }
    
    @Override
	public ValidationError getValidationError() {
		return ValidationError.INVALID_CHECK_DIGITS;
	}
}
