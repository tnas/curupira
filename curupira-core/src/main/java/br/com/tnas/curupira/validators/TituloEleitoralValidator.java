package br.com.tnas.curupira.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import br.com.tnas.curupira.DigitoGenerator;
import br.com.tnas.curupira.DigitoPara;
import br.com.tnas.curupira.MessageProducer;
import br.com.tnas.curupira.SimpleMessageProducer;
import br.com.tnas.curupira.format.TituloEleitoralFormatter;
import br.com.tnas.curupira.type.Estado;
import br.com.tnas.curupira.validation.error.TituloEleitoralError;
import br.com.tnas.curupira.validators.rules.CheckDigitsRule;
import br.com.tnas.curupira.validators.rules.ElectoralStateCodeRule;
import br.com.tnas.curupira.validators.rules.FormattingRule;
import br.com.tnas.curupira.validators.rules.NullRule;
import br.com.tnas.curupira.validators.rules.UnformattingRule;
import br.com.tnas.curupira.validators.rules.ValidationRule;

/**
 * Representa um validador de Título de Eleitor. O algoritmo utilzado foi
 * basaedo na seguinte referência.
 * 
 * http://www.tre-al.gov.br/unidades/corregedoria/resolucoes/res21538.pdf
 * <p>
 * Art. 12. Os tribunais regionais eleitorais farão distribuir, observada a
 * seqüência numérica fornecida pela secretaria de informática, às zonas
 * eleitorais da respectiva circunscrição, séries de números de inscrição
 * eleitoral, a serem utilizados na forma deste artigo.
 * </p>
 * <p>
 * Parágrafo único. O número de inscrição compor-se-á de até 12 algarismos, por
 * unidade da Federação, assim discriminados:
 * </p>
 * 
 * a) os oito primeiros algarismos serão seqüenciados,desprezando-se, na
 * emissão, os zeros à esquerda;
 * 
 * b) os dois algarismos seguintes serão representativos da unidade da Federação
 * de origem da inscrição, conforme códigos constantes da seguinte tabela:
 * 
 * <ul>
 * <li>01 - São Paulo</li>
 * <li>02 - Minas Gerais</li>
 * <li>03 - Rio de Janeiro</li>
 * <li>04 - Rio Grande do Sul</li>
 * <li>05 - Bahia</li>
 * <li>06 - Paraná</li>
 * <li>07 - Ceará</li>
 * <li>08 - Pernambuco</li>
 * <li>09 - Santa Catarina</li>
 * <li>10 - Goiás</li>
 * <li>11 - Maranhão</li>
 * <li>12 - Paraíba</li>
 * <li>13 - Pará</li>
 * <li>14 - Espírito Santo</li>
 * <li>15 - Piauí</li>
 * <li>16 - Rio Grande do Norte</li>
 * <li>17 - Alagoas</li>
 * <li>18 - Mato Grosso</li>
 * <li>19 - Mato Grosso do Sul</li>
 * <li>20 - Distrito Federal</li>
 * <li>21 - Sergipe</li>
 * <li>22 - Amazonas</li>
 * <li>23 - Rondônia</li>
 * <li>24 - Acre</li>
 * <li>25 - Amapá</li>
 * <li>26 - Roraima</li>
 * <li>27 - Tocantins</li>
 * <li>28 - Exterior (ZZ)</li>
 * </ul>
 * 
 * <p>
 * c) os dois últimos algarismos constituirão dígitos verificadores,
 * determinados com base no módulo 11, sendo o primeiro calculado sobre o número
 * seqüencial e o último sobre o código da unidade da Federação seguido do
 * primeiro dígito verificador.
 * </p>
 * 
 * @author Leonardo Bessa
 */
public class TituloEleitoralValidator extends DocumentoValidator<TituloEleitoralFormatter> {

    private static final List<Estado> estadosSubstitoresDigito = Arrays.asList(Estado.SP, Estado.MG);
    
    /**
     * Construtor do TituloEleitoralValidator. Considera por padrão que as cadeias não estão formatadas.
     * Utiliza um {@linkplain SimpleMessageProducer} para geração de mensagens.
     */
    public TituloEleitoralValidator(){
    	this.formatter = new TituloEleitoralFormatter();
    }

    /**
     * Considera se cadeias não estão formatadas ou não.
     * Utiliza um {@linkplain SimpleMessageProducer} para geração de mensagens.
     * @param isFormatted indica se o número está formatado.
     */
    public TituloEleitoralValidator(boolean isFormatted) {
    	this.messageProducer = new SimpleMessageProducer();
        this.isFormatted = isFormatted;
    }
    
    /**
     * <p>
     * Construtor do Validador de Titulo de Eleitor.
     * </p>
     * 
     * @param messageProducer
     *            produtor de mensagem de erro.
     * @param isFormatted indica se o número está formatado.
     */
    public TituloEleitoralValidator(MessageProducer messageProducer,boolean isFormatted) {
    	this.messageProducer = messageProducer;
        this.isFormatted = isFormatted;
    }
    
    public TituloEleitoralValidator(MessageProducer messageProducer) {
    	this.messageProducer = messageProducer;
    }
    
    @Override
    protected String computeCheckDigits(String tituloSemDigito) {
    	
    	int length = tituloSemDigito.length();

    	String sequencial = tituloSemDigito.substring(0,length - 2);
    	String codigoEstado = tituloSemDigito.substring(length - 2, length);
    	boolean ehEstadoSubstitutorDigito = estadosSubstitoresDigito.contains(Estado.deCodigoEleitoral(codigoEstado));
    	
    	String digito1 = this.geraDigito(ehEstadoSubstitutorDigito, sequencial);
    	String digito2 = this.geraDigito(ehEstadoSubstitutorDigito, codigoEstado + digito1);
    	
		return digito1 + digito2;
	}

    private String geraDigito(boolean ehEstadoSubstitutorDigito, String base) {
    	
    	String digito = new DigitoPara(base).mod(11).calcula();
    	
    	if (ehEstadoSubstitutorDigito) {
    		if (digito.equals("1")) {
    			digito = "0";
    		} else if (digito.equals("0")) {
    			digito = "1";
    		} else {
    			digito = new DigitoPara(base).complementarAoModulo().mod(11).calcula();
    		}
    	} else {
    		digito = digito.equals("1") || digito.equals("0") ? "0" :
    			new DigitoPara(base).complementarAoModulo().mod(11).calcula();
    	}
    	
    	return digito;
    }

	@Override
	public String generateRandomValid() {
		final String[] digitosEstados = Estado.codigosEleitorais();
		
		final String digitosSequenciais = new DigitoGenerator().generate(8);
		final String digitosEstado = digitosEstados[new Random().nextInt(digitosEstados.length)];
		final String tituloSemDigito = digitosSequenciais + digitosEstado;
		final String tituloComDigito = tituloSemDigito + computeCheckDigits(tituloSemDigito);
		if (isFormatted) {
			return new TituloEleitoralFormatter().format(tituloComDigito);
		}
		return tituloComDigito;
	}

	@Override
	protected List<ValidationRule> getValidationRules() {

		var formatter = new TituloEleitoralFormatter();

		return List.of(
				new NullRule(TituloEleitoralError.INVALID_DIGITS),
				new FormattingRule(formatter, this.isFormatted, TituloEleitoralError.INVALID_FORMAT),
				new UnformattingRule(formatter, TituloEleitoralError.INVALID_DIGITS),
				new ElectoralStateCodeRule(formatter, TituloEleitoralError.INVALID_CODIGO_DE_ESTADO),
				new CheckDigitsRule(formatter, this::computeCheckDigits, TituloEleitoralError.INVALID_CHECK_DIGITS)
		);
	}
}
