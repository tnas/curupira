package br.com.tnas.curupira.validator.rule;

import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validation.error.InvalidValue;

public class ElectoralStateCodeRule extends ValidationRule {

    private static final int CODIGO_SAO_PAULO = 1;
    private static final int CODIGO_EXTERIOR_ZZ = 28;

    public ElectoralStateCodeRule(Formatter formatter, InvalidValue invalidValue) {
        super(formatter, invalidValue);
    }

    @Override
    public boolean validate(String value) {
        value = this.formatter.unformat(value);
        int stateCode = Integer.parseInt(value.substring(value.length() - 4, value.length() - 2));
        return stateCode >= CODIGO_SAO_PAULO && stateCode <= CODIGO_EXTERIOR_ZZ;
    }
}
