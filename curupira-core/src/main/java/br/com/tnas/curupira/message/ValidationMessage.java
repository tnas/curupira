package br.com.tnas.curupira.message;

/**
 * Mensagens de erro sobre a validação de um objeto devem implementar essa
 * interface.
 * 
 * @author Fabio Kung
 */
public interface ValidationMessage {
    /**
     * @return mensagem de validação armazenda.
     */
    String getMessage();
}
