
package model;

public class Doacao {
    private Voluntario vol;
    private double doacaoDinheiro = 0;
    private String doacaoProduto;
    private boolean repetir;
    private String modoEntrega;
    private boolean entregue;
    private String data;

    public Doacao(Voluntario vol, String doacaoProduto, boolean repetir, String modoEntrega, String data) {
        this.vol = vol;
        this.doacaoProduto = doacaoProduto;
        this.repetir = repetir;
        this.modoEntrega = modoEntrega;
        this.data = data;
    }
    
    public Doacao(){}

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

    public String getDoacaoProduto() {
        return doacaoProduto;
    }

    public void setDoacaoProduto(String doacaoProduto) {
        this.doacaoProduto = doacaoProduto;
    }

    public boolean getRepetir() {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean getEntregue() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }

}
