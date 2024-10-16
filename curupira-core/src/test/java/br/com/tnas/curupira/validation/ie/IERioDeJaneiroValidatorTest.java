package br.com.tnas.curupira.validation.ie;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.validator.Validator;
import br.com.tnas.curupira.validator.ie.IERioDeJaneiroValidator;

public class IERioDeJaneiroValidatorTest extends IEValidatorTest {

	public IERioDeJaneiroValidatorTest() {
		super(wrongCheckDigitUnformattedString, validUnformattedString, validFormattedString, validValues);
	}

	private static final String validFormattedString = "78.045.302";
	
	private static final String validUnformattedString = "78045302";
	
	private static final String wrongCheckDigitUnformattedString = "78045304";
	
	private static final String[] validValues = { validFormattedString, "53.518.028", "71.294.242" };

	@Override
	protected Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted) {
		return new IERioDeJaneiroValidator(messageProducer, isFormatted);
	}

}
