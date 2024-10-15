package br.com.tnas.curupira.validator.error;

import java.util.Arrays;
import java.util.List;

import br.com.tnas.curupira.message.ValidationMessage;

/**
 * Sinaliza uma ocorrência de um valor inválido.
 * 
 * @author leobessa
 * 
 */
public class InvalidStateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final List<ValidationMessage> validationMessages;

    public InvalidStateException(final ValidationMessage validationMessage) {
        this(Arrays.asList(validationMessage));
    }

    public InvalidStateException(List<ValidationMessage> validationMessages) {
        super("Validation errors: " + validationMessages);
        this.validationMessages = validationMessages;
    }

    public List<ValidationMessage> getInvalidMessages() {
        return validationMessages;
    }
}
