package br.com.tnas.curupira.validator.ie;

import java.util.Random;
import java.util.regex.Pattern;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.SimpleMessageProducer;

/**
 * <p>
 * Documentação de referência:
 * </p>
 * <a href="http://www.pfe.fazenda.sp.gov.br/consist_ie.shtm">Secretaria da
 * Fazenda do Estado de São Paulo</a>
 * <a href="http://www.sintegra.gov.br/Cad_Estados/cad_DF.html">SINTEGRA -
 * ROTEIRO DE CRÍTICA DA INSCRIÇÃO ESTADUAL </a>
 * 
 */
public class IEDistritoFederalValidator extends AbstractIEValidator {

	/*
	 * Formato: 07.408.738/002-50
	 */

	public static final Pattern FORMATED = Pattern.compile("(0[7-8])[.]([0-9]\\d{2})[.](\\d{3})[/](\\d{3})[-](\\d{2})");

	public static final Pattern UNFORMATED = Pattern.compile("(0[7-8])([0-9]\\d{2})(\\d{3})(\\d{3})(\\d{2})");

	/**
	 * Este considera, por padrão, que as cadeias estão formatadas e utiliza um
	 * {@linkplain SimpleMessageProducer} para geração de mensagens.
	 */
	public IEDistritoFederalValidator() {
		super(true);
	}

	/**
	 * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
	 * mensagens.
	 * 
	 * @param isFormatted
	 *            considerar cadeia formatada quando <code>true</code>
	 */
	public IEDistritoFederalValidator(boolean isFormatted) {
		super(isFormatted);
	}

	public IEDistritoFederalValidator(MessageProducer messageProducer, boolean isFormatted) {
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
		String iESemDigito = unformattedIE.substring(0, unformattedIE.length() - 2);
		String digitos = unformattedIE.substring(unformattedIE.length() - 2);
		String digitosCalculados = calculaDigitos(iESemDigito);

		return digitos.equals(digitosCalculados);
	}

	private String calculaDigitos(String iESemDigito) {
		DigitoPara digitoPara = new DigitoPara(iESemDigito);
		digitoPara.complementarAoModulo().trocandoPorSeEncontrar("0", 10, 11);

		String digito1 = digitoPara.calcula();
		digitoPara.addDigito(digito1);
		String digito2 = digitoPara.calcula();

		return digito1 + digito2;
	}

	private String geraDoisPrimeirosDigitos() {
		final Random random = new Random();
		final String primeiroDigito = String.valueOf(random.nextInt(7) + 3);
		final String segundoDigito = String.valueOf(random.nextInt(7) + 3);
		return primeiroDigito + segundoDigito;
	}

	public String generateRandomValid() {
		final String ieSemDigitos = "07" + geraDoisPrimeirosDigitos() + new DigitoGenerator().generate(7);
		final String ieComDigitos = ieSemDigitos + calculaDigitos(ieSemDigitos);
		if (isFormatted) {
			return super.format(ieComDigitos, "##.###.###/###-##");
		}
		return ieComDigitos;
	}
}
