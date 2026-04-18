package payment;

public abstract class ChannelPembayaran {
    public String nama;
    public abstract double hitungDiskon(double totalTagihan);
    public abstract double getBiayaAdmin();
    public abstract boolean cekSaldo(double totalTagihan, double saldoPembeli);
}