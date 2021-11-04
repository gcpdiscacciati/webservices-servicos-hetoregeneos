package gcpd.webservices.models;

import java.util.Arrays;

public class Turma {
	
	private int id;
	private int ano;
	private String curso;
	private Aluno alunos[];

	public Turma() {
	}

	public Turma(int id, int ano, String curso, Aluno[] alunos) {
		super();
		this.id = id;
		this.ano = ano;
		this.curso = curso;
		this.alunos = alunos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public Aluno[] getAlunos() {
		return alunos;
	}

	public void setAlunos(Aluno[] alunos) {
		this.alunos = alunos;
	}

	//Formata o objeto turma em uma string de formato JSON
	@Override
	public String toString() {
		return "{\"id\":"+ id + ",\"ano\":" + ano + ",\"curso\":\"" + curso + "\",\"alunos\":" + Arrays.toString(alunos) + "}";
	}

}
