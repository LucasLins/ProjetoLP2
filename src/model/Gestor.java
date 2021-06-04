package model;

public class Gestor {
	
	private int idConta;
    private String nome;
    private String sexo;
    private String cpf;
    private String telefone;
    private Endereco endereco;
    
    
    public Gestor(int idConta, String nome, String sexo, String cpf, String telefone, Endereco endereco) {
		this.idConta = idConta;
		this.nome = nome;
		this.sexo = sexo;
		this.cpf = cpf;
		this.telefone = telefone;
		this.endereco = endereco;
    }

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


    public String getSexo() {
		return sexo;
    }


    public void setSexo(String sexo) {
		this.sexo = sexo;
    }


    public String getCpf() {
		return cpf;
    }


    public void setCpf(String cpf) {
    	this.cpf = cpf;
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

    public String toString() {
		return "Gestor: \nNome: " + nome + "\nSexo: " + sexo + "\nCPF: " + cpf + "\nTelefone: " + telefone + "\nEndereco: "
	+ endereco;
    }
    
    /*public String gerarRelatorio(){
        return Caixa.toString();
    }*/
    
}

