package br.com.tnas.curupira.validation.ie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.ValidationMessage;
import br.com.tnas.curupira.validator.Validator;
import br.com.tnas.curupira.validator.error.InvalidStateException;
import br.com.tnas.curupira.validator.error.ValidationError;

public abstract class IEValidatorTest {
	protected final MessageProducer messageProducer = mock(MessageProducer.class);

	private final String wrongCheckDigitUnformattedIE;

	private final String validUnformattedIE;

	private final String validFormattedIE;

	private final String[] validFormattedValues;

	public IEValidatorTest(String wrongCheckDigitUnformattedIE, String validUnformattedIE,
			String validFormattedIE, String[] validFormattedValues) {
		super();
		this.wrongCheckDigitUnformattedIE = wrongCheckDigitUnformattedIE;
		this.validUnformattedIE = validUnformattedIE;
		this.validFormattedIE = validFormattedIE;
		this.validFormattedValues = validFormattedValues;
	}

	protected abstract Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted);

	@Test
	public void shouldHaveDefaultConstructorThatUsesSimpleMessageProducerAndAssumesThatStringIsFormatted() {
		try {
			getValidator(messageProducer, true).assertValid(validFormattedIE);
		} catch (InvalidStateException ie) {
			String expected = "IEError : INVALID CHECK DIGITS";
			assertEquals(expected, ie.getInvalidMessages().get(0).getMessage());
		} catch (RuntimeException e) {
			fail();
		}
	}

	@Test
	public void shouldNotValidateIEWithInvalidCharacter() {
		Validator<String> validator = getValidator(messageProducer, false);
		try {
			validator.assertValid(validUnformattedIE.replaceFirst(".", "&"));
			fail();
		} catch (InvalidStateException e) {
			assertEquals(1, e.getInvalidMessages().size());
		}

		verify(messageProducer, times(1)).getMessage(ValidationError.IE$INVALID_DIGITS);
	}

	@Test
	public void shouldNotValidateIEWithLessDigitsThanAllowed() {
		Validator<String> validator = getValidator(messageProducer, false);
		try {
			validator.assertValid(validUnformattedIE.replaceFirst(".", ""));
			fail();
		} catch (InvalidStateException e) {
			assertEquals(1, e.getInvalidMessages().size());
		}

		verify(messageProducer, times(1)).getMessage(ValidationError.IE$INVALID_DIGITS);
	}

	@Test
	// IEMatoGrosso, IERioGrandeDoNorte e IEBahia sobreescrevem
	public void shouldNotValidateIEWithMoreDigitsThanAlowed() {
		Validator<String> validator = getValidator(messageProducer, false);

		String value = validUnformattedIE + "5";
		try {
			validator.assertValid(value);
			fail();
		} catch (InvalidStateException e) {
			assertEquals(1, e.getInvalidMessages().size());
		}
		verify(messageProducer, times(1)).getMessage(ValidationError.IE$INVALID_DIGITS);
	}

	@Test
	public void shouldNotValidateIEsWithCheckDigitWrong() {
		Validator<String> validator = getValidator(messageProducer, false);

		String value = wrongCheckDigitUnformattedIE;
		try {
			validator.assertValid(value);
			fail();
		} catch (InvalidStateException e) {
			assertEquals(1, e.getInvalidMessages().size());
		}

		verify(messageProducer, times(1)).getMessage(ValidationError.IE$INVALID_CHECK_DIGITS);
	}

	@Test
	public void shouldValidateValidIE() {
		Validator<String> validator = getValidator(messageProducer, true);

		for (String validValue : validFormattedValues) {
			try {
				validator.assertValid(validValue);
			} catch (InvalidStateException e) {
				fail("IE inválida: " + validValue);
			}
			List<ValidationMessage> errors = validator.invalidMessagesFor(validValue);
			assertTrue(errors.isEmpty());
		}

		verify(messageProducer, never()).getMessage(any(ValidationError.class));

	}

	@Test
	public void shouldValidateValidFormattedNovaIE() {
		Validator<String> validator = getValidator(messageProducer, true);

		String[] validValues = { validFormattedIE };
		for (String validValue : validValues) {
			try {
				validator.assertValid(validValue);
			} catch (InvalidStateException e) {
				fail("IE inválida: " + validValue);
			}
			List<ValidationMessage> errors = validator.invalidMessagesFor(validValue);
			assertTrue(errors.isEmpty());
		}

		verify(messageProducer, never()).getMessage(any(ValidationError.class));
	}

	@Test
	public void shouldValidateNullIE() {
		Validator<String> validator = getValidator(messageProducer, false);
		List<ValidationMessage> errors;
		String value = null;
		try {
			validator.assertValid(value);
		} catch (InvalidStateException e) {
			fail();
		}
		errors = validator.invalidMessagesFor(value);
		assertTrue(errors.isEmpty());

		verify(messageProducer, never()).getMessage(any(ValidationError.class));
	}

	@Test
	public void shouldNotValidateValidUnformattedIE() {
		Validator<String> validator = getValidator(messageProducer, true);

		String value = validFormattedIE.replace('-', ':');
		if(value.equals(validFormattedIE)) {
			value = value.replace('/', ':');
			if(value.equals(validFormattedIE)) {
				value = value.replace('.', ':');
			}
		}
		try {
			validator.assertValid(value);
			fail();
		} catch (InvalidStateException e) {
			assertEquals(1, e.getInvalidMessages().size());
		}

		verify(messageProducer, times(1)).getMessage(ValidationError.IE$INVALID_FORMAT);
	}

    @Test
    public void shouldGenerateValidFormattedIE() {
        final Validator<String> ieValidator = getValidator(messageProducer, true);
        final String generated = ieValidator.generateRandomValid();
        ieValidator.assertValid(generated);
    }

    @Test
    public void shouldGenerateValidUnformattedIE() {
        final Validator<String> ieValidator = getValidator(messageProducer, false);
        final String generated = ieValidator.generateRandomValid();
        ieValidator.assertValid(generated);
    }
}
