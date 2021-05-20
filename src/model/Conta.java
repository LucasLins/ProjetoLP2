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
public class Conta {
	private int id;
	private String usuario;
	private String senha;
	private String tipo; // Gestor, Funcionário, VoluntárioPF, VoluntárioPJ

	public Conta(int id, String usuario, String senha, String tipo) {
		this.id = id;
		this.usuario = usuario;
		this.senha = senha;
		this.tipo = tipo;
	}

	public Conta() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String toString() {
		return "Conta{" + "id=" + id + ", usuario=" + usuario + ", senha=" + senha + ", tipo=" + tipo + '}';
	}
	
}
