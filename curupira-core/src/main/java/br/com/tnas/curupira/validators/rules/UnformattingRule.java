package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validation.error.ValidationError;

public class UnformattingRule extends ValidationRule {

    private final int unformattedSize;

    public UnformattingRule(Formatter formatter, Validatable validatable) {
    	super(formatter, validatable);
        this.unformattedSize = this.formatter.getNoCheckDigitsSize() + this.formatter.getNumberOfCheckDigits();
    }

    @Override
    public boolean validate(String value) {
        try {
            var unformattedValue = this.formatter.unformat(value);
            return unformattedValue.length() == this.unformattedSize 
            		&& this.formatter.getUnformattedPattern().matcher(unformattedValue).matches();
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
    
    @Override
	public ValidationError getValidationError() {
		return ValidationError.INVALID_DIGITS;
	}
}
