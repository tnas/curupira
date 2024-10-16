package br.com.tnas.curupira.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.tnas.curupira.message.MessageProducer;
import br.com.tnas.curupira.message.ValidationMessage;
import br.com.tnas.curupira.validator.error.InvalidStateException;
import br.com.tnas.curupira.validator.error.InvalidValue;

public class RejectAllValidator<T> implements Validator<T> {

    private final MessageProducer messageProducer;

    private final List<InvalidValue> invalidValues;

    public RejectAllValidator(MessageProducer messageProducer, InvalidValue... invalidValues) {
        this.messageProducer = messageProducer;
        this.invalidValues = Arrays.asList(invalidValues);
    }

    public void assertValid(T value) {
        List<InvalidValue> errors = this.invalidValues;
        if (!errors.isEmpty()) {
            throw new InvalidStateException(generateValidationMessages(errors));
        }
    }

    public List<ValidationMessage> invalidMessagesFor(T object) {
        List<ValidationMessage> invalidMessages = new ArrayList<ValidationMessage>();
        for (InvalidValue invalidValue : this.invalidValues) {
            invalidMessages.add(messageProducer.getMessage(invalidValue));
        }
        return invalidMessages;
    }

    public boolean isEligible(T object) {
        return true;
    }

    private List<ValidationMessage> generateValidationMessages(List<InvalidValue> invalidValues) {
        List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
        for (InvalidValue invalidValue : invalidValues) {
            ValidationMessage message = messageProducer.getMessage(invalidValue);
            messages.add(message);
        }
        return messages;
    }

    public T generateRandomValid() {
        throw new UnsupportedOperationException("Operação não suportada pelo validador");
    }

}
