package model;

public class VoluntarioPJ extends Voluntario {
    private String cnpj;
    private String incestadual;

    public VoluntarioPJ(String nome, String telefone, Endereco endereco, String cnpj, String incestadual) {
        super(nome, telefone, endereco);
        this.cnpj = cnpj;
        this.incestadual = incestadual;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIncestadual() {
        return incestadual;
    }

    public void setIncestadual(String incestadual) {
        this.incestadual = incestadual;
    }

    @Override
    public String toString() {
        return "VoluntarioPJ{" + "cnpj=" + cnpj + ", incestadual=" + incestadual + '}';
    }
	
}
