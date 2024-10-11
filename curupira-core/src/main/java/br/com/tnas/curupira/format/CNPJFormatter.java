package br.com.tnas.curupira.format;

import java.util.regex.Pattern;

/**
 * @author Leonardo Bessa
 *
 */
public class CNPJFormatter implements Formatter {
	
	public static final int NO_CHECK_DIGITS_SIZE = 12;
	public static final Pattern FORMATTED = Pattern.compile("(\\d{2})[.](\\d{3})[.](\\d{3})/(\\d{4})-(\\d{2})");
	public static final Pattern UNFORMATTED = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
	
    private final BaseFormatter base;

    public CNPJFormatter() {
        this.base = new BaseFormatter(FORMATTED, "$1.$2.$3/$4-$5", UNFORMATTED, "$1$2$3$4$5");
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
		return FORMATTED;
	}

	@Override
	public Pattern getUnformattedPattern() {
		return UNFORMATTED;
	}
}
