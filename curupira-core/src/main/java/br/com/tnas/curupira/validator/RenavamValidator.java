package br.com.tnas.curupira.validator;

import java.util.List;

import br.com.tnas.curupira.format.RenavamFormatter;
import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.SimpleMessageProducer;
import br.com.tnas.curupira.validator.error.ValidationError;
import br.com.tnas.curupira.validator.rule.CheckDigitsRule;
import br.com.tnas.curupira.validator.rule.FormattingRule;
import br.com.tnas.curupira.validator.rule.NullRule;
import br.com.tnas.curupira.validator.rule.UnformattingRule;
import br.com.tnas.curupira.validator.rule.ValidationRule;

/**
 * <p>
 * Verifica se uma cadeia (String) representa um Renavam válido.
 * </p>
 * 
 * <p>
 * O Renavam, ou Registro nacional de veículos automotores, é o número único de
 * cada veículo e é composto de 10 (dez) dígitos, mais um digito verificador.
 * </p>
 * 
 * Formato do Renavam: "dddd.dddddd-d" onde "d" é um digito decimal.
 * 
 * @author Rafael Carvalho
 */
public class RenavamValidator extends DocumentoValidator<RenavamFormatter> {

    /**
     * Construtor padrão de validador do Renavam. Este considera, por padrão,
     * que as cadeias estão formatadas e utiliza um
     * {@linkplain SimpleMessageProducer} para geração de mensagens.
     */
    public RenavamValidator() {
    	this.formatter = new RenavamFormatter();
    }

    /**
     * Construtor de validador de Renavam. O validador utiliza um
     * {@linkplain SimpleMessageProducer} para geração de mensagens.
     * 
     * @param isFormatted
     *            Informa se a cadeia de caracteres a ser validada está ou não
     *            formatada
     */
    public RenavamValidator(boolean isFormatted) {
    	this();
        this.isFormatted = isFormatted;
        this.messageProducer = new SimpleMessageProducer();
    }

    /**
     * <p>
     * Construtor do validador de Renavam
     * </p>
     * 
     * @param messageProducer
     *            produtor de mensagem de erro.
     * @param isFormatted
     *            Informa se a cadeia de caracteres a ser validada está ou não
     *            formatada
     */
    public RenavamValidator(MessageProducer messageProducer, boolean isFormatted) {
        this.isFormatted = isFormatted;
        this.messageProducer = messageProducer;
    }

	@Override
	protected List<ValidationRule> getValidationRules() {
		this.formatter.setFormatted(this.isFormatted);
		return List.of(
				new NullRule(),
				new FormattingRule(formatter, ValidationError.Renavam$INVALID_FORMAT, this.isFormatted),
				new UnformattingRule(formatter, ValidationError.Renavam$INVALID_DIGITS),
				new CheckDigitsRule(formatter, ValidationError.Renavam$INVALID_CHECK_DIGITS, this::computeCheckDigits)
		);
	}
}
