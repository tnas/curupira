package br.com.tnas.curupira.validators;

import java.util.List;

import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.format.CPFFormatter;
import br.com.tnas.curupira.validation.error.CPFError;
import br.com.tnas.curupira.validators.rules.CheckDigitsRule;
import br.com.tnas.curupira.validators.rules.FormattingRule;
import br.com.tnas.curupira.validators.rules.NullRule;
import br.com.tnas.curupira.validators.rules.RepeatedDigitsRule;
import br.com.tnas.curupira.validators.rules.UnformattingRule;
import br.com.tnas.curupira.validators.rules.ValidationRule;

/**
 * Verifica se uma cadeia (String) é válida para o documento de CPF (Cadastro de
 * Pessoa Física).
 * 
 * @author Leonardo Bessa
 */
public class CPFValidator extends DocumentoValidator<CPFFormatter> {

    private final boolean isIgnoringRepeatedDigits;

    /**
     * Construtor padrão de validador de CPF. Este considera, por padrão, que as
     * cadeias não estão formatadas e utiliza um
     * {@linkplain SimpleMessageProducer} para geração de mensagens.
     */
    public CPFValidator() {
        this(new SimpleMessageProducer(), false, false);
        this.formatter = new CPFFormatter();
    }

    /**
     * Construtor de validador de CPF. O validador utiliza um
     * {@linkplain SimpleMessageProducer} para geração de mensagens. Leva em
     * conta se o valor está ou não formatado.
     * 
     * @param isFormatted
     *            considera cadeia no formato de CPF:"ddd.ddd.ddd-dd" onde "d" é
     *            um dígito decimal.
     */
    public CPFValidator(boolean isFormatted) {
        this(new SimpleMessageProducer(), isFormatted, false);
    }

    /**
     * Construtor de validador de CPF. O validador utiliza um
     * 
     * @param isFormatted
     *            indica se o CPF está formatado.
     * @param isIgnoringRepeatedDigits
     *            condição para ignorar cadeias de CPF com todos os dígitos
     *            repetidos. {@linkplain SimpleMessageProducer} para geração de
     *            mensagens.
     */
    public CPFValidator(boolean isFormatted, boolean isIgnoringRepeatedDigits) {
        this(new SimpleMessageProducer(), isFormatted, isIgnoringRepeatedDigits);
    }

    /**
     * <p>
     * Construtor do Validador de CPF. Leva em consideração se o valor está
     * formatado.
     * </p>
     * <p>
     * Por padrão o validador criado não aceita cadeias de CPF com todos os
     * dígitos repetidos, quando todas as outras condições de validação são
     * aceitas. Para considerar estes documentos válidos use o construtor
     * {@link #CPFValidator(MessageProducer, boolean, boolean)} com a váriavel
     * {@linkplain #isIgnoringRepeatedDigits} em <code>true</code>.
     * </p>
     * 
     * @param messageProducer
     *            produtor de mensagem de erro.
     * @param isFormatted
     *            considera cadeia no formato de CPF: "ddd.ddd.ddd-dd" onde "d"
     *            é um dígito decimal.
     */
    public CPFValidator(MessageProducer messageProducer, boolean isFormatted) {
        this(messageProducer, isFormatted, false);
    }

    /**
     * @param messageProducer
     *            produtor de mensagem de erro.
     * @param isFormatted
     *            condição para considerar cadeia no formato de CPF:
     *            "ddd.ddd.ddd-dd" onde "d" é um dígito decimal.
     * @param isIgnoringRepeatedDigits
     *            condição para ignorar cadeias de CPF com todos os dígitos
     *            repetidos.
     */
    public CPFValidator(MessageProducer messageProducer, boolean isFormatted, boolean isIgnoringRepeatedDigits) {
    	this.formatter = new CPFFormatter();
        this.messageProducer = messageProducer;
        this.isFormatted = isFormatted;
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
    }

    /**
     * Faz o cálculo dos digitos usando a lógica de CPF
     * 
     * @return String os dois dígitos calculados.
     */
    @Override
    protected String computeCheckDigits(String cpfSemDigito) {
        DigitoPara digitoPara = new DigitoPara(cpfSemDigito);
        digitoPara.comMultiplicadoresDeAte(2, 11).complementarAoModulo().trocandoPorSeEncontrar("0", 10, 11).mod(11);

        String digito1 = digitoPara.calcula();
        digitoPara.addDigito(digito1);
        String digito2 = digitoPara.calcula();

        return digito1 + digito2;
    }

    @Override
    protected List<ValidationRule> getValidationRules() {
        var formatter = new CPFFormatter();
        return List.of(
        		new NullRule(CPFError.INVALID_DIGITS),
                new FormattingRule(formatter, this.isFormatted, CPFError.INVALID_FORMAT),
                new UnformattingRule(formatter, CPFError.INVALID_DIGITS),
                new RepeatedDigitsRule(formatter, this.isIgnoringRepeatedDigits, CPFError.REPEATED_DIGITS),
                new CheckDigitsRule(formatter, this::computeCheckDigits, CPFError.INVALID_CHECK_DIGITS)
        );
    }
}
