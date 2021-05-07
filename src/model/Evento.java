package model;

public class Evento {
    private String data;
    private String tempo;
    //private ArrayList<String> equipe;// = new ArrayList();
    private String objetivo;
    private double orcamento;
    //private ArrayList<String> parceiros;
    //private Funcionario responsavel;
	
	
    public Evento(String data, String tempo, String objetivo, double orcamento) {
	this.data = data;
	this.tempo = tempo;
	this.objetivo = objetivo;
	this.orcamento = orcamento;
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


    public String getTempo() {
        return tempo;
    }

    
    public void setTempo(String tempo) {
	this.tempo = tempo;
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


    public String toString() {
	return "Evento \n[Data= " + data + "\nTempo= " + tempo + "\nObjetivo= " + objetivo + "\nOrcamento=  " + orcamento + "]";
	}
    
    
    
}
