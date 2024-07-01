package br.com.tnas.curupira.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.validation.error.AgenciaBancariaError;

/**
 * Representa um validador de agencia bancária.
 * 
 * @author Thiago Nascimento
 */
public class AgenciaBancariaValidator implements Validator<String> {

	public static final Pattern COM_DV = Pattern.compile("(\\d+)\\-([\\dX])");
    public static final Pattern SEM_DV = Pattern.compile("\\d+");
	
    private boolean isComDigito = true;
    private MessageProducer messageProducer;
    
    public AgenciaBancariaValidator() {
    	this.messageProducer = new SimpleMessageProducer();
	}
    
	public AgenciaBancariaValidator(boolean isComDigito) {
		this();
		this.isComDigito = isComDigito;
	}

	public void assertValid(String agencia) {
		
		List<ValidationMessage> errors = this.invalidMessagesFor(agencia);
		
		if (!errors.isEmpty()) {
			throw new InvalidStateException(errors);
	    }
	}

	public List<ValidationMessage> invalidMessagesFor(String agencia) {
		
		List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
		
		if (this.isEligible(agencia)) {
			
			if (this.isComDigito) {
				
				Matcher matcher = COM_DV.matcher(agencia);
				
				if (!matcher.find()) {
					throw new InvalidStateException(this.messageProducer.getMessage(AgenciaBancariaError.INVALID_FORMAT));
				}
				
				String dvInformado = matcher.group(2);
				String dvComputado = this.computarDigitoVerificador(matcher.group(1));
				
				if (!dvInformado.equals(dvComputado)) {
					errors.add(this.messageProducer.getMessage(AgenciaBancariaError.INVALID_CHECK_DIGIT));	
				}
				
			} else {
				errors.add(this.messageProducer.getMessage(AgenciaBancariaError.CHECK_DIGIT_NOT_FOUND));
			}
			
		} else {
			errors.add(this.messageProducer.getMessage(AgenciaBancariaError.INVALID_FORMAT));
		}
		
		return errors;
	}

	public boolean isEligible(String value) {
		
		if (Objects.isNull(value) || value.isBlank()) {
			return false;
		}
		
		return this.isComDigito ? 
				COM_DV.matcher(value).matches() : SEM_DV.matcher(value).matches();
	}

	public String generateRandomValid() {
		final String agenciaSemDigitos = new DigitoGenerator().generate(4);
		return String.format("%s-%s", agenciaSemDigitos, this.computarDigitoVerificador(agenciaSemDigitos));
	}
	
	public String computarDigitoVerificador(String agenciaSemDV) {
		
		String[] algarisms = agenciaSemDV.split(""); 
		int multiplier = 9;
		int sum = 0;
		
		for (int index = algarisms.length - 1; index >= 0; --index) {
			sum += Integer.valueOf(algarisms[index]) * multiplier--;
		}
		
		int rest = sum % 11;
		return rest == 10 ? "X" : String.valueOf(rest); 
	}

}
