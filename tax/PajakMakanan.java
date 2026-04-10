package tax;

import menu.MenuItem;

public class PajakMakanan extends Pajak {
    public double hitung(MenuItem item, int jumlah) {
        double harga = item.harga;
        double total = harga * jumlah;

        if (harga > 50) return total * 0.08;
        else return total * 0.11;
    }
}