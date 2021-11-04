package gcpd.webservices;

import gcpd.webservices.models.Aluno;
import gcpd.webservices.models.Turma;
import static poo.es.EntradaESaida.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONException;

import javax.swing.JOptionPane;

public class Client {
	
 	private Socket clientSocket;
    private PrintWriter saida;
    private BufferedReader entrada;
    
    //Inicia a conexão com o servidor
    public void connect(String ip, int port) throws UnknownHostException, IOException {
        clientSocket = new Socket(ip, port);
        saida = new PrintWriter(clientSocket.getOutputStream(), true);
        entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    
    //Encerra a conexão
    public void closeConnection() throws IOException {
        saida.close();
        entrada.close();
        clientSocket.close();
    }

    //Gera a GUI de cadastro de turmas. A validação deve ser implementada posteriormente.
	private static Turma cadastraTurma(int tamanho) {
    	int id, ano;
    	String curso;
    	List<Aluno> alunos = new ArrayList<>();
    	
    	id = tamanho + 1;
    	curso = lerString("Informe o curso", "Cadastrar Turma");
    	do {
    		ano = lerNumeroInteiro("Informe o ano", "Cadastrar Turma");
    		if(ano < 1900 || ano > 2100)
    			msgErro("Ano inválido! Informe um ano entre 1900 e 2100", "ERRO");
    	}while(ano < 1900 || ano > 2100);
    	alunos = cadastraAluno(alunos);
    	Aluno[] alunoArray = new Aluno[alunos.size()];
		alunoArray = alunos.toArray(alunoArray);
    	Turma turma = new Turma(id, ano, curso, alunoArray);
    	return turma;
	}

	//Cadastra e retorna a lista de alunos de uma turma
	private static List<Aluno> cadastraAluno(List<Aluno> alunos) {
		int opcao = 0;
		int id;
		String nome;
		boolean matriculado;
		do {
			id = alunos.size()+1;
			nome = lerString("Informe o nome do aluno", "Cadastrar Aluno");
			matriculado = menu("Aluno está matriculado?", "Cadastrar Aluno", new String[] {"Sim", "Não"}) == 0 ? true : false;
			Aluno aluno = new Aluno(id, nome, matriculado);
			alunos.add(aluno);
			opcao = menu("Cadastrar novo aluno?", "Cadastrar Aluno", new String[] {"Sim", "Não"});
		}while(opcao!=1 && opcao != JOptionPane.CLOSED_OPTION);
		return alunos;
	}

	public static void main(String[] args) {
		Client client = new Client();
		//Esta variável deve ser alterada para corresponder ao IP do servidor.
		String serverName = "127.0.0.1";
		int serverPort = 16000;
		int opcao = 0;
		ArrayList<Turma> turmas = new ArrayList<>();
		
		try {
			//Inicia a conexão
			client.connect(serverName, serverPort);
			//Cadastra novas turmas enquanto o usuário desejar.
			do {
				Turma turma = cadastraTurma(turmas.size());
				turmas.add(turma);
				opcao = menu("Cadastrar nova turma?", "Cadastro de turmas", new String[] {"Sim", "Não"});
			}while(opcao!=1 && opcao != JOptionPane.CLOSED_OPTION);
			Turma[] turmaArray = new Turma[turmas.size()];
			//Transforma o ArrayList em um Array
			turmaArray = turmas.toArray(turmaArray);
			//Formata as turmas em uma string com o formato JSON
			String turmasString = "{\"turmas\":"+Arrays.toString(turmaArray)+"}";
			//Transforma a string em um objeto JSON
			JSONObject turmasJSON = new JSONObject(turmasString);
			//Envia o objeto JSON para o servidor
			client.saida.println(turmasJSON);
			//Recebe a resposta do servidor
			String resposta = client.entrada.readLine();
			String[] arrayResposta = resposta.split("#");
			//Exibe a mensagem requerida para cada turma em uma caixa de diálogo.
			for(String exibir : arrayResposta) {
				msgInfo(exibir, "Resposta do servidor");
			}
			//Encerra a conexão
			client.closeConnection();
			System.exit(0);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Ocorreu um erro na conexão com o servidor. Certifique-se que o servidor está ativo.");
		}
	}

	

}
