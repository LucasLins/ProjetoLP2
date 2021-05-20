package model;
import java.util.ArrayList;

public class Evento {
    private String data;
    private String duracao;
    private ArrayList<Voluntario> equipe;
    private String objetivo;
    private double orcamento;
    private Funcionario responsavel;

	public Evento(String data, String duracao, ArrayList<Voluntario> equipe, String objetivo, double orcamento, Funcionario responsavel) {
		this.data = data;
		this.duracao = duracao;
		this.equipe = equipe;
		this.objetivo = objetivo;
		this.orcamento = orcamento;
		this.responsavel = responsavel;
	}

	public Evento() {}

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

	public ArrayList<Voluntario> getEquipe() {
		return equipe;
	}

	public void setEquipe(ArrayList<Voluntario> equipe) {
		this.equipe = equipe;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public double getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(double orcamento) {
		this.orcamento = orcamento;
	}

	public Funcionario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Funcionario responsavel) {
		this.responsavel = responsavel;
	}

	public String toString() {
		return "Evento{" + "data=" + data + ", duracao=" + duracao + ", equipe=" + equipe + ", objetivo=" + objetivo + ", orcamento=" + orcamento + ", responsavel=" + responsavel + '}';
	}
	
}
