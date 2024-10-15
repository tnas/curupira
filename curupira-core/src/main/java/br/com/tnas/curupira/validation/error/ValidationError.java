package br.com.tnas.curupira.validation.error;

public enum ValidationError implements InvalidValue {
	
	INVALID_DIGITS,
	Length$INVALID_LENGTH,
	CNPJ$INVALID_FORMAT,
	CNPJ$INVALID_DIGITS, 
	CNPJ$REPEATED_DIGITS, 
	CNPJ$INVALID_CHECK_DIGITS, 
	CPF$INVALID_FORMAT,
	CPF$INVALID_DIGITS, 
	CPF$REPEATED_DIGITS, 
	CPF$INVALID_CHECK_DIGITS, 
	NIT$INVALID_FORMAT,
	NIT$INVALID_DIGITS, 
	NIT$INVALID_CHECK_DIGITS,
	Renavam$INVALID_FORMAT,
	Renavam$INVALID_DIGITS, 
	Renavam$INVALID_CHECK_DIGITS,
	TituloEleitoral$INVALID_FORMAT,
	TituloEleitoral$INVALID_DIGITS, 
	TituloEleitoral$INVALID_CHECK_DIGITS,
	TituloEleitoral$INVALID_STATE_CODE,
	AgenciaBancoDoBrasil$INVALID_FORMAT,
	AgenciaBancoDoBrasil$INVALID_CHECK_DIGITS
}
