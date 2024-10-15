package br.com.tnas.curupira.validator.rule;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validator.error.InvalidValue;

public class FormattingRule extends ValidationRule {

    private final boolean isFormatted;

    public FormattingRule(Formatter formatter, InvalidValue invalidValue, boolean isFormatted) {
        super(formatter, invalidValue);
        this.isFormatted = isFormatted;
    }


    @Override
    public boolean validate(String value) {
        return !this.isFormatted || this.formatter.getFormattedPattern().matcher(this.formatter.reformat(value)).matches();
    }

}
