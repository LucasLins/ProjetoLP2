package model;
import java.util.ArrayList;

public class Caixa {
    
    private double saldo;
    private ArrayList<Double> extrato = new ArrayList<Double>();

	
    public Caixa(double saldo) {
	this.saldo = saldo;
    }
	
    public Caixa() {
	
    }

    public double getSaldo() {
	return saldo;
    }

    public void setSaldo(double saldo) {
	this.saldo = saldo;
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
