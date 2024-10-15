package br.com.tnas.curupira.validation.ie;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.validation.error.ValidationError;
import br.com.tnas.curupira.validator.InvalidStateException;
import br.com.tnas.curupira.validator.Validator;


@Suite
@SelectClasses({IESaoPauloValidatorTest.IESaoPauloComercioIndustria.class,
		IESaoPauloValidatorTest.IESaoPauloProdutorRural.class})
public class IESaoPauloValidatorTest {
	/*
	 * Este teste é um dos mais complexos. Este IESaoPauloValidator suporta o
	 * equivalente ao IESaoPauloProdutorRuralValidator E
	 * IESaoPauloComercioIndustriaValidator utilizando o
	 * LogicOrComposedValidator internamente.
	 * 
	 * O que eu fiz foi criar 2 classes internas que copiam os exemplos do
	 * IESaoPauloProdutorRuralValidatorTest e
	 * IESaoPauloComercioIndustriaValidatorTest e ambos usam o
	 * IESaoPauloValidator. Esta abordagem não funcionou para 3 métodos de
	 * teste genérico que foram sbreescritos:
	 * shouldNotValidateIEWithLessDigitsThanAllowed e
	 * shouldNotValidateIEWithMoreDigitsThanAlowed que não fazem sentido pois os
	 * limites de caracter são muito distantes e o terceiro método
	 * shouldNotValidateIEWithInvalidCharacter que ao inves de lançar
	 * IEError.INVALID_DIGITS lança IEError.INVALID_FORMAT por causa da
	 * utilização do LogicOrComposedValidator
	 */

	public static IESaoPauloValidator newValidator(MessageProducer messageProducer, boolean isFormatted) {
		return new IESaoPauloValidator(messageProducer, isFormatted);
	}

	public static class IESaoPauloComercioIndustria extends IEValidatorTest {

		public IESaoPauloComercioIndustria() {
			super(wrongCheckDigitString, validUnformattedString, validString, validValues);
		}

		private static final String wrongCheckDigitString = "110042490104";
		private static final String validUnformattedString = "110042490114";
		private static final String validString = "110.042.490.114";
		// TODO adicionar mais IE validos para São Paulo
		private static final String[] validValues = { validString };

		@Override
		protected Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted) {
			return newValidator(messageProducer, isFormatted);
		}

		@Override
		public void shouldNotValidateIEWithLessDigitsThanAllowed() {
		}

		@Override
		public void shouldNotValidateIEWithMoreDigitsThanAlowed() {
		}

		@Override
		public void shouldNotValidateIEWithInvalidCharacter() {
			Validator<String> validator = getValidator(messageProducer, false);
			try {
				validator.assertValid(validUnformattedString.replaceFirst(".", "&"));
				fail();
			} catch (InvalidStateException e) {
				assertTrue(e.getInvalidMessages().size() == 1);
			}

			verify(messageProducer, times(1)).getMessage(ValidationError.IE$INVALID_FORMAT);
		}
	}

	public static class IESaoPauloProdutorRural extends IEValidatorTest {

		public IESaoPauloProdutorRural() {
			super(wrongCheckDigitString, validUnformattedString, validString, validValues);
		}

		private static final String wrongCheckDigitString = "P011004245002";
		private static final String validUnformattedString = "P011004243002";
		private static final String validString = "P-01100424.3/002";
		// TODO adicionar mais IE validos para São Paulo
		private static final String[] validValues = { validString };

		@Override
		protected Validator<String> getValidator(MessageProducer messageProducer, boolean isFormatted) {
			return newValidator(messageProducer, isFormatted);
		}

		@Override
		public void shouldNotValidateIEWithLessDigitsThanAllowed() {
		}

		@Override
		public void shouldNotValidateIEWithMoreDigitsThanAlowed() {
		}

		@Override
		public void shouldNotValidateIEWithInvalidCharacter() {
			Validator<String> validator = getValidator(messageProducer, false);
			try {
				validator.assertValid(validUnformattedString.replaceFirst(".", "&"));
				fail();
			} catch (InvalidStateException e) {
				assertTrue(e.getInvalidMessages().size() == 1);
			}

			verify(messageProducer, times(1)).getMessage(ValidationError.IE$INVALID_FORMAT);
		}
	}
}
