package model;


public class Caixa {
    
    private double saldo;

	
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
	return saldo = saldo + valor;
    }

    public double gastar(double valor) {
	return saldo = saldo - valor;
    }

    public String toString() {
	return "Caixa [Saldo= R$ " + saldo + "]";
    }   
    
    
}
