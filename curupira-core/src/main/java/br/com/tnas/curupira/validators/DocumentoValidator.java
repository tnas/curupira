package br.com.tnas.curupira.validators;

import java.util.Objects;
import java.util.regex.Pattern;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;

public abstract class DocumentoValidator implements Validator<String> {

	private Pattern formatedPattern;
    private Pattern unformatedPattern;
	
	protected boolean isFormatted;
	protected MessageProducer messageProducer;
	
	public DocumentoValidator() {
		formatedPattern = Pattern.compile(this.getFormatedMask());
		unformatedPattern = Pattern.compile(this.getUnformatedMask());
		this.messageProducer = new SimpleMessageProducer();
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
		return Objects.nonNull(value) && !value.isBlank() && (isFormatted ? formatedPattern : unformatedPattern).matcher(value).matches();
	}
	
	protected abstract String getFormatedMask();
	
	protected abstract String getUnformatedMask();
	
	public Pattern getFormatedPattern() {
		return formatedPattern;
	}

	public Pattern getUnformatedPattern() {
		return unformatedPattern;
	}
	
}
