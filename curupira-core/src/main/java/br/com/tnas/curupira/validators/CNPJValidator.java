package br.com.tnas.curupira.validators;

import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.format.CNPJFormatter;
import br.com.tnas.curupira.validators.rules.CheckDigitsRule;
import br.com.tnas.curupira.validators.rules.FormattingRule;
import br.com.tnas.curupira.validators.rules.RepeatedDigitsRule;
import br.com.tnas.curupira.validators.rules.UnformattingRule;
import br.com.tnas.curupira.validators.rules.ValidationRule;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Representa um validador de CNPJ.
 * 
 * @author Leonardo Bessa
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
		this.messageProducer = new SimpleMessageProducer();
    }
    
    public CNPJValidator(boolean isFormatted, boolean isIgnoringRepeatedDigits) {
    	this();
        this.isFormatted = isFormatted;
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
        this.messageProducer = new SimpleMessageProducer();
    }

    public CNPJValidator(MessageProducer messageProducer, boolean isFormatted, boolean isIgnoringRepeatedDigits) {
    	this();
        this.messageProducer = messageProducer;
        this.isFormatted = isFormatted;
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
    	this();
		this.messageProducer = messageProducer;
		this.isFormatted  = isFormatted;
    }
    
    public CNPJValidator(MessageProducer messageProducer){
		this.messageProducer = messageProducer;
    }

	/**
	 * Faz o cálculo dos digitos usando a lógica de CNPJ
	 * 
	 * @return String os dois dígitos calculados.
	 */
    @Override
	protected String calculaDigitos(String cnpjSemDigito) {
		DigitoPara digitoPara = new DigitoPara(cnpjSemDigito);
		digitoPara.complementarAoModulo().trocandoPorSeEncontrar("0",10,11).mod(11);
		
		String digito1 = digitoPara.calcula();
		digitoPara.addDigito(digito1);
		String digito2 = digitoPara.calcula();
		
		return digito1 + digito2;
	}

	@Override
	protected Pattern getFormattedPattern() {
		return CNPJFormatter.FORMATTED;
	}

	@Override
	protected Pattern getUnformatedPattern() {
		return CNPJFormatter.UNFORMATTED;
	}

	@Override
	protected int getNoCheckDigitsSize() {
		return CNPJFormatter.NO_CHECK_DIGITS_SIZE;
	}

	@Override
	protected List<ValidationRule> getValidationRules() {
		return List.of(
				new FormattingRule(this.isFormatted, this.getFormattedPattern()),
				new UnformattingRule(new CNPJFormatter(), 14, "[0-9]*"),
				new RepeatedDigitsRule(new CNPJFormatter(), this.isIgnoringRepeatedDigits),
				new CheckDigitsRule(new CNPJFormatter(), 2, this::calculaDigitos)
		);
	}
    
}