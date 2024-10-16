package br.com.tnas.curupira.validation.ie;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.validator.Validator;
import br.com.tnas.curupira.validator.ie.IEGoiasValidator;


public class IEGoiasValidatorTest extends IEValidatorTest {

    public IEGoiasValidatorTest() {
		super(wrongCheckDigitUnformattedString, validUnformattedString, validFormattedString, validValues);
	}

	private static final String wrongCheckDigitUnformattedString = "109876542";

    private static final String validUnformattedString = "109876547";

    private static final String validFormattedString = "10.987.654-7";

    private static final String[] validValues = { validFormattedString, "10.103.119-1", "20.003.152-0" };

	@Override
	protected Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted) {
		return new IEGoiasValidator(messageProducer, isFormatted);
	}


}
