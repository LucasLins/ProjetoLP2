
package model;

public class Voluntario {
    private String nome;
    private String telefone;
    private Endereco endereco;

    public Voluntario(String nome, String telefone, Endereco endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Voluntario() {}
    
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
