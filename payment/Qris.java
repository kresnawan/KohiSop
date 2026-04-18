package payment;

public class Qris extends ChannelPembayaran {
    @Override
    public double hitungDiskon(double totalTagihan) {
        return totalTagihan * 0.05;
    }

    @Override
    public double getBiayaAdmin() {
        return 0;
    }

    @Override
    public boolean cekSaldo(double totalTagihan, double saldoPembeli) {
        double totalAkhir = totalTagihan - hitungDiskon(totalTagihan) + getBiayaAdmin();
        return saldoPembeli >= totalAkhir;
    }

    public Qris() {
        this.nama = "QRIS";
    }
}