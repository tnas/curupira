package br.com.tnas.curupira.format;


import java.util.regex.Pattern;

public class TituloEleitoralFormatter implements Formatter {

	public static final int NO_CHECK_DIGITS_SIZE = 10;
	public static final int NUMBER_OF_CHECK_DIGITS = 2;
	public static final Pattern FORMATED = Pattern.compile("(\\d{10})/(\\d{2})");
	public static final Pattern UNFORMATED = Pattern.compile("(\\d{10})(\\d{2})");
	
	private final BaseFormatter base;

    public TituloEleitoralFormatter() {
        this.base = new BaseFormatter(FORMATED, "$1/$2", UNFORMATED, "$1$2");
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

	@Override
	public int getNumberOfCheckDigits() {
		return NUMBER_OF_CHECK_DIGITS;
	}

	@Override
	public int getNoCheckDigitsSize() {
		return NO_CHECK_DIGITS_SIZE;
	}
}
