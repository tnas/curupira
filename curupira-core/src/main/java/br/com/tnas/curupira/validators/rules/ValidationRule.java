package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.BasicMessageProducer;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validation.error.ValidationError;

public abstract class ValidationRule {

	protected Formatter formatter;
	
    private Validatable validatable;
    private MessageProducer messageProducer;

    protected ValidationRule() {
    	this.messageProducer = new BasicMessageProducer();
    }
    
    protected ValidationRule(Formatter formatter, Validatable validatable) {
    	this();
    	this.formatter = formatter;
    	this.validatable = validatable;
    }
    
    public ValidationMessage getErrorMessage() {
    	return messageProducer.getMessage(this.getValidationError(), this.validatable);
    }
    
    public abstract boolean validate(String value);
    
    public abstract ValidationError getValidationError();
}
