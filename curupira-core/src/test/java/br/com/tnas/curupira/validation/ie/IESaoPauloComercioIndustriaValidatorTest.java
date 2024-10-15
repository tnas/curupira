package br.com.tnas.curupira.validation.ie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.validation.error.InvalidStateException;
import br.com.tnas.curupira.validation.error.ValidationError;
import br.com.tnas.curupira.validator.Validator;

public class IESaoPauloComercioIndustriaValidatorTest extends IEValidatorTest {

	public IESaoPauloComercioIndustriaValidatorTest() {
		super(wrongCheckDigitString, validUnformattedString, validString, validValues);
	}

	private static final String wrongCheckDigitString = "110042490104";
	private static final String wrongSecondCheckDigitString = "110042490115";
	private static final String validUnformattedString = "110042490114";
	private static final String validString = "110.042.490.114";
	// TODO adicionar mais IE validos para São Paulo
	private static final String[] validValues = { validString };

	@Override
	protected Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted) {
		return new IESaoPauloComercioIndustriaValidator(messageProducer, isFormatted);
	}

	@Test
	public void shouldNotValidateIEsWithSecondCheckDigitWrong() {
		Validator<String> validator = getValidator(messageProducer, false);

		try {
			validator.assertValid(wrongSecondCheckDigitString);
			fail();
		} catch (InvalidStateException e) {
			assertEquals(1, e.getInvalidMessages().size());
		}

		verify(messageProducer, times(1)).getMessage(ValidationError.IE$INVALID_CHECK_DIGITS);
	}

}
