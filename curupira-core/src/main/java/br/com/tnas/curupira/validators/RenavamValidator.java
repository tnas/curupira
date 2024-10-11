package br.com.tnas.curupira.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.RenavamFormatter;
import br.com.tnas.curupira.validation.error.RenavamError;
import br.com.tnas.curupira.validators.rules.CheckDigitsRule;
import br.com.tnas.curupira.validators.rules.FormattingRule;
import br.com.tnas.curupira.validators.rules.UnformattingRule;
import br.com.tnas.curupira.validators.rules.ValidationRule;

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
	protected String computeCheckDigits(String renavamSemDigito) {
    	return new DigitoPara(renavamSemDigito).complementarAoModulo().trocandoPorSeEncontrar("0",10,11).mod(11).calcula();
	}

	@Override
	protected Pattern getFormattedPattern() {
		return RenavamFormatter.FORMATTED;
	}

	@Override
	protected Pattern getUnformatedPattern() {
		return RenavamFormatter.UNFORMATTED;
	}
	
	@Override
	protected int getNoCheckDigitsSize() {
		return RenavamFormatter.NO_CHECK_DIGITS_SIZE;
	}

	@Override
	protected List<ValidationRule> getValidationRules() {

		var formatter = new RenavamFormatter();
		formatter.setFormatted(this.isFormatted);

		return List.of(
				new FormattingRule(formatter, this.isFormatted, RenavamError.INVALID_FORMAT),
				new UnformattingRule(formatter, 11, "[0-9]*", RenavamError.INVALID_DIGITS),
				new CheckDigitsRule(formatter, 1, this::computeCheckDigits, RenavamError.INVALID_CHECK_DIGIT)
		);
	}
}
