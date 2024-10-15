package br.com.tnas.curupira.validator.rule;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validator.error.InvalidValue;

public class RepeatedDigitsRule extends ValidationRule {

    private final boolean isIgnoringRepeatedDigits;

    public RepeatedDigitsRule(Formatter formatter, InvalidValue invalidValue, boolean isIgnoringRepeatedDigits) {
    	super(formatter, invalidValue);
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
    }

    @Override
    public boolean validate(String value) {
        return this.isIgnoringRepeatedDigits || this.formatter.unformat(value).chars().distinct().count() > 1;
    }
}
