package br.com.tnas.curupira.message;

import br.com.tnas.curupira.validator.error.InvalidValue;

/**
 * <p>
 * SimpleMessageProducer é responsável pela geração de mensagens de erro. Estas
 * mensagens são geradas atraves dos nomes das anotoções que representam os
 * erros.
 * </p>
 * 
 * <p>
 * Veja o exemplo:
 * </p>
 * <p>
 * A mesagem do erro representado por {@link ValidationError#}.CPF$INVALID_DIGITS é : <br>
 * CPFError : INVALID DIGITS .
 * </p>
 * 
 * @author Leonardo Bessa
 * @author Thiago Nascimento
 * 
 */
public class SimpleMessageProducer implements MessageProducer {

	private static final String MESSAGE_FORMAT = "%sError : %s";
	
	@Override
	public ValidationMessage getMessage(InvalidValue error) {
		
		var messageComponents = error.name().split("\\$");
		
		return messageComponents.length == 1 ? new SimpleValidationMessage(messageComponents[0].replaceAll("_", " ")) :
				new SimpleValidationMessage(String.format(MESSAGE_FORMAT, messageComponents[0], 
						messageComponents[1].replaceAll("_", " ")));
	}
}
