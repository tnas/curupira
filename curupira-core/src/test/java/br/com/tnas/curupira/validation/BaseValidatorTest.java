package br.com.tnas.curupira.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.ValidationMessage;
import br.com.tnas.curupira.validator.BaseValidator;
import br.com.tnas.curupira.validator.error.InvalidStateException;
import br.com.tnas.curupira.validator.error.InvalidValue;

public class BaseValidatorTest {

    @Test
    public void testGetValidationMessagesT() {
        Mockery mockery = new Mockery();
        final MessageProducer messageProducer = mockery.mock(MessageProducer.class);
        final InvalidValue invalidValue = mockery.mock(InvalidValue.class);
        final ValidationMessage validationMessage = mockery.mock(ValidationMessage.class);

        mockery.checking(new Expectations() {
            {
                exactly(1).of(messageProducer).getMessage(invalidValue);
                will(returnValue(validationMessage));
            }
        });
        BaseValidator validator = new BaseValidator(messageProducer);

        List<InvalidValue> invalidValues = Arrays.asList(invalidValue);
        List<ValidationMessage> actual = validator.generateValidationMessages(invalidValues);
        List<ValidationMessage> expected = new ArrayList<ValidationMessage>();
        expected.add(validationMessage);
        assertEquals(expected, actual);

        mockery.assertIsSatisfied();
    }

    @Test
    public void testAssertValidShouldThrowInvalidStateExpectionWhenComesAnInvalidValue() {
        Mockery mockery = new Mockery();
        final MessageProducer messageProducer = mockery.mock(MessageProducer.class);
        final InvalidValue invalidValue = mockery.mock(InvalidValue.class);
        final ValidationMessage validationMessage = mockery.mock(ValidationMessage.class);

        mockery.checking(new Expectations() {
            {
                exactly(1).of(messageProducer).getMessage(invalidValue);
                will(returnValue(validationMessage));
            }
        });
        BaseValidator validator = new BaseValidator(messageProducer);
        try {
            List<InvalidValue> invalidValues = Arrays.asList(invalidValue);
            validator.assertValid(invalidValues);
            fail();
        } catch (InvalidStateException e) {
            List<ValidationMessage> messages0 = e.getInvalidMessages();
            List<ValidationMessage> messages1 = new ArrayList<ValidationMessage>();
            messages1.add(validationMessage);
            assertEquals(messages0, messages1);
        } catch (Exception e) {
            fail();
        }

        mockery.assertIsSatisfied();
    }

    @Test
    public void testAssertValidShouldNotThrowInvalidStateExpectionWhenValueIsValid() {
        Mockery mockery = new Mockery();
        final MessageProducer messageProducer = mockery.mock(MessageProducer.class);

        mockery.checking(new Expectations());
        BaseValidator validator = new BaseValidator(messageProducer);
        try {
            List<InvalidValue> invalidValues = new ArrayList<InvalidValue>();
            validator.assertValid(invalidValues);
        } catch (InvalidStateException e) {
            fail();
        }

        mockery.assertIsSatisfied();
    }

}
