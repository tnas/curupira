package br.com.tnas.curupira.inwords;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:hprange@gmail.com">Henrique Prange</a>
 */
public class FormatoDeMoedaTest {
	
	@Test
	public void useDolarWhenCreatingFormatoDeMoedaForLocaleUS() throws Exception {
		FormatoDeMoeda formato = new FormatoDeMoeda(Locale.US);

		assertThat(formato.getUnidadeDecimalNoSingular(), is("cent"));
		assertThat(formato.getUnidadeDecimalNoPlural(), is("cents"));
		assertThat(formato.getUnidadeInteiraNoSingular(), is("dollar"));
		assertThat(formato.getUnidadeInteiraNoPlural(), is("dollars"));
		assertThat(formato.getCasasDecimais(), is(2));
	}

	@Test
	public void useRealWhenCreatingFormatoDeMoedaForLocalePT_BR() throws Exception {
		FormatoDeMoeda formato = new FormatoDeMoeda(Messages.LOCALE_PT_BR);

		assertThat(formato.getUnidadeDecimalNoSingular(), is("centavo"));
		assertThat(formato.getUnidadeDecimalNoPlural(), is("centavos"));
		assertThat(formato.getUnidadeInteiraNoSingular(), is("real"));
		assertThat(formato.getUnidadeInteiraNoPlural(), is("reais"));
		assertThat(formato.getCasasDecimais(), is(2));
	}

	@Test
	public void throwExceptionWhenCreatingFormatoDeMoedaForUnknownLocale() throws Exception {
		IllegalArgumentException exception = 
				Assertions.assertThrows(IllegalArgumentException.class, () -> new FormatoDeMoeda(Locale.JAPAN));
		assertEquals("Não foi possível determinar a moeda para o país Japão", exception.getMessage());
	}
	
}
