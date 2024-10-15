package br.com.tnas.curupira.validation.ie;

import java.util.ArrayList;
import java.util.List;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.validation.error.ValidationError;
import br.com.tnas.curupira.validator.LogicOrComposedValidator;
import br.com.tnas.curupira.validator.Validator;

public class IESaoPauloValidator implements Validator<String> {

	private final LogicOrComposedValidator<String> baseValidator;

	/**
	 * Este considera, por padrão, que as cadeias estão formatadas e utiliza um
	 * {@linkplain SimpleMessageProducer} para geração de mensagens.
	 */
	public IESaoPauloValidator() {
		this(true);
	}

	/**
	 * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
	 * mensagens.
	 * 
	 * @param isFormatted
	 *            considerar cadeia formatada quando <code>true</code>
	 */
	public IESaoPauloValidator(boolean isFormatted) {
		this(new SimpleMessageProducer(), isFormatted);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IESaoPauloValidator(MessageProducer messageProducer, boolean isFormatted) {
		Class[] validatorClasses = { 
				IESaoPauloComercioIndustriaValidator.class,
				IESaoPauloProdutorRuralValidator.class };
		this.baseValidator = new LogicOrComposedValidator<String>(messageProducer, isFormatted, validatorClasses);
		this.baseValidator.setInvalidFormat(ValidationError.IE$INVALID_FORMAT);
	}

	public void assertValid(String value) {
		if (value != null) {
			baseValidator.assertValid(value);
		}
	}

	public List<ValidationMessage> invalidMessagesFor(String value) {
		List<ValidationMessage> result;
		if (value != null) {
			result = baseValidator.invalidMessagesFor(value);
		} else {
			result = new ArrayList<ValidationMessage>();
		}
		return result;
	}

	public boolean isEligible(String object) {
		return baseValidator.isEligible(object);
	}

	/**
	 * @see Validator#generateRandomValid()
	 * @see LogicOrComposedValidator#generateRandomValid()
	 * @return uma inscrição estadual de comércio e indústria válida
	 */
	public String generateRandomValid() {
		return baseValidator.generateRandomValid();
	}
}
