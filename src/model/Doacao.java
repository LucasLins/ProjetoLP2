
package model;

public class Doacao {
    Voluntario vol;
    double doacaoDinheiro = 0;
    String doacaoProduto;
    int repetir; //0 = não se repetirá | 1 = se repetirar ao decorrer dos meses
    int modoEntrega; // 0 = levará ao local | 1 = pegar no local;
    int entregue = 0; // 0 = Entrega não foi feita | 1 = Entrega concluída
    String data;

    public Doacao(Voluntario vol, String doacaoProduto, int repetir, int modoEntrega, String data) {
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

    public int getRepetir() {
        return repetir;
    }

    public void setRepetir(int repetir) {
        this.repetir = repetir;
    }

    public int getModoEntrega() {
        return modoEntrega;
    }

    public void setModoEntrega(int modoEntrega) {
        this.modoEntrega = modoEntrega;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getEntregue() {
        return entregue;
    }

    public void setEntregue(int entregue) {
        this.entregue = entregue;
    }
        
}
