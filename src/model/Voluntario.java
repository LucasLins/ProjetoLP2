
package model;

public class Voluntario {
	private int idConta;
    private String nome;
    private String telefone;
    private Endereco endereco;

    public Voluntario(int idConta, String nome, String telefone, Endereco endereco) {
		this.idConta = idConta;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Voluntario() {}

	public int getIdConta() {
		return idConta;
	}

	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Voluntario{" + "nome=" + nome + ", telefone=" + telefone + ", endereco=" + endereco + '}';
    }

}
