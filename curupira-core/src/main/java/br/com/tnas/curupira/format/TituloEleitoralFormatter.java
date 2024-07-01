package br.com.tnas.curupira.format;


import br.com.tnas.curupira.validators.TituloEleitoralValidator;

public class TituloEleitoralFormatter implements Formatter{

	private final BaseFormatter base;

    public TituloEleitoralFormatter() {
        this.base = new BaseFormatter(TituloEleitoralValidator.FORMATED, "$1/$2",TituloEleitoralValidator.UNFORMATED, "$1$2");
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
