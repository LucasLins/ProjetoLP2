package model;

public class VoluntarioPF extends Voluntario {
    private String cpf;
    private String sexo;
    private String turno;

    public VoluntarioPF(String nome, String telefone, Endereco endereco, String cpf, String sexo, String turno) {
        super(nome, telefone, endereco);
        this.cpf = cpf;
        this.sexo = sexo;
        this.turno = turno;
    }

    public VoluntarioPF() {}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "VoluntarioPF{" + "cpf=" + cpf + ", sexo=" + sexo + ", turno=" + turno + '}';
    }

}
