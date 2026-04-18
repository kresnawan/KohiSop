package payment;

public class Tunai extends ChannelPembayaran {
    @Override
    public double hitungDiskon(double totalTagihan) {
        return 0; 
    }

    @Override
    public double getBiayaAdmin() {
        return 0; 
    }

    @Override
    public boolean cekSaldo(double totalTagihan, double saldoPembeli) {
        return true;
    }

    public Tunai() {
        this.nama = "Tunai";
    }
}