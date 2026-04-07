import menu.MenuItem;

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
                    String jumlah = app.input.next();
                    if (jumlah.equals("S") || jumlah.equals("0")) {
                        System.out.println("Pemesanan dibatalkan");
                        break;
                    }

                    int jumlahAsInt = Integer.parseInt(jumlah);
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
}
