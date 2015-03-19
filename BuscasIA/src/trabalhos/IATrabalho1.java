package trabalhos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import exemplos.HLCA;
import busca.BuscaLargura;
import busca.Estado;
import busca.Nodo;

public class IATrabalho1 implements Estado {

	private List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	private String[] grade = new String[10];

	public static void main(String[] args) {
		Disciplina disciplina1 = new Disciplina("A", null, Horario.SEG_18_20, Horario.SEG_20_22);
		Disciplina disciplina2 = new Disciplina("B", null, Horario.TER_18_20, Horario.TER_20_22);
		Disciplina disciplina3 = new Disciplina("C", null, Horario.QUA_18_20, Horario.QUA_20_22);
		Disciplina disciplina4 = new Disciplina("D", null, Horario.QUI_18_20, Horario.QUI_20_22);
		Disciplina disciplina5 = new Disciplina("E", null, Horario.SEX_18_20, Horario.SEX_20_22);
		
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		disciplinas.add(disciplina1);
		disciplinas.add(disciplina2);
		disciplinas.add(disciplina3);
		disciplinas.add(disciplina4);
		disciplinas.add(disciplina5);
		IATrabalho1 problema = new IATrabalho1(disciplinas);

		// chama busca em largura
		BuscaLargura buscaLargura = new BuscaLargura();

		Nodo nodo = buscaLargura.busca(problema);
		if (nodo == null) {
			System.out.println("Problema sem solução");		
		} else {
			System.out.println("Solução:\n" + nodo.montaCaminho() + "\n");
		}

	}
	
	public IATrabalho1(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public IATrabalho1(List<Disciplina> disciplinas, String[] grade) {
		this.disciplinas = disciplinas;
		this.grade = grade;
	}
	@Override
	public int custo() {
		return 1;
	}

	@Override
	public boolean ehMeta() {
		for (int i = 0; i < this.grade.length; i++) {
			if (this.grade[i] == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Estado> sucessores() {
		List<Estado> suc = new LinkedList<Estado>();
		for (int i = 0; i < disciplinas.size(); i++) {
			this.adicionaDisciplina(suc, i);

		}
		return suc;
	}

	private void adicionaDisciplina(List<Estado> suc, int posicao) {
			List<Disciplina> disciplinas = new ArrayList<Disciplina>(this.disciplinas);
			Disciplina disciplina = disciplinas.remove(posicao);
			List<String> grade = new ArrayList<String>(Arrays.asList(this.grade));
			IATrabalho1 estadoNovo = new IATrabalho1(disciplinas, grade.toArray(new String[this.grade.length]));
			for (int i = 0; i < disciplina.getHorarios().length; i++) {
				Horario horario = disciplina.getHorarios()[i];
				switch (horario) {
				case SEG_18_20:
					insereDisciplina(estadoNovo, disciplina, 0);
					break;
				case SEG_20_22:
					insereDisciplina(estadoNovo, disciplina, 1);
					break;
				case TER_18_20:
					insereDisciplina(estadoNovo, disciplina, 2);
					break;
				case TER_20_22:
					insereDisciplina(estadoNovo, disciplina, 3);
					break;
				case QUA_18_20:
					insereDisciplina(estadoNovo, disciplina, 4);
					break;
				case QUA_20_22:
					insereDisciplina(estadoNovo, disciplina, 5);
					break;
				case QUI_18_20:
					insereDisciplina(estadoNovo, disciplina, 6);
					break;
				case QUI_20_22:
					insereDisciplina(estadoNovo, disciplina, 7);
					break;
				case SEX_18_20:
					insereDisciplina(estadoNovo, disciplina, 8);
					break;
				case SEX_20_22:
					insereDisciplina(estadoNovo, disciplina, 9);
					break;
				default:
					break;
				}
			}
			suc.add(estadoNovo);
	}

	private void insereDisciplina(IATrabalho1 estado, Disciplina disciplina, int posicao) {
		estado.grade[posicao] = disciplina.getNome();
	}
	
	@Override
	public int hashCode() {
		return grade.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IATrabalho1){
			IATrabalho1 param = (IATrabalho1) obj;
			for (int i = 0; i < 10; i++) {
				if (param.grade[i] == null && this.grade[i] == null)
					continue;
				
				if ((param.grade[i] == null && this.grade[i] != null)
				&&  (param.grade[i] != null && this.grade[i] == null))
					return false;
				
				if (!param.grade[i].equals(this.grade[i])){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}
