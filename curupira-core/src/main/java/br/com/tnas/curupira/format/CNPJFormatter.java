package br.com.tnas.curupira.format;

import br.com.tnas.curupira.validators.CNPJValidator;

/**
 * @author Leonardo Bessa
 *
 */
public class CNPJFormatter implements Formatter {
    private final BaseFormatter base;

    public CNPJFormatter() {
    	var validator = new CNPJValidator();
        this.base = new BaseFormatter(validator.getFormatedPattern(), "$1.$2.$3/$4-$5", validator.getUnformatedPattern(), "$1$2$3$4$5");
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
