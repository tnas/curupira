package br.com.tnas.curupira.validators;

import java.util.Objects;
import java.util.regex.Pattern;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.format.Formatter;

public abstract class DocumentoValidator<F extends Formatter> implements Validator<String> {

    protected F formatter;
	protected boolean isFormatted;
	protected MessageProducer messageProducer;
	
	public DocumentoValidator() {
		this.messageProducer = new SimpleMessageProducer();
	}

	protected abstract int getNoCheckDigitsSize();
	
	protected abstract Pattern getFormatedPattern();
	
	protected abstract Pattern getUnformatedPattern();
	
	protected abstract String calculaDigitos(String valueWithoutDigit);
	
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
				&& (isFormatted ? getFormatedPattern() : getUnformatedPattern()).matcher(value).matches();
	}
	
    @Override
	public String generateRandomValid() {
    	
		final String valorSemDigitos = new DigitoGenerator().generate(this.getNoCheckDigitsSize());
		final String valorComDigitos = valorSemDigitos + this.calculaDigitos(valorSemDigitos);
		
		if (isFormatted) {
			return this.formatter.format(valorComDigitos);
		}
		return valorComDigitos;
	}

    // TODO: Refactor
	protected boolean hasAllRepeatedDigits(String value) {
        for (int i = 1; i < value.length(); i++) {
            if (value.charAt(i) != value.charAt(0)) {
                return false;
            }
        }
        return true;
    }
	
}
