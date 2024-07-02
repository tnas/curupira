package br.com.tnas.curupira.inwords;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author Victor dos Santos Pereira
 * @author Leonardo Bessa
 * 
 */
public class IntegerNumericToWordsConverterPT_BR_Test extends IntegerNumericToWordsConverterTest {

    private static Locale defaultLocale;

    @BeforeAll
    public static void setPT_BR_Locale() {
        defaultLocale = Locale.getDefault();
        Locale.setDefault(new Locale("PT", "br"));
    }

    @AfterAll
    public static void setDefaultLocale() {
        Locale.setDefault(defaultLocale);
    }

}
