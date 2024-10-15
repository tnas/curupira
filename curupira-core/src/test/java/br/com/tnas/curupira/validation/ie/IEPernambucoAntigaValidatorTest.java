package br.com.tnas.curupira.validation.ie;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.validator.Validator;
import br.com.tnas.curupira.validator.ie.IEPernambucoAntigaValidator;

public class IEPernambucoAntigaValidatorTest extends IEValidatorTest {

	public IEPernambucoAntigaValidatorTest() {
		super(wrongCheckDigitUnformattedString, validUnformattedString, validFormattedString, validValues);
	}

	private static final String wrongCheckDigitUnformattedString = "18100100000048";

	private static final String validUnformattedString = "18100100000049";

	private static final String validFormattedString = "18.1.001.0000004-9";

	private static final String[] validValues = { validFormattedString, "18119003256336", 
		"1811500337842-7", "18119003584848", "18132003335425", "1818310338152-6"};

	@Override
	protected Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted) {
		return new IEPernambucoAntigaValidator(messageProducer, isFormatted);
	}

}
