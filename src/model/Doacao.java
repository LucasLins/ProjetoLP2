
package model;

import java.util.ArrayList;

public class Doacao {
    private Voluntario vol;
    private double doacaoDinheiro = 0;
    private ArrayList<Item> itens = new ArrayList<>();
    private boolean repetir;
    private String modoEntrega;
    private boolean entregue;
    private String data;
    
	public Doacao(Voluntario vol, double doacaoDinheiro, ArrayList<Item> itens, boolean repetir, String modoEntrega,
			boolean entregue, String data) {
		this.vol = vol;
		this.doacaoDinheiro = doacaoDinheiro;
		this.itens.addAll(itens);
		this.repetir = repetir;
		this.modoEntrega = modoEntrega;
		this.entregue = entregue;
		this.data = data;
	}

    public Doacao() {}

	public Voluntario getVol() {
		return vol;
	}

	public void setVol(Voluntario vol) {
		this.vol = vol;
	}

	public double getDoacaoDinheiro() {
		return doacaoDinheiro;
	}

	public void setDoacaoDinheiro(double doacaoDinheiro) {
		this.doacaoDinheiro = doacaoDinheiro;
	}

	public ArrayList<Item> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
	}

	public boolean isRepetir() {
		return repetir;
	}

	public void setRepetir(boolean repetir) {
		this.repetir = repetir;
	}

	public String getModoEntrega() {
		return modoEntrega;
	}

	public void setModoEntrega(String modoEntrega) {
		this.modoEntrega = modoEntrega;
	}

	public boolean isEntregue() {
		return entregue;
	}

	public void setEntregue(boolean entregue) {
		this.entregue = entregue;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String infoRepetir(){
		if(this.repetir == false){
			return "Sim";
		}
		else{
			return "Não";
		}
	}
	
	public String infoEntregue(){
		if(this.entregue == false){
			return "Sim";
		}
		else{
			return "Não";
		}
	}

	@Override
	public String toString() {
		return "Doação:\n" + "Voluntário: " + this.vol + ", Valor Dinheiro: " + this.doacaoDinheiro + ", Repetir: " + this.infoRepetir() + ", Modo de entrega: " + this.modoEntrega + ", Entregue: " + this.infoEntregue() + ", Data: " + this.data + "\nItens:\n" + this.itens;
	}

}
