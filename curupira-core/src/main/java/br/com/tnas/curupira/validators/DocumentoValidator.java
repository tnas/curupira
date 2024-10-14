package br.com.tnas.curupira.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.rules.ValidationRule;

public abstract class DocumentoValidator<F extends Formatter> implements Validator<String> {

    protected F formatter;
	protected boolean isFormatted;
	protected MessageProducer messageProducer;
	
	protected DocumentoValidator() {
		this.messageProducer = new SimpleMessageProducer();
	}

	protected abstract List<ValidationRule> getValidationRules();
	
	protected String computeCheckDigits(String valueWithoutDigit) {
		
		var checkDigits = new StringBuilder();
		
		DigitoPara digitoPara = new DigitoPara(valueWithoutDigit);
		digitoPara.complementarAoModulo().trocandoPorSeEncontrar("0", 10, 11).mod(11);
		
		while (checkDigits.toString().length() < this.formatter.getNumberOfCheckDigits()) {
			var digit = digitoPara.calcula();
			checkDigits.append(digit);
			digitoPara.addDigito(digit);
		} 
		
		return checkDigits.toString();
	}

	@Override
	public List<ValidationMessage> invalidMessagesFor(String value) {

		var messages = new ArrayList<ValidationMessage>();

		var error = this.getValidationRules()
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
				&& (isFormatted ? this.formatter.getFormattedPattern() 
						: this.formatter.getUnformattedPattern()).matcher(value).matches();
	}
	
    @Override
	public String generateRandomValid() {
    	
		final String valorSemDigitos = new DigitoGenerator().generate(this.formatter.getNoCheckDigitsSize());
		final String valorComDigitos = valorSemDigitos + this.computeCheckDigits(valorSemDigitos);
		
		if (isFormatted) {
			return this.formatter.format(valorComDigitos);
		}
		return valorComDigitos;
	}
}
