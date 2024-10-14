package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

public abstract class ValidationRule {

	protected Formatter formatter;
    protected InvalidValue errorKey;
    private MessageProducer messageProducer;

    protected ValidationRule() {
    	this.messageProducer = new SimpleMessageProducer();
    }
    
    public ValidationMessage getErrorMessage() {
    	return messageProducer.getMessage(this.errorKey);
    }

    public abstract boolean validate(String value);
}
