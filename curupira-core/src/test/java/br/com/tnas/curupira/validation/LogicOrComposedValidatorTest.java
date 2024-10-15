package br.com.tnas.curupira.validation;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.validator.CNPJValidator;
import br.com.tnas.curupira.validator.CPFValidator;
import br.com.tnas.curupira.validator.LogicOrComposedValidator;

@SuppressWarnings("unchecked")
public class LogicOrComposedValidatorTest {
	private MessageProducer messageProducer;

	@BeforeEach
	public void setUp() {
		messageProducer = mock(MessageProducer.class);
	}

	@Test
	public void shouldThrowIllegalArgumentExceptionIfNoValidatorsArePassedOnConstruction()
	        throws Exception {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new LogicOrComposedValidator<String>(messageProducer, true));
	}

	@Test
	public void shouldGenerateValidDocumentAccordingToTheFirstValidatorPassedAsArgument()
	        throws Exception {
		LogicOrComposedValidator<String> validatorCpfPrimeiro = new LogicOrComposedValidator<String>(
		        messageProducer, true, CPFValidator.class, CNPJValidator.class);
		new CPFValidator(true).assertValid(validatorCpfPrimeiro.generateRandomValid());

		LogicOrComposedValidator<String> validatorCnpjPrimeiro = new LogicOrComposedValidator<String>(
		        messageProducer, true, CNPJValidator.class, CPFValidator.class);
		new CNPJValidator(true).assertValid(validatorCnpjPrimeiro.generateRandomValid());
	}

    @Test
	public void shouldGenerateValidDocumentFormattedAccordingToParameterSpecifiedInConstructor()
	        throws Exception {
		LogicOrComposedValidator<String> validatorFormatado = new LogicOrComposedValidator<String>(
		        messageProducer, true, CPFValidator.class, CNPJValidator.class);
		new CPFValidator(true).assertValid(validatorFormatado.generateRandomValid());

		LogicOrComposedValidator<String> validatorNaoFormatado = new LogicOrComposedValidator<String>(
		        messageProducer, false, CPFValidator.class, CNPJValidator.class);
		new CPFValidator(false).assertValid(validatorNaoFormatado.generateRandomValid());
	}
}
