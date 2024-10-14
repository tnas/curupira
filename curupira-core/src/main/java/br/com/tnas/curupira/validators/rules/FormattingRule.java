package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

public class FormattingRule extends ValidationRule {

    private final boolean isFormatted;

    public FormattingRule(Formatter formatter, boolean isFormatted, InvalidValue errorKey) {
        this.formatter = formatter;
        this.isFormatted = isFormatted;
        this.errorKey = errorKey;
    }


    @Override
    public boolean validate(String value) {
        return !this.isFormatted || this.formatter.getFormattedPattern().matcher(this.formatter.reformat(value)).matches();
    }
}
