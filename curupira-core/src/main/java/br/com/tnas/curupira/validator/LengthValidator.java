package br.com.tnas.curupira.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.validation.error.ValidationError;

/**
 * Validador para aceitar cadeias de tamanho pré-definido. A cadeia verificada
 * por este validador é a retornada pelo método {@code toString()} do objeto em
 * questão.
 * 
 * @author leonardobessa
 * 
 */
public class LengthValidator implements Validator<Object> {

    private final int validLength;
    private final BaseValidator base;

    /**
     * @param validLength
     *            quantidade de caracteres das cadeias a serem aceitas.
     */
    public LengthValidator(int validLength) {
        this.validLength = validLength;
        base = new BaseValidator();
    }

    /**
     * @param messageProducer
     *            produto de mensagens.
     * @param validLength
     *            quantidade de caracteres das cadeias a serem aceitas.
     * 
     */
    public LengthValidator(MessageProducer messageProducer, int validLength) {
        base = new BaseValidator(messageProducer);
        this.validLength = validLength;
    }

    @Override
    public void assertValid(Object object) {
    	
    	var validationMessages = this.invalidMessagesFor(object);
    	
    	if (!validationMessages.isEmpty()) {
            throw new InvalidStateException(validationMessages);
        }
    }

    @Override
    public List<ValidationMessage> invalidMessagesFor(Object object) {
    	
    	List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
    	
    	if (Objects.isNull(object) || object.toString().length() != validLength) {
    		messages.addAll(this.base.generateValidationMessages(List.of(ValidationError.Length$INVALID_LENGTH)));
    	}
    	
        return messages;
    }
    
    @Override
    public boolean isEligible(Object object) {
        return true;
    }

    @Override
    public Object generateRandomValid() {
        throw new UnsupportedOperationException("Operação não suportada por este validador");
    }
}
