package br.com.tnas.curupira.formatter;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.format.RenavamFormatter;

public class RenavamFormatterTest {

    private final Formatter formatter = new RenavamFormatter();

    @Test
    public void shouldFormatAnUnformattedRenavam() {
        String formattedRenavam = formatter.format("00736407677");
        assertEquals("0073.640767-7", formattedRenavam);
    }

    @Test
    public void shouldUnformatAFormattedRenavam() {
        String unformattedRenavam = formatter.unformat("73.640767-7");
        assertEquals("736407677", unformattedRenavam);
    }

    @Test
	public void shouldVerifyIfAValueIsFormattedOrNot() throws Exception {
		assertTrue(formatter.isFormatted("73.640767-7"));
		assertTrue(formatter.isFormatted("0073.640767-7"));
		assertFalse(formatter.isFormatted("736407677"));
		assertFalse(formatter.isFormatted("73.x407a7-7"));
	}

    @Test
	public void shouldVerifyIfAValueCanBeFormattedOrNot() throws Exception {
		assertFalse(formatter.canBeFormatted("73.640767-7"));
		assertFalse(formatter.canBeFormatted("0073.640767-7"));
		assertTrue(formatter.canBeFormatted("736407677"));
		assertTrue(formatter.canBeFormatted("00736407677"));
		assertFalse(formatter.canBeFormatted("73.x407a7-7"));
	}
}
