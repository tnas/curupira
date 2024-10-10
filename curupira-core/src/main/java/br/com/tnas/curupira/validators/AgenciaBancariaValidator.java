package br.com.tnas.curupira.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.validation.error.AgenciaBancariaError;

/**
 * Representa um validador de agencia bancária do Banco do Brasil.
 * 
 * @author Thiago Nascimento
 */
public class AgenciaBancariaValidator extends DocumentoValidator {

    public AgenciaBancariaValidator() {
	}
    
	public AgenciaBancariaValidator(boolean isComDigito) {
		this();
		this.isFormatted = isComDigito;
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
			
			if (this.isFormatted) {
				
				Matcher matcher = this.getFormatedPattern().matcher(agencia);
				
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

	@Override
	protected String getFormatedMask() {
		return "(\\d+)\\-([\\dX])";
	}

	@Override
	protected String getUnformatedMask() {
		return "\\d+";
	}

}
