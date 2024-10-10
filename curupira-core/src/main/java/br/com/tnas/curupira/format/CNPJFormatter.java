package br.com.tnas.curupira.format;

import java.util.regex.Pattern;

/**
 * @author Leonardo Bessa
 *
 */
public class CNPJFormatter implements Formatter {
	
	public static final int NO_CHECKDIGITS_SIZE = 12;
	public static final Pattern FORMATED = Pattern.compile("(\\d{2})[.](\\d{3})[.](\\d{3})/(\\d{4})-(\\d{2})");
	public static final Pattern UNFORMATED = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
	
    private final BaseFormatter base;

    public CNPJFormatter() {
        this.base = new BaseFormatter(FORMATED, "$1.$2.$3/$4-$5", UNFORMATED, "$1$2$3$4$5");
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

}
