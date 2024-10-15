package br.com.tnas.curupira;

import br.com.tnas.curupira.validation.error.InvalidValue;

public class BasicMessageProducer implements MessageProducer {

	private static final String MESSAGE_FORMAT = "%sError : %s";
	
	@Override
	public ValidationMessage getMessage(InvalidValue error) {
		
		var messageComponents = error.name().split("\\$");
		
		return messageComponents.length == 1 ? new SimpleValidationMessage(messageComponents[0].replaceAll("_", " ")) :
				new SimpleValidationMessage(String.format(MESSAGE_FORMAT, messageComponents[0], 
						messageComponents[1].replaceAll("_", " ")));
	}
}
