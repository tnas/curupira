package br.com.tnas.curupira;

import java.util.Objects;

import br.com.tnas.curupira.validation.error.InvalidValue;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validation.error.ValidationError;

public class BasicMessageProducer implements MessageProducer {

	private static final String MESSAGE_FORMAT = "%sError : %s";
	
	@Override
	public ValidationMessage getMessage(ValidationError error, Validatable validatable) {
		var errorDescription = error.name().replaceAll("_", " ");
		return Objects.isNull(validatable) ? new SimpleValidationMessage(errorDescription) :
				new SimpleValidationMessage(String.format(MESSAGE_FORMAT, validatable.name(), errorDescription));
	}

	@Override
	public ValidationMessage getMessage(InvalidValue invalidValue) {
		throw new UnsupportedOperationException();
	}
}
