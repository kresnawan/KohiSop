package menu;

public class MenuItem {
    public String kode;
    public String nama;
    public double harga;
    public MenuType tipe;

    public MenuItem(String kode, String nama, double harga, MenuType tipe) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.tipe = tipe;
    }
}