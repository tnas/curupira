package br.com.tnas.curupira.validator.rule;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.SimpleMessageProducer;
import br.com.tnas.curupira.message.ValidationMessage;
import br.com.tnas.curupira.validator.error.InvalidValue;

public abstract class ValidationRule {

	protected Formatter formatter;
	
    private InvalidValue invalidValue;
    private MessageProducer messageProducer;

    protected ValidationRule(InvalidValue invalidValue) {
    	this.invalidValue = invalidValue;
    	this.messageProducer = new SimpleMessageProducer();
    }
    
    protected ValidationRule(Formatter formatter, InvalidValue invalidValue) {
    	this(invalidValue);
    	this.formatter = formatter;
    }
    
    public ValidationMessage getErrorMessage() {
    	return messageProducer.getMessage(this.invalidValue);
    }
    
    public abstract boolean validate(String value);
}
