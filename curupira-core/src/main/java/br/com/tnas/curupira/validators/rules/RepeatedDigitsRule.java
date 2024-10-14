package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

public class RepeatedDigitsRule extends ValidationRule {

    private final boolean isIgnoringRepeatedDigits;

    public RepeatedDigitsRule(Formatter formatter, boolean isIgnoringRepeatedDigits, InvalidValue errorKey) {
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
        this.formatter = formatter;
        this.errorKey = errorKey;
    }

    @Override
    public boolean validate(String value) {
        return this.isIgnoringRepeatedDigits || this.formatter.unformat(value).chars().distinct().count() > 1;
    }
}
