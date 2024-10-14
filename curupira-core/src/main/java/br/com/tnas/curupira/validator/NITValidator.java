package br.com.tnas.curupira.validator;

import java.util.List;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.format.NITFormatter;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validator.rule.CheckDigitsRule;
import br.com.tnas.curupira.validator.rule.FormattingRule;
import br.com.tnas.curupira.validator.rule.NullRule;
import br.com.tnas.curupira.validator.rule.UnformattingRule;
import br.com.tnas.curupira.validator.rule.ValidationRule;

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
	protected List<ValidationRule> getValidationRules() {
		return List.of(
				new NullRule(),
				new FormattingRule(formatter, Validatable.NIT, this.isFormatted),
				new UnformattingRule(formatter, Validatable.NIT),
				new CheckDigitsRule(formatter, Validatable.NIT, this::computeCheckDigits)
		);
	}
}
