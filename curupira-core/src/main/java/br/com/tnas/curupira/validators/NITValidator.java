package br.com.tnas.curupira.validators;

import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.format.NITFormatter;
import br.com.tnas.curupira.validation.error.NITError;
import br.com.tnas.curupira.validators.rules.CheckDigitsRule;
import br.com.tnas.curupira.validators.rules.FormattingRule;
import br.com.tnas.curupira.validators.rules.UnformattingRule;
import br.com.tnas.curupira.validators.rules.ValidationRule;

import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * Validador do Número de Identificação do Trabalhador. Este documento contém 11
 * (onze) caracteres numéricos, no formato ddd.ddddd.dd-d.
 * </p>
 * <p>
 * O NIT corresponde ao número do <b>PIS/PASEP/CI</b> sendo que, no caso de
 * Contribuinte Individual (CI), pode ser utilizado o número de inscrição no
 * Sistema Único de Saúde (SUS) ou na Previdência Social.
 * </p>
 * 
 * @author Leonardo Bessa
 */
public class NITValidator extends DocumentoValidator<NITFormatter> {

    /**
     * Este considera, por padrão, que as cadeias não estão formatadas e utiliza um
     * {@linkplain SimpleMessageProducer} para geração de mensagens.
     */
    public NITValidator() {
    	this.formatter = new NITFormatter();
    }

    /**
     * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
     * mensagens.

     * @param isFormatted
     *            considera cadeia no formato de NIT: "ddd.ddddd.dd-d" onde "d"
     *            é um dígito decimal.
     */
    public NITValidator(boolean isFormatted) {
    	this();
    	this.messageProducer = new SimpleMessageProducer();
        this.isFormatted = isFormatted;
    }

    /**
     * <p>
     * Construtor do Validador de NIT.
     * </p>
     * 
     * @param messageProducer
     *            produtor de mensagem de erro.
     * @param isFormatted
     *            considera cadeia no formato de NIT: "ddd.ddddd.dd-d" onde "d"
     *            é um dígito decimal.
     */
    public NITValidator(MessageProducer messageProducer, boolean isFormatted) {
        this.messageProducer = messageProducer;
        this.isFormatted = isFormatted;
    }

    @Override
    protected String computeCheckDigits(String nitSemDigito) {
    	return new DigitoPara(nitSemDigito).complementarAoModulo().trocandoPorSeEncontrar("0",10,11).mod(11).calcula();
	}
    
	@Override
	protected Pattern getFormattedPattern() {
		return NITFormatter.FORMATED;
	}

	@Override
	protected Pattern getUnformatedPattern() {
		return NITFormatter.UNFORMATED;
	}
	
	@Override
	protected int getNoCheckDigitsSize() {
		return NITFormatter.NO_CHECKDIGITS_SIZE;
	}

	@Override
	protected List<ValidationRule> getValidationRules() {

		var formatter = new NITFormatter();

		return List.of(
				new FormattingRule(formatter, this.isFormatted, NITError.INVALID_FORMAT),
				new UnformattingRule(formatter, 11, "[0-9]*", NITError.INVALID_DIGITS),
				new CheckDigitsRule(formatter, 1, this::computeCheckDigits, NITError.INVALID_CHECK_DIGITS)
		);
	}
}
