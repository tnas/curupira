package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.validation.error.CNPJError;

import java.util.regex.Pattern;

public class FormattingRule implements ValidationRule {

    private final boolean isFormatted;
    private final Pattern formattedPattern;

    public FormattingRule(boolean isFormatted, Pattern formattedPattern) {
        this.isFormatted = isFormatted;
        this.formattedPattern = formattedPattern;
    }

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(CNPJError.INVALID_FORMAT);
    }

    @Override
    public boolean validate(String value) {
        return this.isFormatted == this.formattedPattern.matcher(value).matches();
    }
}
