package br.com.tnas.curupira.validator.ie;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.ValidationMessage;
import br.com.tnas.curupira.validator.BaseValidator;
import br.com.tnas.curupira.validator.Validator;
import br.com.tnas.curupira.validator.error.InvalidValue;
import br.com.tnas.curupira.validator.error.ValidationError;

public abstract class AbstractIEValidator implements Validator<String> {

	protected final boolean isFormatted;
	private final BaseValidator baseValidator;

	public AbstractIEValidator() {
		this(true);
	}

	public AbstractIEValidator(boolean isFormatted) {
		this.isFormatted = isFormatted;
		this.baseValidator = new BaseValidator();
	}

	public AbstractIEValidator(MessageProducer messageProducer, boolean isFormatted) {
		this.baseValidator = new BaseValidator(messageProducer);
		this.isFormatted = isFormatted;
	}

	private List<InvalidValue> getInvalidValues(String IE) {
		List<InvalidValue> errors = new ArrayList<InvalidValue>();
		if (IE != null) {
			String unformattedIE = checkForCorrectFormat(IE, errors);
			if (errors.isEmpty()) {
				if (!hasValidCheckDigits(unformattedIE)) {
					errors.add(ValidationError.IE$INVALID_CHECK_DIGITS);
				}
			}
		}
		return errors;
	}

	protected String checkForCorrectFormat(String ie, List<InvalidValue> errors) {
		String unformatedIE = null;
		if (isFormatted) {
			if (!(getFormattedPattern().matcher(ie).matches())) {
				errors.add(ValidationError.IE$INVALID_FORMAT);
			}
			unformatedIE = ie.replaceAll("\\D", "");
		} else {
			if (!getUnformattedPattern().matcher(ie).matches()) {
				errors.add(ValidationError.IE$INVALID_DIGITS);
			}
			unformatedIE = ie;
		}
		return unformatedIE;
	}

	protected abstract Pattern getUnformattedPattern();

	protected abstract Pattern getFormattedPattern();

	@Override
	public boolean isEligible(String value) {
		boolean result;
		if (isFormatted) {
			result = getFormattedPattern().matcher(value).matches();
		} else {
			result = getUnformattedPattern().matcher(value).matches();
		}
		return result;
	}

	@Override
	public void assertValid(String IE) {
		baseValidator.assertValid(getInvalidValues(IE));
	}

	@Override
	public List<ValidationMessage> invalidMessagesFor(String IE) {
		return baseValidator.generateValidationMessages(getInvalidValues(IE));
	}

	protected abstract boolean hasValidCheckDigits(String value);

	protected String format(String value, String pattern, String validCharacters) {
		try {
			final MaskFormatter formatador = new MaskFormatter(pattern);
			formatador.setValidCharacters(validCharacters);
			formatador.setValueContainsLiteralCharacters(false);
			return formatador.valueToString(value);
		} catch (ParseException e) {
			throw new RuntimeException("Valor gerado não bate com o padrão: " + value, e);
		}
	}

	protected String format(String value, String pattern) {
		return this.format(value, pattern, "1234567890");
	}
}