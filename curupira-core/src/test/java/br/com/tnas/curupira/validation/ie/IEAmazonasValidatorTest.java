package br.com.tnas.curupira.validation.ie;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.validator.Validator;
import br.com.tnas.curupira.validator.ie.IEAmazonasValidator;

public class IEAmazonasValidatorTest extends IEValidatorTest {

	public IEAmazonasValidatorTest() {
		super(wrongCheckDigitUnformattedString, validUnformattedString,
				validString, validValues);
	}

	private static final String wrongCheckDigitUnformattedString = "041939800";

	private static final String validUnformattedString = "041939808";

	private static final String validString = "04.193.980-8";

	private static final String[] validValues = { "04.345.678-2",
			"04.193.980-8", "06.200.021-7",	"07.000.507-9", "04.104.862-8" };

	@Override
	protected Validator<String> getValidator(MessageProducer messageProducer,
			boolean isFormatted) {
		return new IEAmazonasValidator(messageProducer, isFormatted);
	}
}
