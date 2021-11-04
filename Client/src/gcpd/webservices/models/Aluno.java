package gcpd.webservices.models;

public class Aluno {

	private int id;
	private String nome;
	private boolean matriculado;
	
	public Aluno() {
		id = 0;
		nome = "";
		matriculado = false;
	}

	public Aluno(int id, String nome, boolean matriculado) {
		this.id = id;
		this.nome = nome;
		this.matriculado = matriculado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isMatriculado() {
		return matriculado;
	}

	public void setMatriculado(boolean matriculado) {
		this.matriculado = matriculado;
	}

	//O método toString() formata o objeto Aluno em uma string com o formato JSON
	@Override
	public String toString() {
		return "{\"id\":"+ id + ",\"nome\":\"" + nome + "\",\"matriculado\":" + matriculado + "}";
	}
	

}
