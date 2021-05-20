package model;
import java.util.ArrayList;

public class Caixa {
    
    private double saldo;
    private ArrayList<Double> extrato;

    public Caixa(double saldo) {
		this.saldo = saldo;
    }
	
    public Caixa() {}

    public double getSaldo() {
		return saldo;
    }

    public void setSaldo(double saldo) {
		this.saldo = saldo;
    }

	public ArrayList<Double> getExtrato() {
		return extrato;
	}

	public void setExtrato(ArrayList<Double> extrato) {
		this.extrato = extrato;
	}

    public double doar(double valor) {
        extrato.add(valor);
	return saldo = saldo + valor;
    }

    public double gastar(double valor) {
        extrato.add(valor);
		return saldo = saldo - valor;
    }
    
    public String toString() {
		return "Caixa:\n Saldo= R$ " + saldo + "\nExtrato:\n [" + extrato + "]";
    
    }
    
}
