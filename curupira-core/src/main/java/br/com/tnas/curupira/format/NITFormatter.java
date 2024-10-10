package br.com.tnas.curupira.format;

import br.com.tnas.curupira.validators.NITValidator;

/**
 * @author Leonardo Bessa
 */
public class NITFormatter implements Formatter {

    private final BaseFormatter base;

    public NITFormatter() {
    	var validator = new NITValidator();
        this.base = new BaseFormatter(validator.getFormatedPattern(), "$1.$2.$3-$4", validator.getUnformatedPattern(), "$1$2$3$4");
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
