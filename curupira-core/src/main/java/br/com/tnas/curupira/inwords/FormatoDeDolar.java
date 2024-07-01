package br.com.tnas.curupira.inwords;

/**
 * Representa o formato para números em dólares com até duas casas decimais.
 *
 * @author <a href="mailto:hprange@gmail.com">Henrique Prange</a>
 */
public class FormatoDeDolar implements FormatoDeExtenso {
	
    public String getUnidadeInteiraNoSingular() {
        return "dollar";
    }

    public String getUnidadeInteiraNoPlural() {
        return "dollars";
    }

    public String getUnidadeDecimalNoSingular() {
        return "cent";
    }

	public String getUnidadeDecimalNoPlural() {
        return "cents";
    }

    public int getCasasDecimais() {
        return 2;
    }
}
