
package model;

import java.util.ArrayList;

public class Doacao {
    private Voluntario vol;
    private double doacaoDinheiro = 0;
    private ArrayList<Item> itens;
    private boolean repetir;
    private String modoEntrega;
    private boolean entregue;
    private String data;
    
	public Doacao(Voluntario vol, double doacaoDinheiro, ArrayList<Item> itens, boolean repetir, String modoEntrega,
			boolean entregue, String data) {
		super();
		this.vol = vol;
		this.doacaoDinheiro = doacaoDinheiro;
		this.itens = itens;
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

	@Override
	public String toString() {
		return "Doacao{" + "vol=" + vol + ", doacaoDinheiro=" + doacaoDinheiro + ", itens=" + itens + ", repetir=" + repetir + ", modoEntrega=" + modoEntrega + ", entregue=" + entregue + ", data=" + data + '}';
	}

}
