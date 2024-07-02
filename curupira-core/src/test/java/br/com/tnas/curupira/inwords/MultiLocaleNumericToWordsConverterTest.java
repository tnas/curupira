package br.com.tnas.curupira.inwords;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <a href="mailto:hprange@gmail.com">Henrique Prange</a>
 */
public class MultiLocaleNumericToWordsConverterTest {
	
	@Test
	public void supportMultipleCountriesWithSameLanguageWhenConvertingToWords() throws Exception {
	    Locale[] locales = new Locale[] { Locale.ENGLISH, Locale.CANADA, Locale.US, Locale.UK };

	    for (Locale locale : locales) {
	    	NumericToWordsConverter converter = new NumericToWordsConverter(new InteiroSemFormato(), locale);

	    	String result = converter.toWords(13L);

	    	assertThat(result, is("thirteen"));
		}
	}

	@Test
	public void throwExceptionWhenConvertingToWordsWithUnsupportedLanguage() throws Exception {
    	NumericToWordsConverter converter = new NumericToWordsConverter(new InteiroSemFormato(), Locale.JAPAN);
    	UnsupportedOperationException exception = 
				Assertions.assertThrows(UnsupportedOperationException.class, () -> converter.toWords(13L));
		assertEquals("Não é possivel converter números para o idioma japonês", exception.getMessage());
	}

	@Test
	public void useBrazilianLocaleWhenConvertingToWordsWithoutLocale() throws Exception {
		Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(new Locale("en", "US"));

        try {
			NumericToWordsConverter converter = new NumericToWordsConverter(new InteiroSemFormato());

	    	String result = converter.toWords(13L);

	    	assertThat(result, is("treze"));
        } finally {
        	Locale.setDefault(defaultLocale);
        }
	}
}
