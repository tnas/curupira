package br.com.tnas.curupira.formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.format.CEPFormatter;
import br.com.tnas.curupira.format.Formatter;

public class CEPFormatterTest {

    private Formatter formatter;

    @BeforeEach
    public void before() {
        formatter = new CEPFormatter();
    }

    @Test
    public void testFormat() {
        String unfotmatedValue = "12345678";
        String formatedValue = formatter.format(unfotmatedValue);
        assertEquals(formatedValue, "12345-678");
    }

    @Test
    public void testUnformat() {
        String unfotmatedValue = "12345-678";
        String formatedValue = formatter.unformat(unfotmatedValue);
        assertEquals(formatedValue, "12345678");
    }

    @Test
    public void shouldVerifyIfAValueIsFormattedOrNot() throws Exception {
        assertTrue(formatter.isFormatted("12345-678"));
        assertFalse(formatter.isFormatted("12345678"));
        assertFalse(formatter.isFormatted("12345-67a"));
    }

    @Test
    public void shouldVerifyIfAValueCanBeFormattedOrNot() throws Exception {
        assertFalse(formatter.canBeFormatted("12345-678"));
        assertTrue(formatter.canBeFormatted("12345678"));
        assertFalse(formatter.canBeFormatted("12345-678"));
    }

    @Test
    public void testShoudNotThrowExceptionIfAlreadyUnformated() {
        String fotmatedValue = "12345678";
        String unformatedValue = formatter.unformat(fotmatedValue);
        assertEquals(unformatedValue, "12345678");
    }

}