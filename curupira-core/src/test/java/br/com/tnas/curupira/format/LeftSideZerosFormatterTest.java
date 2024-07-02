package br.com.tnas.curupira.format;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeftSideZerosFormatterTest {

	private Formatter formatter;

	@BeforeEach
	public void setUp() throws Exception {
		formatter = new LeftSideZerosFormatter(14);
	}

    @Test
    public void testFormat() {
        String actual = formatter.format("1234567890");
        assertEquals("00001234567890", actual);
    }

    @Test
    public void testUnformat() {
        String actual = formatter.unformat("000567890");
        assertEquals("567890", actual);
    }

    @Test
	public void shouldVerifyIfAValueIsAlreadyFormattedOrNot() throws Exception {
		assertTrue(formatter.isFormatted("00001234567890"));
		assertFalse(formatter.isFormatted("00001234"));
		assertFalse(formatter.isFormatted("1234567890"));
		assertFalse(formatter.isFormatted("123456789012345"));
	}

    @Test
	public void shouldVerifyIfAValueCanBeFormattedOrNot() throws Exception {
		assertTrue(formatter.canBeFormatted("00001234567890"));
		assertTrue(formatter.canBeFormatted("00001234"));
		assertTrue(formatter.canBeFormatted("1234567890"));
		assertFalse(formatter.canBeFormatted("123456789012345"));
		assertFalse(formatter.canBeFormatted("abc123"));
	}
}
