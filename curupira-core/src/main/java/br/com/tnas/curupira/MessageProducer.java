package br.com.tnas.curupira;

import br.com.tnas.curupira.validation.error.InvalidValue;
import br.com.tnas.curupira.validation.error.Validatable;
import br.com.tnas.curupira.validation.error.ValidationError;

/**
 * <p>
 * MessageProducers são responsáveis pela busca de mensagens de validação.
 * </p>
 * <p>
 * Possíveis implementações incluem a busca em base de dados, arquivos xml e
 * ResourceBundles.
 * </p>
 * 
 * @author Fabio Kung
 * @author Leonardo Bessa
 */
public interface MessageProducer {
    /**
     * @param invalidValue
     *            valor inválido ao qual se procura a mensagem associada.
     * @return mensagem de validação associada ao erro.
     */
    ValidationMessage getMessage(InvalidValue invalidValue);
    
    default ValidationMessage getMessage(ValidationError invalidValue, Validatable validatable) {
    	throw new UnsupportedOperationException();
    }
    
    default ValidationMessage getMessage(ValidationError invalidValue) {
    	throw new UnsupportedOperationException();
    }
}
