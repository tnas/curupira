package br.com.tnas.curupira.format;

import java.util.regex.Pattern;

public class AgenciaBancariaFormatter implements Formatter {
	
	public static final int NO_CHECK_DIGITS_SIZE = 4;
	public static final int NUMBER_OF_CHECK_DIGITS = 1;
	public static final Pattern FORMATTED = Pattern.compile("(\\d+)\\-([\\dX])");
	public static final Pattern UNFORMATTED = Pattern.compile("(\\d+)");
	
	private final BaseFormatter base;
	
	public AgenciaBancariaFormatter() {
		this.base = new BaseFormatter(FORMATTED, "$1-$2", UNFORMATTED, "$1$2");
	}
	
	@Override
	public String format(String value)  {
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
	
	@Override
	public int getNoCheckDigitsSize() {
		return NO_CHECK_DIGITS_SIZE;
	}

	@Override
	public int getNumberOfCheckDigits() {
		return NUMBER_OF_CHECK_DIGITS;
	}
	
}
