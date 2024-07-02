package br.com.tnas.curupira.formatter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.format.CPFFormatter;
import br.com.tnas.curupira.format.Formatter;

public class CPFFormatterTest {

    private Formatter formatter;

    @BeforeEach
    public void before() {
        formatter = new CPFFormatter();
    }

    @Test
    public void testFormat() {
        String unformattedValue = "11122233344";
        String formattedValue = formatter.format(unformattedValue);
        assertEquals(formattedValue, "111.222.333-44");
    }

    @Test
    public void testUnformat() {
        String formattedValue = "111.222.333-44";
        String unformattedValue = formatter.unformat(formattedValue);
        assertEquals(unformattedValue, "11122233344");
    }

    @Test
	public void shouldDetectIfAValueIsFormattedOrNot() throws Exception {
		assertTrue(formatter.isFormatted("111.222.333-44"));
		assertFalse(formatter.isFormatted("11122233344"));
		assertFalse(formatter.isFormatted("1.1a1.1-2"));
	}

    @Test
	public void shouldDetectIfAValueCanBeFormattedOrNot() throws Exception {
    	assertFalse(formatter.canBeFormatted("111.222.333-44"));
		assertTrue(formatter.canBeFormatted("11122233344"));
		assertFalse(formatter.canBeFormatted("1.1a1.1-2"));
	}

}
