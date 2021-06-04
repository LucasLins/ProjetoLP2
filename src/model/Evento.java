package model;
import java.util.ArrayList;

public class Evento {
	private String nome;
    private String data;
    private String duracao;
    private ArrayList<Trabalho> trabalhos = new ArrayList<>();
	private ArrayList<Voluntario> patrocinadores = new ArrayList<>();
    private String objetivo;
    private ArrayList<Gasto> gastos = new ArrayList<>();
    private Funcionario responsavel;

	public Evento(String nome, String data, String duracao, ArrayList<Trabalho> trabalhos, ArrayList<Voluntario> patrocinadores, String objetivo, ArrayList<Gasto> gastos, Funcionario responsavel) {
		this.nome = nome;
		this.data = data;
		this.duracao = duracao;
		this.trabalhos.addAll(trabalhos);
		this.patrocinadores.addAll(patrocinadores);
		this.objetivo = objetivo;
		this.gastos.addAll(gastos);
		this.responsavel = responsavel;
	}

	public Evento(String nome, String data, String duracao, String objetivo, ArrayList<Gasto> gastos,Funcionario responsavel) { // Construtor sem atribuir trabalhos
		this.nome = nome;
		this.data = data;
		this.duracao = duracao;
		this.objetivo = objetivo;
		this.gastos.addAll(gastos);
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
	
	public void setTrabalhos(ArrayList<Trabalho> trabalhos) {
		this.trabalhos.addAll(trabalhos);
	}

	public ArrayList<Voluntario> getPatrocinadores() {
		return patrocinadores;
	}

	public void setPatrocinadores(ArrayList<Voluntario> patrocinadores) {
		this.patrocinadores.addAll(patrocinadores);
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
		this.gastos.addAll(gastos);
	}
	
	public Funcionario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Funcionario responsavel) {
		this.responsavel = responsavel;
	}
	
	public void addPatrocinador(Voluntario vol){
		this.patrocinadores.add(vol);
	}
	
	public String infoGastos(){
		String listaGastos = "";
		for(int i = 0; i<gastos.size(); i++){
			listaGastos += gastos.get(i).toString();
		}
		return listaGastos;
	}
	
	public String infoTrabalhos(){
		String listaTrabalhos = "";
		for(int i = 0; i<trabalhos.size(); i++){
			listaTrabalhos += trabalhos.get(i);
		}
		return listaTrabalhos;
	}
	
	public String infoPatrocinadores(){
		String listaPatrocinadores = "";
		for(int i = 0; i<patrocinadores.size(); i++){
			listaPatrocinadores += patrocinadores.get(i);
		}
		return listaPatrocinadores;
	}
	
	public boolean verificarPatrocinadorRepetido(Voluntario vol){
		boolean repetido = false;
		for(int i = 0; i<patrocinadores.size(); i++){
			if(patrocinadores.get(i) == vol){
				i = patrocinadores.size();
				repetido = true;
			}
		}
		return repetido;
	}
	
	public void setVoluntarioTrabalho(int index, Voluntario vol){
		this.trabalhos.get(index).setVol(vol);
	}

	public double gerarTotalGastos(){
		double total = 0;
		for(int i = 0; i<gastos.size(); i++){
			total += gastos.get(i).getValor();
		}
		return total;
	}
	
	@Override
	public String toString() {
		return "Evento:\n" + "Nome:" + nome + ", Data:" + data + ", Duração:" + duracao + ", Objetivo:" + objetivo + "\nGastos:\n" + infoGastos() + "\nResponsável:\n" + responsavel;
	}
	
}
