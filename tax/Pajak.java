package tax;

import menu.MenuItem;

public abstract class Pajak {
    public abstract double hitung(MenuItem item, int jumlah);
}