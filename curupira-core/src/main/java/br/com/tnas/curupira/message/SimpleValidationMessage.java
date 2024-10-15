package br.com.tnas.curupira.message;

/**
 * Implementação básica e imutável. Apenas guarda a mensagem.
 * 
 * @author Fabio Kung
 */
public class SimpleValidationMessage implements ValidationMessage {
    private final String message;

    public SimpleValidationMessage(String message) {
        this.message = message;
    }

    /**
     * @see br.com.tnas.curupira.message.caelum.stella.ValidationMessage#getMessage()
     */
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public String toString() {
    	return this.getMessage();
    }
}
