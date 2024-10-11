package br.com.tnas.curupira.validators;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.rules.NullRule;
import br.com.tnas.curupira.validators.rules.ValidationRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class DocumentoValidator<F extends Formatter> implements Validator<String> {

    protected F formatter;
	protected boolean isFormatted;
	protected MessageProducer messageProducer;
	
	protected DocumentoValidator() {
		this.messageProducer = new SimpleMessageProducer();
	}

	protected abstract int getNoCheckDigitsSize();
	
	protected abstract Pattern getFormattedPattern();
	
	protected abstract Pattern getUnformatedPattern();
	
	protected abstract String computeCheckDigits(String valueWithoutDigit);

	protected abstract List<ValidationRule> getValidationRules();

	@Override
	public List<ValidationMessage> invalidMessagesFor(String value) {

		var messages = new ArrayList<ValidationMessage>();

		var rules = new ArrayList<>(this.getValidationRules());
		rules.addFirst(new NullRule());

		var error = rules
				.stream()
				.filter(r -> !r.validate(value)).map(ValidationRule::getErrorMessage)
				.findFirst();
        error.ifPresent(messages::add);

		return messages;
	}

	@Override
    public void assertValid(String value) {
    	
        var errors = this.invalidMessagesFor(value);
        
		if (!errors.isEmpty()) {
            throw new InvalidStateException(errors);
        }
    }
	
	@Override
	public boolean isEligible(String value) {
		return Objects.nonNull(value) && !value.isBlank() 
				&& (isFormatted ? getFormattedPattern() : getUnformatedPattern()).matcher(value).matches();
	}
	
    @Override
	public String generateRandomValid() {
    	
		final String valorSemDigitos = new DigitoGenerator().generate(this.getNoCheckDigitsSize());
		final String valorComDigitos = valorSemDigitos + this.computeCheckDigits(valorSemDigitos);
		
		if (isFormatted) {
			return this.formatter.format(valorComDigitos);
		}
		return valorComDigitos;
	}
}
