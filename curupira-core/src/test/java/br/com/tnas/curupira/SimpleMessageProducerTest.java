package br.com.tnas.curupira;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.message.SimpleMessageProducer;
import br.com.tnas.curupira.message.ValidationMessage;
import br.com.tnas.curupira.validator.error.InvalidValue;

public class SimpleMessageProducerTest {

    private enum DocumentoError implements InvalidValue {
        INVALID_DIGITS, INVALID_CHECK_DIGITS;
    }

    @Test
    public void testGetMessage() {
        SimpleMessageProducer messageProducer = new SimpleMessageProducer();
        ValidationMessage message = messageProducer.getMessage(DocumentoError.INVALID_CHECK_DIGITS);
        assertEquals("INVALID CHECK DIGITS", message.getMessage());
    }

}
