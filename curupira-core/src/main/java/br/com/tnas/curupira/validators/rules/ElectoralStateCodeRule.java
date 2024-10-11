package br.com.tnas.curupira.validators.rules;

import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.Formatter;
import br.com.tnas.curupira.validators.InvalidValue;

public class ElectoralStateCodeRule implements ValidationRule {

    private static final int CODIGO_SAO_PAULO = 1;
    private static final int CODIGO_EXTERIOR_ZZ = 28;

    private final Formatter formatter;
    private final InvalidValue errorKey;

    public ElectoralStateCodeRule(Formatter formatter, InvalidValue errorKey) {
        this.formatter = formatter;
        this.errorKey = errorKey;
    }

    @Override
    public ValidationMessage getErrorMessage() {
        return messageProducer.getMessage(this.errorKey);
    }

    @Override
    public boolean validate(String value) {
        value = this.formatter.unformat(value);
        int stateCode = Integer.parseInt(value.substring(value.length() - 4, value.length() - 2));
        return stateCode >= CODIGO_SAO_PAULO && stateCode <= CODIGO_EXTERIOR_ZZ;
    }
}
