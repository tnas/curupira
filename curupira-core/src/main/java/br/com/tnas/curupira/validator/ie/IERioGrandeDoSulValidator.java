package br.com.tnas.curupira.validator.ie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.SimpleMessageProducer;
import br.com.tnas.curupira.message.ValidationMessage;
import br.com.tnas.curupira.validator.BaseValidator;
import br.com.tnas.curupira.validator.error.InvalidValue;
import br.com.tnas.curupira.validator.error.ValidationError;

public class IERioGrandeDoSulValidator extends AbstractIEValidator {

	private final boolean isFormatted;

	public static final Pattern FORMATED = Pattern.compile("[0-4]\\d{2}\\/\\d{7}");

	public static final Pattern UNFORMATED = Pattern.compile("([0-4]\\d{2})\\d{7}");

	/**
	 * Este considera, por padrão, que as cadeias estão formatadas e utiliza um
	 * {@linkplain SimpleMessageProducer} para geração de mensagens.
	 */
	public IERioGrandeDoSulValidator() {
		this(true);
	}

	/**
	 * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
	 * mensagens.
	 * 
	 * @param isFormatted
	 *            considerar cadeia formatada quando <code>true</code>
	 */
	public IERioGrandeDoSulValidator(boolean isFormatted) {
		this.baseValidator = new BaseValidator();
		this.isFormatted = isFormatted;
	}

	public IERioGrandeDoSulValidator(MessageProducer messageProducer, boolean isFormatted) {
		this.baseValidator = new BaseValidator(messageProducer);
		this.isFormatted = isFormatted;
	}

	private List<InvalidValue> getInvalidValues(String IE) {
		List<InvalidValue> errors = new ArrayList<InvalidValue>();
		errors.clear();
		if (IE != null) {
			String unformatedIE = checkForCorrectFormat(IE, errors);
			if (errors.isEmpty()) {
				if (!hasValidCheckDigits(unformatedIE)) {
					errors.add(ValidationError.IE$INVALID_CHECK_DIGITS);
				}
			}
		}
		return errors;
	}

	protected String checkForCorrectFormat(String ie, List<InvalidValue> errors) {
		String unformatedIE = null;
		if (isFormatted) {
			if (!(FORMATED.matcher(ie).matches())) {
				errors.add(ValidationError.IE$INVALID_FORMAT);
			}
			unformatedIE = ie.replaceAll("\\D", "");
		} else {
			if (!UNFORMATED.matcher(ie).matches()) {
				errors.add(ValidationError.IE$INVALID_DIGITS);
			}
			unformatedIE = ie;
		}
		return unformatedIE;
	}

	protected boolean hasValidCheckDigits(String unformattedIE) {
		String iESemDigito = unformattedIE.substring(0, unformattedIE.length() - 1);
		String digito = unformattedIE.substring(unformattedIE.length() - 1);

		String digitoCalculado = calculaDigito(iESemDigito);

		return digito.equals(digitoCalculado);
	}

	private String calculaDigito(String iESemDigito) {
		DigitoPara digitoPara = new DigitoPara(iESemDigito);
		digitoPara.complementarAoModulo().trocandoPorSeEncontrar("0", 10, 11);

		return digitoPara.calcula();
	}

	public boolean isEligible(String value) {
		boolean result;
		if (isFormatted) {
			result = FORMATED.matcher(value).matches();
		} else {
			result = UNFORMATED.matcher(value).matches();
		}
		return result;
	}

	private final BaseValidator baseValidator;

	public void assertValid(String cpf) {
		baseValidator.assertValid(getInvalidValues(cpf));
	}

	public List<ValidationMessage> invalidMessagesFor(String cpf) {
		return baseValidator.generateValidationMessages(getInvalidValues(cpf));
	}

	public String generateRandomValid() {
		final String primeiroDigito = String.valueOf(new Random().nextInt(5));
		final String ieSemDigito = primeiroDigito + new DigitoGenerator().generate(8);
		final String ieComDigito = ieSemDigito + calculaDigito(ieSemDigito);
		if (isFormatted) {
			return super.format(ieComDigito, "###/#######");
		}
		return ieComDigito;
	}

	@Override
	protected Pattern getUnformattedPattern() {
		return UNFORMATED;
	}

	@Override
	protected Pattern getFormattedPattern() {
		return FORMATED;
	}
}
