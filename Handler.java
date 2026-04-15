import menu.Menu;
import menu.MenuItem;
import menu.MenuType;
import tax.Pajak;
import tax.PajakMakanan;
import tax.PajakMinuman;
import cart.CartItem;
import colors.Colors;

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
        app.input.nextLine();
        MenuItem chosen = Menu.getMenu(kode);
        if (chosen == null) {
            System.out.printf("Menu dengan kode '%s' tidak ditemukan :(\n", kode);
        } else {
            System.out.printf("Anda akan menambahkan %s seharga Rp %.0f kedalam keranjang\n", chosen.nama,
                    chosen.harga);
            while (true) {
                try {
                    System.out.print("Masukkan jumlah : ");

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
                        app.cart.addItemAmount(chosen, jumlahAsInt);
                        System.out.printf(Colors.BOLD + Colors.GREEN + "Berhasil menambahkan %s sebanyak %d porsi\n"
                                + Colors.RESET, chosen.nama, jumlahAsInt);
                    } catch (Exception e) {
                        System.out.println(Colors.BOLD + Colors.RED + e.getMessage() + Colors.RESET);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("\nOh no, input anda tidak valid!\n");
                    continue;
                }
            }
        }
    }

    public static void handleRemoveFromCart(KohiSop app) {
        String kode = app.input.next();
        app.input.nextLine();
        CartItem chosen;

        try {
            chosen = app.cart.getItem(kode);

            System.out.printf("Anda akan mengurangi %s dari keranjang\n", chosen.menu.nama);
            while (true) {
                try {
                    System.out.print("Masukkan jumlah : ");

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
                        app.cart.removeItemAmount(chosen, jumlahAsInt);
                        System.out.printf(Colors.BOLD + Colors.GREEN + "Berhasil mengurangi %s sebanyak %d porsi\n"
                                + Colors.RESET, chosen.menu.nama, jumlahAsInt);
                    } catch (Exception e) {
                        System.out.println(Colors.BOLD + Colors.RED + e.getMessage() + Colors.RESET);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("\nInput anda tidak valid\n");
                    continue;
                }
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
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

            // System.out.printf("| %-68s | \n", "");
            System.out.printf("|  %-68s  | \n", "KohiSop");

            System.out.printf(" %-68s \n", str.replace(" ", "-"));

            System.out.printf("|  %-48s %19s  | \n", "Pesanan Anda", "IDR");
            System.out.printf("|  %-68s  | \n", "");
            System.out.printf("|  %-4s  %-35s  %7s  %7s  %7s  | \n", "Kode", "Nama", "Harga", "Jumlah", "Total");
            System.out.printf("|  %-68s  | \n", "");

            // Loop
            double totalHarga = 0;
            double totalPajak = 0;

            // Loop BARU
            for (CartItem c : app.cart.items) {

                double subtotal = c.menu.harga * c.amount;
                totalHarga += subtotal;

                Pajak pajak;

                if (c.menu.tipe == MenuType.Minuman) {
                    pajak = new PajakMinuman();
                } else {
                    pajak = new PajakMakanan();
                }

                double pajakItem = pajak.hitung(c.menu, c.amount);
                totalPajak += pajakItem;

                System.out.printf("|  %-4s  %-35s  %7.0f  %7d  %7.0f  | \n",
                        c.menu.kode, c.menu.nama, c.menu.harga, c.amount, subtotal);

                System.out.printf("|  %-4s  %-35s  %7s  %7s  %7.0f  | \n",
                        "", "Pajak", "", "", pajakItem);

                System.out.printf("|  %-68s  | \n", "");
            }

            System.out.printf("|  %-68s  | \n", "");

            System.out.printf("|  %-4s  %-53s  %7.0f  | \n", "", "Total (Tanpa Pajak)", totalHarga);
            System.out.printf("|  %-4s  %-53s  %7.0f  | \n", "", "Total Pajak", totalPajak);

            System.out.printf("|  %-68s  | \n", "");

            System.out.printf("|  %-4s  %-53s  %7.0f  | \n", "", "GRAND TOTAL", totalHarga + totalPajak);
            System.out.printf("|  %-68s  | \n", "");

            System.out.printf(" %-68s \n", str.replace(" ", "-"));

            // System.out.printf("| %-68s | \n", "");
            System.out.printf("|  %-68s  | \n", "Terima kasih dan silakan datang kembali ^^!");

            System.out.printf(" %-68s \n", str.replace(" ", "-"));
        }
    }
}