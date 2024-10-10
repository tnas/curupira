package br.com.tnas.curupira.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.ValidationMessage;
import br.com.tnas.curupira.format.CNPJFormatter;
import br.com.tnas.curupira.validation.error.CNPJError;

/**
 * Representa um validador de CNPJ.
 * 
 * @author Leonardo Bessa
 */
public class CNPJValidator extends DocumentoValidator<CNPJFormatter> {

    private boolean isIgnoringRepeatedDigits;

    /**
     * Este considera, por padrão, que as cadeias não estão formatadas e utiliza um
     * {@linkplain SimpleMessageProducer} para geração de mensagens.
     */
    public CNPJValidator() {
    	this.formatter = new CNPJFormatter();
    }

    /**
     * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
     * mensagens. Leva em conta se o valor está ou não formatado.
     * 
     * @param isFormatted
     *            considera cadeia no formato de CNPJ: "dd.ddd.ddd/dddd-dd" onde
     *            "d" é um dígito decimal.
     */
    public CNPJValidator(boolean isFormatted) {
    	this();
		this.isFormatted  = isFormatted;
		this.messageProducer = new SimpleMessageProducer();
    }
    
    public CNPJValidator(boolean isFormatted, boolean isIgnoringRepeatedDigits) {
    	this();
        this.isFormatted = isFormatted;
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
        this.messageProducer = new SimpleMessageProducer();
    }

    public CNPJValidator(MessageProducer messageProducer, boolean isFormatted, boolean isIgnoringRepeatedDigits) {
    	this();
        this.messageProducer = messageProducer;
        this.isFormatted = isFormatted;
        this.isIgnoringRepeatedDigits = isIgnoringRepeatedDigits;
    }

    /**
     * <p>
     * Construtor do Validador de CNPJ. Leva em consideração se o valor está formatado.
     * </p>
     * <p>
     * 
     * @param messageProducer
     *            produtor de mensagem de erro.
     * @param isFormatted
     *            considera cadeia no formato de CNPJ: "dd.ddd.ddd/dddd-dd" onde
     *            "d" é um dígito decimal.
     */
    public CNPJValidator(MessageProducer messageProducer, boolean isFormatted) {
    	this();
		this.messageProducer = messageProducer;
		this.isFormatted  = isFormatted;
    }
    
    public CNPJValidator(MessageProducer messageProducer){
		this.messageProducer = messageProducer;
    }

    @Override
    public List<ValidationMessage> invalidMessagesFor(String cnpj) {

    	List<ValidationMessage> errors = new ArrayList<ValidationMessage>();    	
        
        if (cnpj != null) {

        	if(isFormatted != this.getFormatedPattern().matcher(cnpj).matches()) {
        		errors.add(messageProducer.getMessage(CNPJError.INVALID_FORMAT));
        	}
        	
        	String unformatedCNPJ = null;
        	try{
				unformatedCNPJ = new CNPJFormatter().unformat(cnpj);
        	}catch(IllegalArgumentException e){
        		errors.add(messageProducer.getMessage(CNPJError.INVALID_DIGITS));
        		return errors;
        	}
        	
            if(unformatedCNPJ.length() != 14 || !unformatedCNPJ.matches("[0-9]*")){
            	errors.add(messageProducer.getMessage(CNPJError.INVALID_DIGITS));
            }
            
            if ((!isIgnoringRepeatedDigits) && hasAllRepeatedDigits(unformatedCNPJ)) {
                errors.add(messageProducer.getMessage(CNPJError.REPEATED_DIGITS));
            }
           
            String cnpjSemDigito = unformatedCNPJ.substring(0, unformatedCNPJ.length() - 2);
            String digitos = unformatedCNPJ.substring(unformatedCNPJ.length() - 2);

			String digitosCalculados = calculaDigitos(cnpjSemDigito);

            if(!digitos.equals(digitosCalculados)){
            	errors.add(messageProducer.getMessage(CNPJError.INVALID_CHECK_DIGITS));
            }
            
        }
        return errors;
    }

	/**
	 * Faz o cálculo dos digitos usando a lógica de CNPJ
	 * 
	 * @return String os dois dígitos calculados.
	 */
    @Override
	protected String calculaDigitos(String cnpjSemDigito) {
		DigitoPara digitoPara = new DigitoPara(cnpjSemDigito);
		digitoPara.complementarAoModulo().trocandoPorSeEncontrar("0",10,11).mod(11);
		
		String digito1 = digitoPara.calcula();
		digitoPara.addDigito(digito1);
		String digito2 = digitoPara.calcula();
		
		return digito1 + digito2;
	}

	@Override
	protected Pattern getFormatedPattern() {
		return CNPJFormatter.FORMATED;
	}

	@Override
	protected Pattern getUnformatedPattern() {
		return CNPJFormatter.UNFORMATED;
	}

	@Override
	protected int getNoCheckDigitsSize() {
		return CNPJFormatter.NO_CHECKDIGITS_SIZE;
	}
    
}