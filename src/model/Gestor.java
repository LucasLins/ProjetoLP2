package model;
import model.Caixa;
public class Gestor {

    public String nome;
    public String sexo;
    public String cpf;
    public String telefone;
    public String endereco;
    
    
    public Gestor(String nome, String sexo, String cpf, String telefone, String endereco) {
        this.nome = nome;
	this.sexo = sexo;
	this.cpf = cpf;
	this.telefone = telefone;
	this.endereco = endereco;
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


    public String getEndereco() {
	return endereco;
	}


    public void setEndereco(String endereco) {
	this.endereco = endereco;
	}


    public String toString() {
	return "Gestor: \nNome= " + nome + "\nSexo= " + sexo + "\nCPF= " + cpf + "\nTelefone= " + telefone + "\nEndereco= "
	+ endereco;
    }
    
    /*public String gerarRelatorio(){
        return Caixa.toString();
    }*/
    
}

