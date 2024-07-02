package br.com.tnas.curupira.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.validation.error.AgenciaBancariaError;
import br.com.tnas.curupira.validators.AgenciaBancariaValidator;
import br.com.tnas.curupira.validators.InvalidStateException;

/**
 * @author Thiago Nascimento
 */
public class AgenciaBancariaValidatorTest {

	private MessageProducer messageProducer = new SimpleMessageProducer();
	
	@Test
	public void shouldAcceptEligibleAgenciasComDV() {
		AgenciaBancariaValidator validator = new AgenciaBancariaValidator();
		assertTrue(validator.isEligible("3610-2"));
		assertFalse(validator.isEligible("3610"));
	}
	
	@Test
	public void shouldAcceptEligibleAgenciasSemDV() {
		AgenciaBancariaValidator validator = new AgenciaBancariaValidator(false);
		assertFalse(validator.isEligible("3610-2"));
		assertTrue(validator.isEligible("3610"));
	}
	
	@Test
	public void shouldReturnNoValidationMessagesForCorrectAgenciasComDV() {
		AgenciaBancariaValidator validator = new AgenciaBancariaValidator();
		assertTrue(validator.invalidMessagesFor("3610-2").isEmpty());
		assertTrue(validator.invalidMessagesFor("3793-1").isEmpty());
		assertTrue(validator.invalidMessagesFor("197-X").isEmpty());
		assertTrue(validator.invalidMessagesFor("4158-0").isEmpty());
		assertTrue(validator.invalidMessagesFor("2121-0").isEmpty());
		assertTrue(validator.invalidMessagesFor("1284-X").isEmpty());
	}
	
	@Test
	public void shouldReturnInvalidCheckDigitForIncorrectDV() {
		
		AgenciaBancariaValidator validator = new AgenciaBancariaValidator();
		
		try {
			validator.assertValid("2121-9");
			fail();
		} catch (InvalidStateException e) {
			e.getInvalidMessages().contains(this.messageProducer.getMessage(AgenciaBancariaError.INVALID_CHECK_DIGIT));
		}
	}
	
	@Test
	public void shouldReturnCheckDigitNotFoundForAgenciaSemDV() {
		
		AgenciaBancariaValidator validator = new AgenciaBancariaValidator();
		
		try {
			validator.assertValid("1103");
			fail();
		} catch (InvalidStateException e) {
			e.getInvalidMessages().contains(this.messageProducer.getMessage(AgenciaBancariaError.CHECK_DIGIT_NOT_FOUND));
		}
	}
}
