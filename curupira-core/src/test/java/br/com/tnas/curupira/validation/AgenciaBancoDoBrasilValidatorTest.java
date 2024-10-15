package br.com.tnas.curupira.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.BasicMessageProducer;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.validation.error.ValidationError;
import br.com.tnas.curupira.validator.AgenciaBancoDoBrasilValidator;
import br.com.tnas.curupira.validator.InvalidStateException;

/**
 * @author Thiago Nascimento
 */
class AgenciaBancoDoBrasilValidatorTest {

	private MessageProducer messageProducer = new BasicMessageProducer();
	
	@Test
	void shouldAcceptEligibleAgenciasComDV() {
		AgenciaBancoDoBrasilValidator validator = new AgenciaBancoDoBrasilValidator(true);
		assertTrue(validator.isEligible("3610-2"));
		assertFalse(validator.isEligible("3610"));
	}
	
	@Test
	void shouldAcceptEligibleAgenciasSemDV() {
		AgenciaBancoDoBrasilValidator validator = new AgenciaBancoDoBrasilValidator();
		assertFalse(validator.isEligible("3610-2"));
		assertTrue(validator.isEligible("3610"));
	}
	
	@Test
	void shouldReturnNoValidationMessagesForCorrectAgenciasComDV() {
		AgenciaBancoDoBrasilValidator validator = new AgenciaBancoDoBrasilValidator(true);
		assertTrue(validator.invalidMessagesFor("3610-2").isEmpty());
		assertTrue(validator.invalidMessagesFor("3793-1").isEmpty());
		assertTrue(validator.invalidMessagesFor("197-X").isEmpty());
		assertTrue(validator.invalidMessagesFor("4158-0").isEmpty());
		assertTrue(validator.invalidMessagesFor("2121-0").isEmpty());
		assertTrue(validator.invalidMessagesFor("1284-X").isEmpty());
	}
	
	@Test
	void shouldReturnInvalidCheckDigitForIncorrectDV() {
		
		AgenciaBancoDoBrasilValidator validator = new AgenciaBancoDoBrasilValidator(true);
		
		try {
			validator.assertValid("2121-9");
			fail();
		} catch (InvalidStateException e) {
			e.getInvalidMessages().contains(this.messageProducer.getMessage(ValidationError.AgenciaBancoDoBrasil$INVALID_CHECK_DIGITS));
		}
	}
	
	@Test
	void shouldReturnCheckDigitNotFoundForAgenciaSemDV() {
		
		AgenciaBancoDoBrasilValidator validator = new AgenciaBancoDoBrasilValidator();
		
		try {
			validator.assertValid("1103");
			fail();
		} catch (InvalidStateException e) {
			e.getInvalidMessages().contains(this.messageProducer.getMessage(ValidationError.AgenciaBancoDoBrasil$INVALID_CHECK_DIGITS));
		}
	}
}
