package trabalhos;

import exemplos.EstadoJarros;


public class Disciplina {
	
	private String nome;
	
	private Disciplina preRequisito;
	
	private Horario[] horarios = new Horario[2];

	public Disciplina(String nome, Disciplina preRequisito, Horario... horarios) {
		super();
		this.nome = nome;
		this.preRequisito = preRequisito;
		this.horarios = horarios;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Disciplina getPreRequisito() {
		return preRequisito;
	}

	public void setPreRequisitos(Disciplina preRequisito) {
		this.preRequisito = preRequisito;
	}

	public Horario[] getHorarios() {
		return horarios;
	}

	public void setHorarios(Horario[] horarios) {
		this.horarios = horarios;
	}
	
	
} 
