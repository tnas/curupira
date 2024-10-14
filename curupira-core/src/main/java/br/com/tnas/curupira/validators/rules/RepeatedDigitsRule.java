package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validation.error.ValidationError;

public class RepeatedDigitsRule extends ValidationRule {

    private final boolean isIgnoringRepeatedDigits;

    public RepeatedDigitsRule(Formatter formatter, Validatable validatable, boolean isIgnoringRepeatedDigits) {
    	super(formatter, validatable);
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
    }

    @Override
    public boolean validate(String value) {
        return this.isIgnoringRepeatedDigits || this.formatter.unformat(value).chars().distinct().count() > 1;
    }
    
    @Override
	public ValidationError getValidationError() {
		return ValidationError.REPEATED_DIGITS;
	}
}
