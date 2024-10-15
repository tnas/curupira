package br.com.tnas.curupira.validator;

import java.util.List;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.format.CNPJFormatter;
import br.com.tnas.curupira.validation.error.ValidationError;
import br.com.tnas.curupira.validator.rule.CheckDigitsRule;
import br.com.tnas.curupira.validator.rule.FormattingRule;
import br.com.tnas.curupira.validator.rule.NullRule;
import br.com.tnas.curupira.validator.rule.RepeatedDigitsRule;
import br.com.tnas.curupira.validator.rule.UnformattingRule;
import br.com.tnas.curupira.validator.rule.ValidationRule;

/**
 * Representa um validador de CNPJ.
 * 
 * @author Leonardo Bessa
 * @author Thiago Nascimento
 */
public class CNPJValidator extends DocumentoValidator<CNPJFormatter> {

    private boolean isIgnoringRepeatedDigits;

    /**
     * Este considera, por padrão, que as cadeias não estão formatadas e utiliza um
     * {@linkplain SimpleMessageProducer} para geração de mensagens.
     */
    public CNPJValidator() {
    	this.formatter = new CNPJFormatter();
    }

    /**
     * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
     * mensagens. Leva em conta se o valor está ou não formatado.
     * 
     * @param isFormatted
     *            considera cadeia no formato de CNPJ: "dd.ddd.ddd/dddd-dd" onde
     *            "d" é um dígito decimal.
     */
    public CNPJValidator(boolean isFormatted) {
    	this();
		this.isFormatted  = isFormatted;
    }
    
    public CNPJValidator(boolean isFormatted, boolean isIgnoringRepeatedDigits) {
    	this(isFormatted);
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
    }

    /**
     * <p>
     * Construtor do Validador de CNPJ. Leva em consideração se o valor está formatado.
     * </p>
     * <p>
     *
     * @param messageProducer
     *            produtor de mensagem de erro.
     * @param isFormatted
     *            considera cadeia no formato de CNPJ: "dd.ddd.ddd/dddd-dd" onde
     *            "d" é um dígito decimal.
     */
    public CNPJValidator(MessageProducer messageProducer, boolean isFormatted) {
    	this(isFormatted);
		this.messageProducer = messageProducer;
    }
    
	@Override
	protected List<ValidationRule> getValidationRules() {
		return List.of(
				new NullRule(),
				new FormattingRule(this.formatter, ValidationError.CNPJ$INVALID_FORMAT, this.isFormatted),
				new UnformattingRule(this.formatter, ValidationError.CNPJ$INVALID_DIGITS),
				new RepeatedDigitsRule(this.formatter, ValidationError.CNPJ$REPEATED_DIGITS, this.isIgnoringRepeatedDigits),
				new CheckDigitsRule(this.formatter, ValidationError.CNPJ$INVALID_CHECK_DIGITS, this::computeCheckDigits)
		);
	}
    
}