package br.com.tnas.curupira.validator.rule;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validation.error.ValidationError;

public class FormattingRule extends ValidationRule {

    private final boolean isFormatted;

    public FormattingRule(Formatter formatter, Validatable validatable, boolean isFormatted) {
        super(formatter, validatable);
        this.isFormatted = isFormatted;
    }


    @Override
    public boolean validate(String value) {
        return !this.isFormatted || this.formatter.getFormattedPattern().matcher(this.formatter.reformat(value)).matches();
    }


	@Override
	public ValidationError getValidationError() {
		return ValidationError.INVALID_FORMAT;
	}
}
