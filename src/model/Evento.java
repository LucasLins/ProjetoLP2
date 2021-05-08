package model;
import java.util.ArrayList;

public class Evento {
    private String data;
    private String duracao;
    private ArrayList<Voluntario> equipe = new ArrayList<Voluntario>();
    private String objetivo;
    private double orcamento;
    private Funcionario responsavel;
	
	
    public Evento(String data, String tempo, String objetivo, double orcamento, Funcionario responsavel) {
	this.data = data;
	this.duracao = tempo;
	this.objetivo = objetivo;
	this.orcamento = orcamento;
        this.responsavel = responsavel;
    }
 
    public Evento() {
    }


    //Gets e Sets
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

    public String getResponsavel() {
	return responsavel.nome;
    }


    public void setResponsavel(Funcionario responsavel) {
	this.responsavel = responsavel;
    }

    public String toString() {
	return "Evento \n[Data= " + data + "\nTempo= " + duracao + "\nObjetivo= " + objetivo + "\nOrcamento=  " + orcamento + "]";
	}
    
    
    
}
