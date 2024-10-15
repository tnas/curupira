package br.com.tnas.curupira.validator.rule;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.InvalidValue;

public class UnformattingRule extends ValidationRule {

    private final int unformattedSize;

    public UnformattingRule(Formatter formatter, InvalidValue invalidValue) {
    	super(formatter, invalidValue);
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
}
