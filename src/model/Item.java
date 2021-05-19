package model;

public class Item {
	private String nome;
	private double peso;
	private int quantidade;
	
	public Item(String nome, double peso, int quantidade) {
		super();
		this.nome = nome;
		this.peso = peso;
		this.quantidade = quantidade;
	}
	
	public Item() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "Item{" + "nome=" + nome + ", peso=" + peso + ", quantidade=" + quantidade + '}';
	}

}
