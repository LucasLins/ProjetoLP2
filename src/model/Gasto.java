/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
/**
 *
 * @author lucas
 */
public class Gasto {
	private String nome;
	private double valor;

	public Gasto(String nome, double valor) {
		this.nome = nome;
		this.valor = valor;
	}

	public Gasto() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String toString() {
		return String.format("Descrição: %s – Valor: %f\n", this.nome, this.valor);
	}
	
}
