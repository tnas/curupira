package br.com.tnas.curupira.validation.ie;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.validator.Validator;
import br.com.tnas.curupira.validator.ie.IESergipeValidator;

public class IESergipeValidatorTest extends IEValidatorTest {

	public IESergipeValidatorTest() {
		super(wrongCheckDigitUnformattedString, validUnformattedString, validString, validValues);
	}

	private static final String wrongCheckDigitUnformattedString = "271234564";
	private static final String validUnformattedString = "271234563";
	private static final String validString = "27123456-3";
	
	private static final String[] validValues = { validString, "44.250.767-4", "67.893.465-7" };

	@Override
	protected Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted) {
		return new IESergipeValidator(messageProducer, isFormatted);
	}
}
