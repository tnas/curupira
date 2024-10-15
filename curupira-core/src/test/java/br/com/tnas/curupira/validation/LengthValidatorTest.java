package br.com.tnas.curupira.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleValidationMessage;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.validation.error.InvalidStateException;
import br.com.tnas.curupira.validation.error.ValidationError;
import br.com.tnas.curupira.validator.LengthValidator;

public class LengthValidatorTest {

    @Test
    public void shouldAssertValidCorrectLengthStrings() {
        LengthValidator validator = new LengthValidator(2);
        validator.assertValid("23");
    }

    @Test
    public void shouldNotAssertValidIncorrectLengthStrings() {
        LengthValidator validator = new LengthValidator(2);
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid("230"));
    }

    @Test
    public void shouldAssertValidCorrectLengthIntegers() {
        LengthValidator validator = new LengthValidator(4);
        validator.assertValid(1234);
    }

    @Test
    public void shouldNotAssertValidIncorrectLengthIntegers() {
        LengthValidator validator = new LengthValidator(4);
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid(123));
    }

    @Test
    public void shouldReturnCorrectValidationMessage() {
        Mockery mockery = new Mockery();
        final MessageProducer messageProducer = mockery.mock(MessageProducer.class);
        LengthValidator lengthValidator = new LengthValidator(messageProducer, 2);
        String message = "O tamanho da entrada é inválido.";
        final ValidationMessage validationMessage = new SimpleValidationMessage(message);
        mockery.checking(new Expectations() {
            {
                oneOf(messageProducer).getMessage(with(equal(ValidationError.Length$INVALID_LENGTH)));
                will(returnValue(validationMessage));
            }
        });
        List<ValidationMessage> invalidMessages = lengthValidator.invalidMessagesFor(12345);
        assertEquals(1, invalidMessages.size());
        assertEquals(message, invalidMessages.get(0).getMessage());
    }

    @Test
    public void shouldReturnPrettySimpleMessageValidation() {
        LengthValidator lengthValidator = new LengthValidator(2);
        List<ValidationMessage> invalidMessages = lengthValidator.invalidMessagesFor(12345);
        assertEquals(1, invalidMessages.size());
        assertEquals("LengthError : INVALID LENGTH", invalidMessages.get(0).getMessage());
    }
    
    @Test
    public void shouldThrowExceptionWhenGeneratingRandomValue() {
    	Assertions.assertThrows(UnsupportedOperationException.class, () -> new LengthValidator(2).generateRandomValid());
    }
}
