package br.com.tnas.curupira.format;

import java.util.regex.Pattern;

/**
 * @author Rafael Carvalho
 *
 */
public class RenavamFormatter implements Formatter {

	public static final int NO_CHECKDIGITS_SIZE = 10;
    public static final Pattern FORMATED = Pattern.compile("(\\d{2,4}).(\\d{6})-(\\d{1})");
    public static final Pattern UNFORMATED = Pattern.compile("(\\d{2,4})(\\d{6})(\\d{1})");

    private final BaseFormatter base;

    public RenavamFormatter() {
        this.base = new BaseFormatter(FORMATED, "$1.$2-$3", UNFORMATED, "$1$2$3");
    }

	public String format(String renavam) throws IllegalArgumentException {
        return base.format(renavam);
    }

	public String unformat(String renavam) throws IllegalArgumentException {
        return base.unformat(renavam);
    }

    public boolean isFormatted(String value) {
    	return base.isFormatted(value);
    }

    public boolean canBeFormatted(String value) {
    	return base.canBeFormatted(value);
    }
}
