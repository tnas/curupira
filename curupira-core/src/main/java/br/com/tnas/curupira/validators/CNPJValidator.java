package br.com.tnas.curupira.validators;

import java.util.List;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.format.CNPJFormatter;
import br.com.tnas.curupira.validation.error.CNPJError;
import br.com.tnas.curupira.validators.rules.CheckDigitsRule;
import br.com.tnas.curupira.validators.rules.FormattingRule;
import br.com.tnas.curupira.validators.rules.NullRule;
import br.com.tnas.curupira.validators.rules.RepeatedDigitsRule;
import br.com.tnas.curupira.validators.rules.UnformattingRule;
import br.com.tnas.curupira.validators.rules.ValidationRule;

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
				new NullRule(CNPJError.INVALID_DIGITS),
				new FormattingRule(this.formatter, this.isFormatted, CNPJError.INVALID_FORMAT),
				new UnformattingRule(this.formatter, CNPJError.INVALID_DIGITS),
				new RepeatedDigitsRule(this.formatter, this.isIgnoringRepeatedDigits, CNPJError.REPEATED_DIGITS),
				new CheckDigitsRule(this.formatter, this::computeCheckDigits, CNPJError.INVALID_CHECK_DIGITS)
		);
	}
    
}