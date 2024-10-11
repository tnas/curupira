package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.ValidationMessage;

public interface ValidationRule {

    MessageProducer messageProducer = new SimpleMessageProducer();

    ValidationMessage getErrorMessage();

    boolean validate(String value);
}
