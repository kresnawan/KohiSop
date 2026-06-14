package kitchen;

import java.util.LinkedList;
import menu.MenuItem;

public class AntreanDapur {
    private final LinkedList<MenuItem> listMakanan;
    private final LinkedList<MenuItem> listMinuman;

    public AntreanDapur() {
        listMakanan = new LinkedList<>();
        listMinuman = new LinkedList<>();
    }

    public void masukanPesananBaru(MenuItem menu, String tipe) {
        if (tipe.equalsIgnoreCase("Makanan")) {
            listMakanan.addLast(menu);
        } else if (tipe.equalsIgnoreCase("Minuman")) {
            listMinuman.addLast(menu);
        }
    }

    public void cetakAntreanDapur() {
        System.out.println("=== ANTRIAN DAPUR (MAKANAN) ===");
        if (listMakanan.size() == 0) {
            System.out.println("- Kosong, tidak ada antrian -");
        } else {
            for (int i = 0; i < listMakanan.size(); i++) {
            
                System.out.println((i + 1) + ". " + listMakanan.get(i).toString());
            }
        }

        System.out.println("\n=== ANTRIAN BARISTA (MINUMAN) ===");
        if (listMinuman.size() == 0) {
            System.out.println("- Kosong, tidak ada antrian -");
        } else {
            for (int i = 0; i < listMinuman.size(); i++) {

                System.out.println((i + 1) + ". " + listMinuman.get(i).toString());
            }
        }
        System.out.println("=================================");
    }

    public void makananSelesai() {
        if (listMakanan.size() > 0) {
            MenuItem itemSelesai = listMakanan.removeFirst(); // FIFO
            System.out.println("--> [DAPUR] Makanan " + itemSelesai.toString() + " sudah siap saji!");
        } else {
            System.out.println("Gagal: Antrian makanan emang lagi kosong.");
        }
    }

    public void minumanSelesai() {
        if (listMinuman.size() > 0) {
            MenuItem itemSelesai = listMinuman.removeFirst(); // FIFO
            System.out.println("--> [BARISTA] Minuman " + itemSelesai.toString() + " sudah selesai dibuat!");
        } else {
            System.out.println("Gagal: Antrian minuman emang lagi kosong.");
        }
    }
}