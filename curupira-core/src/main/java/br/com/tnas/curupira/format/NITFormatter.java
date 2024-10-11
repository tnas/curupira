package br.com.tnas.curupira.format;

import java.util.regex.Pattern;

/**
 * @author Leonardo Bessa
 */
public class NITFormatter implements Formatter {

	public static final int NO_CHECKDIGITS_SIZE = 10;
	public static final Pattern FORMATED = Pattern.compile("(\\d{3})[.](\\d{5})[.](\\d{2})-(\\d{1})");
	public static final Pattern UNFORMATED = Pattern.compile("(\\d{3})(\\d{5})(\\d{2})(\\d{1})");
	
    private final BaseFormatter base;

    public NITFormatter() {
        this.base = new BaseFormatter(FORMATED, "$1.$2.$3-$4", UNFORMATED, "$1$2$3$4");
    }

	public String format(String value) {
        return base.format(value);
    }

	public String unformat(String value) {
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
        return FORMATED;
    }

    @Override
    public Pattern getUnformattedPattern() {
        return UNFORMATED;
    }
}
