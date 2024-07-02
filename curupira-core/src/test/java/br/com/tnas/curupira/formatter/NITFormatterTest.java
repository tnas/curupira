package br.com.tnas.curupira.formatter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.format.NITFormatter;

public class NITFormatterTest {

    private Formatter formatter;

    @BeforeEach
    public void before() {
        formatter = new NITFormatter();
    }

    @Test
    public void testFormat() {
        String unfotmatedValue = "17033259504";
        String formatedValue = formatter.format(unfotmatedValue);
        assertEquals(formatedValue, "170.33259.50-4");
    }

    @Test
    public void testUnformat() {
        String fotmatedValue = "170.33259.50-4";
        String unformatedValue = formatter.unformat(fotmatedValue);
        assertEquals(unformatedValue, "17033259504");
    }

    @Test
	public void verifyIfAValueIsAlreadyFormattedOrNot() throws Exception {
		assertTrue(formatter.isFormatted("170.33259.50-4"));
		assertFalse(formatter.isFormatted("17033259504"));
		assertFalse(formatter.isFormatted("170.C32b9.50-a"));
	}

    @Test
	public void verifyIfAValueCanBeFormattedOrNot() throws Exception {
		assertFalse(formatter.canBeFormatted("170.33259.50-4"));
		assertTrue(formatter.canBeFormatted("17033259504"));
		assertFalse(formatter.canBeFormatted("170.C32b9.50-a"));
	}
}
