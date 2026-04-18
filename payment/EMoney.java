package payment;

public class EMoney extends ChannelPembayaran {
    @Override
    public double hitungDiskon(double totalTagihan) {
        return totalTagihan * 0.07;
    }

    @Override
    public double getBiayaAdmin() {
        return 20;
    }

    @Override
    public boolean cekSaldo(double totalTagihan, double saldoPembeli) {
        double totalAkhir = totalTagihan - hitungDiskon(totalTagihan) + getBiayaAdmin();
        return saldoPembeli >= totalAkhir;
    }

    public EMoney() {
        this.nama = "EMoney";
    }
}