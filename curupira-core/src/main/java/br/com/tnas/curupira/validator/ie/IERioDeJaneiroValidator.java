package br.com.tnas.curupira.validator.ie;

import java.util.regex.Pattern;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.SimpleMessageProducer;

public class IERioDeJaneiroValidator extends AbstractIEValidator {

	public static final Pattern FORMATED = Pattern.compile("\\d{2}(\\.\\d{3}){2}");

	public static final Pattern UNFORMATED = Pattern.compile("\\d{8}");

	/**
	 * Este considera, por padrão, que as cadeias estão formatadas e utiliza um
	 * {@linkplain SimpleMessageProducer} para geração de mensagens.
	 */
	public IERioDeJaneiroValidator() {
		super(true);
	}

	/**
	 * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
	 * mensagens.
	 * 
	 * @param isFormatted
	 *            considerar cadeia formatada quando <code>true</code>
	 */
	public IERioDeJaneiroValidator(boolean isFormatted) {
		super(isFormatted);
	}

	public IERioDeJaneiroValidator(MessageProducer messageProducer, boolean isFormatted) {
		super(messageProducer, isFormatted);
	}

	@Override
	protected Pattern getUnformattedPattern() {
		return UNFORMATED;
	}

	@Override
	protected Pattern getFormattedPattern() {
		return FORMATED;
	}

	protected boolean hasValidCheckDigits(String unformattedIE) {
		String iESemDigito = unformattedIE.substring(0, unformattedIE.length() - 1);
		String digito = unformattedIE.substring(unformattedIE.length() - 1);

		String digitoCalculado = calculaDigito(iESemDigito);

		return digito.equals(digitoCalculado);
	}

	private String calculaDigito(String iESemDigito) {
		DigitoPara digitoPara = new DigitoPara(iESemDigito);
		digitoPara.comMultiplicadoresDeAte(2, 7).complementarAoModulo().trocandoPorSeEncontrar("0", 10, 11);

		return digitoPara.calcula();
	}

	public String generateRandomValid() {
		final String ieSemDigito = new DigitoGenerator().generate(7);
		final String ieComDigito = ieSemDigito + calculaDigito(ieSemDigito);
		if (isFormatted) {
			return super.format(ieComDigito, "##.###.###");
		}
		return ieComDigito;
	}
}
