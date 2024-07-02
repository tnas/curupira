package br.com.tnas.curupira.formatter;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.format.TituloEleitoralFormatter;

public class TituloEleitoralFormatterTest {

    private Formatter formatter;

    @BeforeEach
    public void before() {
        formatter = new TituloEleitoralFormatter();
    }

    @Test
    public void testFormat() {
        String unfotmatedValue = "133968200302";
        String formatedValue = formatter.format(unfotmatedValue);
        assertEquals(formatedValue, "1339682003/02");
    }

    @Test
    public void testUnformat() {
        String fotmatedValue = "1339682003/02";
        String unformatedValue = formatter.unformat(fotmatedValue);
        assertEquals(unformatedValue, "133968200302");
    }

    @Test
	public void shouldVerifyIfAValueIsFormattedOrNot() throws Exception {
		assertTrue(formatter.isFormatted("1339682003/02"));
		assertFalse(formatter.isFormatted("133968200302"));
		assertFalse(formatter.isFormatted("1339682003/0x"));
	}

    @Test
	public void shouldVerifyIfAValueCanBeFormattedOrNot() throws Exception {
		assertFalse(formatter.canBeFormatted("1339682003/02"));
		assertTrue(formatter.canBeFormatted("133968200302"));
		assertFalse(formatter.canBeFormatted("1339682003/0x"));
	}
}

