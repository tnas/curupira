package br.com.tnas.curupira.type;

import static br.com.tnas.curupira.type.Estado.AC;
import static br.com.tnas.curupira.type.Estado.AL;
import static br.com.tnas.curupira.type.Estado.AM;
import static br.com.tnas.curupira.type.Estado.AP;
import static br.com.tnas.curupira.type.Estado.BA;
import static br.com.tnas.curupira.type.Estado.CE;
import static br.com.tnas.curupira.type.Estado.DF;
import static br.com.tnas.curupira.type.Estado.ES;
import static br.com.tnas.curupira.type.Estado.GO;
import static br.com.tnas.curupira.type.Estado.MA;
import static br.com.tnas.curupira.type.Estado.MG;
import static br.com.tnas.curupira.type.Estado.MS;
import static br.com.tnas.curupira.type.Estado.MT;
import static br.com.tnas.curupira.type.Estado.PA;
import static br.com.tnas.curupira.type.Estado.PB;
import static br.com.tnas.curupira.type.Estado.PE;
import static br.com.tnas.curupira.type.Estado.PI;
import static br.com.tnas.curupira.type.Estado.PR;
import static br.com.tnas.curupira.type.Estado.RJ;
import static br.com.tnas.curupira.type.Estado.RN;
import static br.com.tnas.curupira.type.Estado.RO;
import static br.com.tnas.curupira.type.Estado.RR;
import static br.com.tnas.curupira.type.Estado.RS;
import static br.com.tnas.curupira.type.Estado.SC;
import static br.com.tnas.curupira.type.Estado.SE;
import static br.com.tnas.curupira.type.Estado.SP;
import static br.com.tnas.curupira.type.Estado.TO;

import java.util.EnumSet;
import java.util.Set;

/**
 * Representa as divisões regionais do território brasileiro.
 *
 * @author <a href="mailto:hprange@gmail.com">Henrique Prange</a>
 */
public enum Regiao {
	CENTRO_OESTE(DF, GO, MT, MS),
	NORDESTE(AL, BA, CE, MA, PB, PE, PI, RN, SE),
	NORTE(AC, AM, AP, PA, RO, RR, TO),
	SUDESTE(ES, MG, RJ, SP),
	SUL(PR, RS, SC);

	private final Set<Estado> estados;

	private Regiao(Estado... estados) {
		this.estados = EnumSet.of(estados[0], estados);
	}

	/**
	 * A lista de estados que compõem essa região.
	 *
	 * @return Retorna uma lista dos estados que compõem essa região.
	 */
	public Set<Estado> estados() {
		return estados;
	}

	/**
	 * Verifica se o estado informado faz parte dessa região.
	 *
	 * @param estado Um estado do território brasileiro.
	 * @return Retorna {@code true} se o estado fizer parte dessa região ou {@code false} caso contrário.
	 */
	public boolean compostaPor(Estado estado) {
		return estados().contains(estado);
	}
}
