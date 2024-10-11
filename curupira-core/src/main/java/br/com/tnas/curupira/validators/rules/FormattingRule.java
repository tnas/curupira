package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

public class FormattingRule implements ValidationRule {

    private final Formatter formatter;
    private final boolean isFormatted;
    private final InvalidValue errorKey;

    public FormattingRule(Formatter formatter, boolean isFormatted, InvalidValue errorKey) {
        this.formatter = formatter;
        this.isFormatted = isFormatted;
        this.errorKey = errorKey;
    }

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(this.errorKey);
    }

    @Override
    public boolean validate(String value) {
        return !this.isFormatted || this.formatter.getFormattedPattern().matcher(this.formatter.reformat(value)).matches();
    }
}
