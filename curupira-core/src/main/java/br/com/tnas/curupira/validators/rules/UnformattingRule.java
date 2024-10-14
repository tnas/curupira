package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

public class UnformattingRule extends ValidationRule {

    private final int unformattedSize;

    public UnformattingRule(Formatter formatter, InvalidValue errorKey) {
        this.formatter = formatter;
        this.unformattedSize = this.formatter.getNoCheckDigitsSize() + this.formatter.getNumberOfCheckDigits();
        this.errorKey = errorKey;
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
