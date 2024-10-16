package br.com.tnas.curupira.validation.ie;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.validator.Validator;
import br.com.tnas.curupira.validator.ie.IESantaCatarinaValidator;

public class IESantaCatarinaValidatorTest extends IEValidatorTest {

	public IESantaCatarinaValidatorTest() {
		super(wrongCheckDigitString, validUnformattedString, validString, validValues);
	}

	private static final String wrongCheckDigitString = "251040858";
	private static final String validUnformattedString = "251040852";
	private static final String validString = "251.040.852";
	private static final String[] validValues = { validString, "251040852", "214562549", "241603331"};

	@Override
	protected Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted) {
		return new IESantaCatarinaValidator(messageProducer, isFormatted);
	}

}
