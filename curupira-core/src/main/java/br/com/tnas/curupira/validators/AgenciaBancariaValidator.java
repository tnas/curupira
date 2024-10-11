package br.com.tnas.curupira.validators;

import br.com.tnas.curupira.format.AgenciaBancariaFormatter;
import br.com.tnas.curupira.validation.error.AgenciaBancariaError;
import br.com.tnas.curupira.validators.rules.CheckDigitsRule;
import br.com.tnas.curupira.validators.rules.FormattingRule;
import br.com.tnas.curupira.validators.rules.ValidationRule;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Representa um validador de agencia bancária do Banco do Brasil.
 * 
 * @author Thiago Nascimento
 */
public class AgenciaBancariaValidator extends DocumentoValidator<AgenciaBancariaFormatter> {

    public AgenciaBancariaValidator() {
    	this.formatter = new AgenciaBancariaFormatter();
	}
    
	public AgenciaBancariaValidator(boolean isComDigito) {
		this();
		this.isFormatted = isComDigito;
	}

	@Override
	protected String computeCheckDigits(String agenciaSemDV) {
		
		String[] algarisms = agenciaSemDV.split("");
		int multiplier = 9;
		int sum = 0;
		
		for (int index = algarisms.length - 1; index >= 0; --index) {
			sum += Integer.parseInt(algarisms[index]) * multiplier--;
		}
		
		int rest = sum % 11;
		return rest == 10 ? "X" : String.valueOf(rest); 
	}

	@Override
	protected Pattern getFormattedPattern() {
		return AgenciaBancariaFormatter.FORMATTED;
	}

	@Override
	protected Pattern getUnformatedPattern() {
		return AgenciaBancariaFormatter.UNFORMATTED;
	}
	
	@Override
	protected int getNoCheckDigitsSize() {
		return AgenciaBancariaFormatter.NO_CHECK_DIGITS_SIZE;
	}

	@Override
	protected List<ValidationRule> getValidationRules() {

		var formatter = new AgenciaBancariaFormatter();

		return List.of(
				new FormattingRule(formatter, this.isFormatted, AgenciaBancariaError.INVALID_FORMAT),
				new CheckDigitsRule(formatter, 1, this::computeCheckDigits, AgenciaBancariaError.INVALID_CHECK_DIGIT)
		);
	}
}
