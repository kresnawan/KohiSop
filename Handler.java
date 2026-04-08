import menu.MenuItem;
import cart.CartItem;

public class Handler {
    public static void handleExit(KohiSop app) {
        System.out.println("Terima kasih telah menggunakan KohiSop!\n");
        app.input.close();
        System.exit(0);
    }

    public static void handleHelp(KohiSop app) {
        app.help.display();
    }

    public static void handleMenu(KohiSop app) {
        app.menu.display();
    }

    public static void handleDisplayCart(KohiSop app) {
        app.cart.display();
    }

    public static void handleAddToCart(KohiSop app) {
        String kode = app.input.next();
        MenuItem chosen = app.menu.getMenu(kode);
        if (chosen == null) {
            System.out.printf("Menu dengan kode '%s' tidak ditemukan :(\n", kode);
        } else {
            System.out.printf("[Pemesanan] Anda akan memesan %s seharga Rp %.0f\n", chosen.nama, chosen.harga);
            while (true) {
                try {
                    System.out.print("[Pemesanan] ");
                    System.out.print("Masukkan jumlah : ");

                    app.input.nextLine();
                    String jumlah = app.input.nextLine();
                    int jumlahAsInt;

                    if (jumlah.equals("S") || jumlah.equals("0")) {
                        System.out.println("Pemesanan dibatalkan");
                        break;
                    } else if (jumlah.toLowerCase().equals("cc")) {
                        System.out.println("Pemesanan dibatalkan");
                        handleExit(app);
                        break;
                    } else if (jumlah.equals("")) {
                        jumlahAsInt = 1;
                    } else {
                        jumlahAsInt = Integer.parseInt(jumlah);
                    } 

                    try {
                        app.cart.add(chosen, jumlahAsInt);
                        System.out.println("Berhasil ditambahkan");
                    } catch (Exception e) {
                        System.out.println("[Pemesanan] " + e.getMessage());
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("\nOh no, input anda tidak valid!\n");
                    app.input.nextLine();
                    continue;
                }
            }
        }
    }

    public static void handleClearConsole() {
        for (int j = 0; j < 100; j++)
        System.out.println();
    }

    public static void handleCheckout(KohiSop app) {
        if (app.cart.items.isEmpty()) {
            System.out.println("Keranjang kamu masih kosong :_|");
        } else {
            String str = String.format("%-72s", "");
            System.out.printf(" %-68s \n", str.replace(" ", "-"));

            System.out.printf("|  %-68s  | \n", "");
            System.out.printf("|  %-68s  | \n", "KohiSop");
            System.out.printf("|  %-48s %19s  | \n", "Pesanan Anda", "IDR");
            System.out.printf("|  %-68s  | \n", "");
            System.out.printf("|  %-4s  %-35s  %7s  %7s  %7s  | \n", "Kode", "Nama", "Harga", "Jumlah", "Total");
            System.out.printf("|  %-68s  | \n", "");

            // Loop
            for (CartItem c : app.cart.items) {
                System.out.printf("|  %-4s  %-35s  %7s  %7s  %7s  | \n", c.menu.kode, c.menu.nama, c.menu.harga, c.amount, c.menu.harga * c.amount);
                System.out.printf("|  %-4s  %-10s %-24s  %7s  %7s  %7s  | \n", "", "\\_ Pajak: ", "25%", "1", "", "");
                System.out.printf("|  %-68s  | \n", "");
            }
            
            System.out.printf("|  %-68s  | \n", "");

            System.out.printf("|  %-4s  %-53s  %7s  | \n", "", "Total (Tanpa Pajak, Diskon, Biaya Admin)", 100);
            System.out.printf("|  %-4s  %-35s  %7s  %7s  %7s  | \n", "", "Diskon", "", "", 99);
            System.out.printf("|  %-4s  %-35s  %7s  %7s  %7s  | \n", "", "Biaya Admin", "", "", 99);
            System.out.printf("|  %-4s  %-53s  %7s  | \n", "", "Total Pajak", 100);
            
            System.out.printf("|  %-68s  | \n", "");
            System.out.printf("|  %-4s  %-35s  %7s  %7s  %7s  | \n", "", "GRAND TOTAL", "", "", 99);
            System.out.printf("|  %-68s  | \n", "");

            System.out.printf(" %-68s \n", str.replace(" ", "-"));
        }
    }
}
