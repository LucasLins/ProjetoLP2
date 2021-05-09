
package model;

public class Voluntario {
    private String nome;
    private String sexo;
    private String cpf;
    private String telefone;
    private String endereco;
    private String turno;

    public Voluntario(String nome, String sexo, String cpf, String telefone, String endereco, String turno) {
        this.nome = nome;
        this.sexo = sexo;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.turno = turno;
    }

    public Voluntario(){}
    
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
            return "Voluntario [nome=" + nome + ", sexo=" + sexo + ", cpf=" + cpf + ", telefone=" + telefone + ", endereco="
                            + endereco + ", turno=" + turno + "]";
    }

}
