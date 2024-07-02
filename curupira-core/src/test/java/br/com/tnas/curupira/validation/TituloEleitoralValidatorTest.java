package br.com.tnas.curupira.validation;


import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.validators.InvalidStateException;
import br.com.tnas.curupira.validators.TituloEleitoralValidator;
import br.com.tnas.curupira.validators.Validator;

/**
 * @author Leonardo Bessa
 */
public class TituloEleitoralValidatorTest {

    private final String[] validStrings = { "543275360116","142501480248", "557833330370", "013785610434",
            "253346440540", "033734180663", "585353130710", "884328631058", "553505611201", "028565701333",
            "245770031481", "713782341503", "403374181694", "452083221724", "162749070141" };
	
    private final String[] validStringsFormatted = { "5432753601/16","1425014802/48", "5578333303/70", "0137856104/34",
            "2533464405/40", "0337341806/63", "5853531307/10", "8843286310/58", "5535056112/01", "0285657013/33",
            "2457700314/81", "7137823415/03", "4033741816/94", "4520832217/24", "2800440202/05"};
    
    private final String[] invalidFirstDigitStrings = { "543275360106", "452083221714", "253346440520", "553505611231",
            "884328631048" };

    private final String[] invalidSecondDigitStrings = { "543275360119", "452083221728", "253346440547",
            "553505611206", "884328631055" };

    private Validator<String> validator;
    private Validator<String> validatorFormatted;

    @BeforeEach
    public void setup() {
        validator = new TituloEleitoralValidator(false);
        validatorFormatted = new TituloEleitoralValidator(true);
   
    }
    
    @Test
    public void shouldValidateCorrectString() {
        for (String validString : validStrings) {
            validator.assertValid(validString);
        }
    }
    
    @Test
    public void shouldValidateCorrectFormattedString() {
    	for (String validString : validStringsFormatted) {
    		validatorFormatted.assertValid(validString);
    	}
    }

    @Test
    public void shouldNotValidateStringWithFirstCheckDigitWrong() {
        for (String invalidString : invalidFirstDigitStrings) {
            try {
                validator.assertValid(invalidString);
                fail("O titulo eleitoral " + invalidString + " deve ser considerado inválido!");
            } catch (InvalidStateException e) {
            }
        }
    }

    @Test
    public void shouldNotValidateStringWithSecondCheckDigitWrong() {
        for (String invalidString : invalidSecondDigitStrings) {
            try {
                validator.assertValid(invalidString);
                fail("O titulo eleitoral " + invalidString + " deve ser considerado inválido!");
            } catch (InvalidStateException e) {
            }
        }
    }

    @Test
    public void shouldNotValidateStringMoreDigits() {
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid(validStrings[0] + "0"));
    }

    @Test
    public void shouldNotValidateStringWithCodigoDeEstadoInvalidoMenorDoQueUm() {
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid("471235380051"));
    }

    @Test
    public void shouldNotValidateStringWithCodigoDeEstadoInvalidoMaiorDoQue28() {
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid("815155812960"));
    }

    @Test
    public void shouldGenerateRandomValidUnformatted() {
        final String value = validator.generateRandomValid();
        validator.assertValid(value);
    }

    @Test
    public void shouldGenerateRandomValidFormatted() {
        final String value = validatorFormatted.generateRandomValid();
        validatorFormatted.assertValid(value);
    }
}
