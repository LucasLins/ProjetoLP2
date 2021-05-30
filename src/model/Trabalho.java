
package model;

public class Trabalho {
    private Voluntario vol;
    private String nome;
    private String descricao;

    public Trabalho(Voluntario vol, String nome, String descricao) {
        this.vol = vol;
        this.nome = nome;
        this.descricao = descricao;
    }

	public Trabalho(String nome, String descricao) { // Construtor sem voluntário
		this.nome = nome;
		this.descricao = descricao;
	}
    
    public Trabalho(){}

    public Voluntario getVol() {
        return vol;
    }

    public void setVol(Voluntario vol) {
        this.vol = vol;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	@Override
	public String toString() {
		return "Nome: " + nome + ", Descrição: " + descricao + "\n";
	}
	
	
    
}
