package br.com.tnas.curupira.format;

import java.util.regex.Pattern;

public class AgenciaBancariaFormatter implements Formatter {
	
	public static final int NO_CHECKDIGITS_SIZE = 4;
	public static final Pattern FORMATED = Pattern.compile("(\\d+)\\-([\\dX])");
	public static final Pattern UNFORMATED = Pattern.compile("(\\d+)");
	
	private final BaseFormatter base;
	
	public AgenciaBancariaFormatter() {
		this.base = new BaseFormatter(FORMATED, "$1-$2", UNFORMATED, "$1");
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

}
