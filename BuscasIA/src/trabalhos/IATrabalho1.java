package trabalhos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import exemplos.HLCA;
import busca.BuscaLargura;
import busca.Estado;
import busca.MostraStatusConsole;
import busca.Nodo;

public class IATrabalho1 implements Estado {

	private List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	private List<Disciplina> disciplinasAprovadas = new ArrayList<Disciplina>();
	private String[] grade = new String[10];
	private static int metaDisciplinas = 10;

	public static void main(String[] args) {
		Disciplina disciplinaA1 = new Disciplina("A1", null, null);
		Disciplina disciplinaB2 = new Disciplina("B1", null, null);
		Disciplina disciplinaC3 = new Disciplina("C1", null, null);
		Disciplina disciplinaD4 = new Disciplina("D1", null, null);
		Disciplina disciplinaE5 = new Disciplina("E1", null, null);

		Disciplina disciplina1 = new Disciplina("A", disciplinaA1,
				Horario.SEG_18_20, Horario.SEG_20_22);
		Disciplina disciplina2 = new Disciplina("B", disciplinaB2,
				Horario.TER_18_20, Horario.TER_20_22);
		Disciplina disciplina3 = new Disciplina("C", disciplinaC3,
				Horario.QUA_18_20, Horario.QUA_20_22);
		Disciplina disciplina4 = new Disciplina("D", disciplinaD4,
				Horario.QUI_18_20, Horario.QUI_20_22);
		Disciplina disciplina5 = new Disciplina("E", disciplinaE5,
				Horario.SEX_18_20, Horario.SEX_20_22);

		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		disciplinas.add(disciplina1);
		disciplinas.add(disciplina2);
		disciplinas.add(disciplina3);
		disciplinas.add(disciplina4);
		disciplinas.add(disciplina5);

		List<Disciplina> disciplinasAprovadas = new ArrayList<Disciplina>();
		disciplinasAprovadas.add(disciplinaA1);
		disciplinasAprovadas.add(disciplinaB2);
		disciplinasAprovadas.add(disciplinaC3);
		disciplinasAprovadas.add(disciplinaD4);
		// disciplinasAprovadas.add(disciplinaE5);

		IATrabalho1 problema = new IATrabalho1(disciplinas,
				disciplinasAprovadas);

		// Nodo s = new AEstrela().busca(problema);
		Nodo s = new BuscaLargura(new MostraStatusConsole()).busca(problema);
		// Nodo s = new AEstrela(new MostraStatusConsole()).busca(problema);
		// Nodo s = new BuscaIterativo(new
		// MostraStatusConsole()).busca(problema);
		// Nodo s = new BuscaProfundidade(25,new
		// MostraStatusConsole()).busca(problema);
		// Nodo s = new BuscaBidirecional(new
		// MostraStatusConsole()).busca(problema, getEstadoMeta());

		while (s == null && metaDisciplinas > 0) {
			metaDisciplinas--;
			s = new BuscaLargura(new MostraStatusConsole()).busca(problema);
		}
		if (metaDisciplinas == 0) {
			System.out.println("Sem solução");
		} else {
			System.out.println("Meta " + metaDisciplinas + ", Solução ("
					+ s.getProfundidade() + ")= " + s.montaCaminho());
		}
	}

	public IATrabalho1(List<Disciplina> disciplinas,
			List<Disciplina> disciplinasAprovadas) {
		this.disciplinas = disciplinas;
		this.disciplinasAprovadas = disciplinasAprovadas;
	}

	public IATrabalho1(List<Disciplina> disciplinas,
			List<Disciplina> disciplinasAprovadas, String[] grade) {
		this.disciplinas = disciplinas;
		this.disciplinasAprovadas = disciplinasAprovadas;
		this.grade = grade;
	}

	@Override
	public int custo() {
		return 1;
	}

	@Override
	public boolean ehMeta() {
		int disciplinasEncaixadas = 0;
		for (int i = 0; i < metaDisciplinas; i++) {
			if (this.grade[i] != null) {
				disciplinasEncaixadas++;
			}
		}
		return disciplinasEncaixadas == metaDisciplinas;
	}

	@Override
	public List<Estado> sucessores() {
		List<Estado> suc = new LinkedList<Estado>();
		for (int i = 0; i < disciplinas.size(); i++) {
			Disciplina preRequisito = disciplinas.get(i).getPreRequisito();
			if (preRequisito == null) {
				this.adicionaDisciplina(suc, i);
			} else {
				for (Disciplina aprovada : disciplinasAprovadas) {
					if (aprovada.getNome() == preRequisito.getNome()) {
						this.adicionaDisciplina(suc, i);
						break;
					}
				}
			}
		}
		return suc;
	}

	private void adicionaDisciplina(List<Estado> suc, int posicao) {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>(
				this.disciplinas);
		Disciplina disciplina = disciplinas.remove(posicao);
		List<String> grade = new ArrayList<String>(Arrays.asList(this.grade));
		IATrabalho1 estadoNovo = new IATrabalho1(disciplinas,
				disciplinasAprovadas,
				grade.toArray(new String[this.grade.length]));
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

	private void insereDisciplina(IATrabalho1 estado, Disciplina disciplina,
			int posicao) {
		estado.grade[posicao] = disciplina.getNome();
	}

	@Override
	public int hashCode() {
		return grade.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IATrabalho1) {
			IATrabalho1 param = (IATrabalho1) obj;
			for (int i = 0; i < 10; i++) {
				if (param.grade[i] == null && this.grade[i] == null)
					continue;

				if ((param.grade[i] == null && this.grade[i] != null)
						&& (param.grade[i] != null && this.grade[i] == null))
					return false;

				if (!param.grade[i].equals(this.grade[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer r = new StringBuffer("\n");
		for (int i = 0; i < 10; i++) {
			r.append(grade[i] + "-");
		}
		r.append("\n");
		return r.toString();
	}

}
