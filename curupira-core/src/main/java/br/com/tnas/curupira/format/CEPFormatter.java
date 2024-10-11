package br.com.tnas.curupira.format;

import java.util.regex.Pattern;

/**
 * @author Edenir Norberto Anschau
 *
 */
public class CEPFormatter implements Formatter {

    public static final Pattern FORMATTED = Pattern.compile("(\\d{5})-(\\d{3})");
    public static final Pattern UNFORMATTED = Pattern.compile("(\\d{5})(\\d{3})");
 
    private final BaseFormatter base;

    public CEPFormatter() {
        this.base = new BaseFormatter(FORMATTED, "$1-$2", UNFORMATTED, "$1$2");
    }

    public String format(String value) throws IllegalArgumentException {
        return base.format(value);
    }

    public String unformat(String value) throws IllegalArgumentException {
        return base.unformat(value);
    }

    public boolean isFormatted(String value) {
        return base.isFormatted(value);
    }

    public boolean canBeFormatted(String value) {
        return base.canBeFormatted(value);
    }

    @Override
    public Pattern getFormattedPattern() {
        return FORMATTED;
    }

    @Override
    public Pattern getUnformattedPattern() {
        return UNFORMATTED;
    }
}