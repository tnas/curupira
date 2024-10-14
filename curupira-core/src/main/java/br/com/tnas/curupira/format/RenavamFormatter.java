package br.com.tnas.curupira.format;

import java.util.regex.Pattern;

/**
 * @author Rafael Carvalho
 *
 */
public class RenavamFormatter implements Formatter {

	public static final int NO_CHECK_DIGITS_SIZE = 10;
	public static final int NUMBER_OF_CHECK_DIGITS = 1;
    public static final Pattern FORMATTED = Pattern.compile("(\\d{2,4}).(\\d{6})-(\\d{1})");
    public static final Pattern UNFORMATTED = Pattern.compile("(\\d{2,4})(\\d{6})(\\d{1})");

    private final BaseFormatter base;
    private boolean isFormatted;

    public RenavamFormatter() {
        this.base = new BaseFormatter(FORMATTED, "$1.$2-$3", UNFORMATTED, "$1$2$3");
    }

	public String format(String renavam) throws IllegalArgumentException {
        return base.format(renavam);
    }

	public String unformat(String renavam) throws IllegalArgumentException {
        return base.unformat(this.reformat(renavam));
    }

    public boolean isFormatted(String value) {
    	return base.isFormatted(value);
    }

    public boolean canBeFormatted(String value) {
    	return base.canBeFormatted(value);
    }

    @Override
    public String reformat(String renavam) {
        return (isFormatted && renavam.length() == 11) || (!isFormatted && renavam.length() == 9)
                ? "00" + renavam : renavam;
    }

    @Override
    public Pattern getFormattedPattern() {
        return FORMATTED;
    }

    @Override
    public Pattern getUnformattedPattern() {
        return UNFORMATTED;
    }

    public void setFormatted(boolean formatted) {
        isFormatted = formatted;
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
