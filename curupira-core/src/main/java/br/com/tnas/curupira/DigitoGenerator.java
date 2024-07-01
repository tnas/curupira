package br.com.tnas.curupira;

import java.util.Random;

public class DigitoGenerator {
	private static final Random RANDOM = new Random();

	public String generate(int quantidade) {
		final StringBuilder digitos = new StringBuilder();
		for (int i = 0; i < quantidade; i++) {
			digitos.append(RANDOM.nextInt(9) + 1);
		}
		return digitos.toString();
	}
}
