package br.com.tnas.curupira.formatter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.format.CNPJFormatter;
import br.com.tnas.curupira.format.Formatter;

public class CNPJFormatterTest {

    private Formatter formatter;

    @BeforeEach
    public void before() {
        formatter = new CNPJFormatter();
    }

    @Test
    public void testFormat() {
        String unfotmatedValue = "26637142000158";
        String formatedValue = formatter.format(unfotmatedValue);
        assertEquals(formatedValue, "26.637.142/0001-58");
    }

    @Test
    public void testUnformat() {
        String fotmatedValue = "26.637.142/0001-58";
        String unformatedValue = formatter.unformat(fotmatedValue);
        assertEquals(unformatedValue, "26637142000158");
    }

    @Test
    public void testShoudNotThrowExceptionIfAlreadyUnformated() {
        String fotmatedValue = "26637142000158";
        String unformatedValue = formatter.unformat(fotmatedValue);
        assertEquals(unformatedValue, "26637142000158");
    }

    @Test
	public void shouldVerifyIfAValueIsAlreadyFormattedOrNot() throws Exception {
		assertTrue(formatter.isFormatted("26.637.142/0001-58"));
		assertFalse(formatter.isFormatted("26637142000158"));
		assertFalse(formatter.isFormatted("26.7.1x2/00a1-58"));
	}

    @Test
	public void shouldVerifyIfAValueCanBeFormatted() throws Exception {
		assertFalse(formatter.canBeFormatted("26.637.142/0001-58"));
		assertTrue(formatter.canBeFormatted("26637142000158"));
		assertFalse(formatter.canBeFormatted("26.7.1x2/00a1-58"));
	}
}
