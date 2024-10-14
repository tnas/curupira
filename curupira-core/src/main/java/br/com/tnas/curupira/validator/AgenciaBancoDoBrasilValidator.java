package br.com.tnas.curupira.validator;

import java.util.List;

import br.com.tnas.curupira.format.AgenciaBancariaFormatter;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validator.rule.CheckDigitsRule;
import br.com.tnas.curupira.validator.rule.FormattingRule;
import br.com.tnas.curupira.validator.rule.NullRule;
import br.com.tnas.curupira.validator.rule.ValidationRule;

/**
 * Representa um validador de agencia bancária do Banco do Brasil.
 * 
 * @author Thiago Nascimento
 */
public class AgenciaBancoDoBrasilValidator extends DocumentoValidator<AgenciaBancariaFormatter> {

    public AgenciaBancoDoBrasilValidator() {
    	this.formatter = new AgenciaBancariaFormatter();
	}
    
	public AgenciaBancoDoBrasilValidator(boolean isComDigito) {
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
	protected List<ValidationRule> getValidationRules() {
		return List.of(
				new NullRule(),
				new FormattingRule(formatter, Validatable.AgenciaBancoDoBrasil, this.isFormatted),
				new CheckDigitsRule(formatter, Validatable.AgenciaBancoDoBrasil, this::computeCheckDigits)
		);
	}
}
