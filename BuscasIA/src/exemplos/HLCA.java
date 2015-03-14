package exemplos;

import java.util.LinkedList;
import java.util.List;

import busca.BuscaLargura;
import busca.Estado;
import busca.Nodo;

/**
 * @author danieldossantos
 *
 */
public class HLCA implements Estado {

	public String getDescricao() {
		return "Uma pessoa, um lobo, um carneiro e um cesto de alface estao a beira \n"+
				"de um rio. Dispondo de um barco no qual pode carregar apenas um dos \n"+
				"outros tres, a pessoa deve transportar tudo para a outra margem. \n"+
				"Determine uma serie de travessias que respeite a seguinte condicao: \n"+
				"em nenhum momento devem ser deixados juntos e sozinhos o lobo e o \n"+
				"carneiro ou o carneiro e o cesto de alface. \n\n";
	}

	final char homem, lobo, carneiro, alface;

	final String op; // Opera�‹o que gerou o estado

	/**
	 * Construtor da Classe que representa os estados
	 * @param h: Homem, que pode ter os seguintes valores: d= direta; e= esquerda
	 * @param l: Lobo, que pode ter os seguintes valores: d= direta; e= esquerda
	 * @param c: Carneiro, que pode ter os seguintes valores: d= direta; e= esquerda
	 * @param a: Alface, que pode ter os seguintes valores: d= direta; e= esquerda
	 * @param o: Conjunto de Opera�›es
	 */
	public HLCA(char h, char l, char c, char a, String o) {
		homem = h;
		lobo = l;
		carneiro = c;
		alface = a;
		op = o;
	}

	/**
	 * Custo para gera�‹o dos estados
	 */
	@Override
	public int custo() {
		return 1;
	}

	/**
	 * Verifica se o estado Ž meta
	 */
	@Override
	public boolean ehMeta() {
		return homem == 'd' && lobo == 'd' && carneiro =='d' && alface == 'd';
	}

	/**
	 * Gera lista de sucessores do nodo 
	 */
	@Override
	public List<Estado> sucessores() {
		List<Estado> suc = new LinkedList<Estado>(); // Lista de sucessores

		levarCarneiro(suc);
		levarLobo(suc);
		levarAlface(suc);
		levarNada(suc);

		return suc;
	}

	/**
	 * Valida se o estado Ž v‡lido de acordo com as regras do problema
	 * @return
	 */
	private boolean ehValido() {
		// lobo e carneiro na mesma margem sem o homem
		if (lobo == carneiro && lobo != homem) {
			return false;
		} 
		// carneiro e alfase na mesma margem sem o homem
		if (carneiro == alface && carneiro != homem) {
			return false;
		}
		return true;
	}

	/**
	 * Homem atravessando sozinho
	 * @param suc
	 */
	private void levarNada(List<Estado> suc) {
		char novaMargem = ladoOposto(homem);
		HLCA novo = new HLCA(novaMargem, lobo, carneiro, alface, "levarNada-" + homem + novaMargem);

		if (novo.ehValido()) {
			suc.add(novo);
		}

	}

	private void levarAlface(List<Estado> suc) {
		if (alface == homem) {
			char novaMargem = ladoOposto(homem);
			HLCA novo = new HLCA(novaMargem, lobo, carneiro, novaMargem, "levarAlface-" + alface + novaMargem);

			if (novo.ehValido()) {
				suc.add(novo);
			}
		}		
	}

	private void levarLobo(List<Estado> suc) {
		if (lobo == homem) {
			char novaMargem = ladoOposto(homem);
			HLCA novo = new HLCA(novaMargem, novaMargem, carneiro, alface, "levarLobo-" + lobo + novaMargem);

			if (novo.ehValido()) {
				suc.add(novo);
			}
		}
	}

	private void levarCarneiro(List<Estado> suc) {
		if (carneiro == homem) {
			char novaMargem = ladoOposto(homem);
			HLCA novo = new HLCA(novaMargem, lobo, novaMargem, alface, "levar Carneiro-" + carneiro + novaMargem);

			if (novo.ehValido()) {
				suc.add(novo);
			}
		}
	}

	private char ladoOposto(char l) {
		if (l == 'e') {
			return 'd';
		} else {
			return 'e';
		}
	}

	public boolean equals(Object o) {
		if (o instanceof HLCA) {
			HLCA e = (HLCA) o;
			return e.homem == this.homem && e.lobo == this.lobo && e.carneiro == this.carneiro && e.alface == this.alface;
		}
		return false;
	}

	/** 
	 * retorna o hashCode desse estado
	 * (usado para poda, conjunto de fechados)
	 */
	public int hashCode() {
		return (""+homem + lobo + carneiro + alface).hashCode();
	}

	public String toString() {
		String dir = ""; // quem esta na dir
		String esq = ""; // quem esta na esq
		if (homem == 'd') {
			dir += 'h';
		} else {
			esq += 'h';
		}
		if (lobo == 'd') {
			dir += 'l';
		} else {
			esq += 'l';
		}
		if (carneiro == 'd') {
			dir += 'c';
		} else {
			esq += 'c';
		}
		if (alface == 'd') {
			dir += 'a';
		} else {
			esq += 'a';
		}
		return esq +"--" + dir + " (" + op + ")\n";
	}

	public static void main(String[] a) {
		HLCA problema = new HLCA('e', 'e', 'e', 'e', "inicial");

		// chama busca em largura
		BuscaLargura buscaLargura = new BuscaLargura();

		Nodo nodo = buscaLargura.busca(problema);

		if (nodo == null) {
			System.out.println("Problema sem solu�‹o");		
		} else {
			System.out.println("Solu�‹o:\n" + nodo.montaCaminho() + "\n");
		}
	}

}
