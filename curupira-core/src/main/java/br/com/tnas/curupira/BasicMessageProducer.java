package br.com.tnas.curupira;

import java.util.Objects;

import br.com.tnas.curupira.validation.error.InvalidValue;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validation.error.ValidationError;

public class BasicMessageProducer implements MessageProducer {

	private static final String MESSAGE_FORMAT = "%sError : %s";
	
	private MessageProducer simpleMessageProducer;
	
	public BasicMessageProducer() {
		this.simpleMessageProducer = new SimpleMessageProducer();
	}
	
	@Override
	public ValidationMessage getMessage(ValidationError error, Validatable validatable) {
		return Objects.isNull(validatable) ? this.getMessage(error) :
				new SimpleValidationMessage(String.format(MESSAGE_FORMAT, validatable.name(), 
						error.name().replaceAll("_", " ")));
	}
	
	@Override
	public ValidationMessage getMessage(ValidationError invalidValue) {
    	return new SimpleValidationMessage(invalidValue.name().replaceAll("_", " "));
    }

	@Override
	public ValidationMessage getMessage(InvalidValue invalidValue) {
		return this.simpleMessageProducer.getMessage(invalidValue);
	}
}
