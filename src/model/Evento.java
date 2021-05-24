package model;
import java.util.ArrayList;

public class Evento {
	private String nome;
    private String data;
    private String duracao;
    private ArrayList<Trabalho> trabalhos;
    private String objetivo;
    private ArrayList<Gasto> gastos;
    private Funcionario responsavel;

	public Evento(String nome, String data, String duracao, ArrayList<Trabalho> trabalhos, String objetivo, ArrayList<Gasto> gastos, Funcionario responsavel) {
		this.nome = nome;
		this.data = data;
		this.duracao = duracao;
		this.trabalhos = trabalhos;
		this.objetivo = objetivo;
		this.gastos = gastos;
		this.responsavel = responsavel;
	}

	public Evento(String nome, String data, String duracao, String objetivo, ArrayList<Gasto> gastos, Funcionario responsavel) { // Construtor sem atribuir trabalhos
		this.nome = nome;
		this.data = data;
		this.duracao = duracao;
		this.objetivo = objetivo;
		this.gastos = gastos;
		this.responsavel = responsavel;
	}
	
	

	public Evento() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public ArrayList<Trabalho> getTrabalhos() {
		return trabalhos;
	}

	public void setEquipe(ArrayList<Trabalho> trabalhos) {
		this.trabalhos = trabalhos;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public ArrayList<Gasto> getGastos() {
		return gastos;
	}

	public void setGastos(ArrayList<Gasto> gastos) {
		this.gastos = gastos;
	}

	public Funcionario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Funcionario responsavel) {
		this.responsavel = responsavel;
	}

	@Override
	public String toString() {
		return "Evento{" + "nome=" + nome + ", data=" + data + ", duracao=" + duracao + ", trabalhos=" + trabalhos + ", objetivo=" + objetivo + ", gastos=" + gastos + ", responsavel=" + responsavel + '}';
	}
	
}
