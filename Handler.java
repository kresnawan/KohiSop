import menu.Menu;
import menu.MenuItem;
import menu.MenuType;
import payment.ChannelPembayaran;
import payment.EMoney;
import payment.Qris;
import payment.Tunai;
import tax.Pajak;
import tax.PajakMakanan;
import tax.PajakMinuman;
import currency.CurrencyConverter;
import currency.CurrencyType;
import cart.Cart;
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
			System.out.printf("Menu dengan kode '%s' tidak ditemukan\n", kode);
			System.out.println("Ketik 'menu' untuk melihat menu");
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
						System.out.println("Ketik 'cart' untuk melihat keranjang anda saat ini");
						System.out.println("Ketik 'checkout' untuk membuat pesanan");
					} catch (Exception e) {
						System.out.println(Colors.BOLD + Colors.RED + e.getMessage() + Colors.RESET);
					}
					break;
				} catch (Exception e) {
					System.out.println("Input anda tidak valid\n");
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
			System.out.printf(Colors.BOLD + Colors.RED + "%s\n" + Colors.RESET, e.getMessage());
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
			app.input.nextLine();
			ChannelPembayaran ch;
			ChannelPembayaran[] chs = Handler.displayChannelPembayaran();
			System.out.println();

			while (true) {
				System.out.printf("Masukkan kode channel pembayaran (contoh: 1): ");
				String n = app.input.nextLine();
				int choice;

				if (n.equals("S")) {
					System.out.println("Checkout dibatalkan");
					return;
				}

				try {
					choice = Integer.parseInt(n);
					ch = chs[choice - 1];
				} catch (Exception e) {
					System.out.println("Input anda tidak valid atau channel pembayaran tidak tersedia.");
					continue;
				}

				System.out.printf("Anda memilih %s sebagai channel pembayaran.\n", ch.nama);
				break;
			}

			double grandTotalIDR = printTagihan(app.cart, ch, CurrencyType.IDR);

			if (ch instanceof Qris || ch instanceof EMoney) {
				if (!ch.cekSaldo(grandTotalIDR, app.saldo)) {
					System.out.printf(Colors.BOLD + Colors.RED
							+ "Checkout gagal, saldo anda tidak mencukupi untuk menggunakan channel pembayaran %s.\n"
							+ Colors.RESET, ch.nama);
					System.out.println("Ketik 'topup <jumlah>' untuk menambahkan saldo.");
					return;
				}
			}

			CurrencyType ctChosen;
			CurrencyType[] ctArray = Handler.displayMataUang();

			while (true) {
				System.out.println();
				System.out.printf("Masukkan kode mata uang yang akan digunakan untuk pembayaran (contoh: 1): ");
				String cur = app.input.next();

				try {
					int choice = Integer.parseInt(cur);
					ctChosen = ctArray[choice - 1];
				} catch (Exception e) {
					System.out.println("Input anda tidak valid.");
					continue;
				}
				break;
			}

			printNota(app.cart, ch, ctChosen);
			app.saldo -= grandTotalIDR;
			app.cart.empty();

		}
	}

	private static ChannelPembayaran[] displayChannelPembayaran() {
		ChannelPembayaran[] chs = {
				new Qris(),
				new Tunai(),
				new EMoney()
		};

		String str = String.format("%-32s", "");
		System.out.printf("%-32s\n", str.replace(" ", "-"));
		System.out.printf("%-2s %-30s\n", "No", "Channel Pembayaran");
		System.out.printf("%-32s\n", str.replace(" ", "-"));
		for (int i = 0; i < chs.length; i++) {
			System.out.printf("%-2s %-30s\n", i + 1, chs[i].nama);
		}

		return chs;
	}

	private static CurrencyType[] displayMataUang() {
		CurrencyType[] mu = {
				CurrencyType.EUR,
				CurrencyType.IDR,
				CurrencyType.JPY,
				CurrencyType.MYR,
				CurrencyType.USD
		};

		String str = String.format("%-32s", "");
		System.out.printf("%-32s\n", str.replace(" ", "-"));
		System.out.printf("%-2s %-30s\n", "No", "Mata Uang");
		System.out.printf("%-32s\n", str.replace(" ", "-"));
		for (int i = 0; i < mu.length; i++) {
			System.out.printf("%-2s %-30s\n", i + 1, CurrencyConverter.getCode(mu[i]));
		}

		return mu;
	}

	private static double printTagihan(Cart c, ChannelPembayaran cp, CurrencyType type) {
		String str = String.format("%-72s", "");
		System.out.printf(" %-68s \n", str.replace(" ", "-"));

		// System.out.printf("| %-68s | \n", "");
		// System.out.printf("| %-68s | \n", "KohiSop");
		System.out.printf("|  %-48s %19s  | \n", "Tagihan Anda", cp.nama + " | " + CurrencyConverter.getCode(type));

		System.out.printf(" %-68s \n", str.replace(" ", "-"));

		System.out.printf("|  %-68s  | \n", "");
		System.out.printf("|  %-4s  %-35s  %7s  %7s  %7s  | \n", "Kode", "Nama", "Harga", "Jumlah", "Total");
		System.out.printf("|  %-68s  | \n", "");

		double totalHarga = 0;
		double totalPajak = 0;

		for (CartItem item : c.items) {

			double subtotal = CurrencyConverter.convert(item.menu.harga * item.amount, type);
			totalHarga += subtotal;

			Pajak pajak;

			if (item.menu.tipe == MenuType.Minuman) {
				pajak = new PajakMinuman();
			} else {
				pajak = new PajakMakanan();
			}

			double pajakItem = CurrencyConverter.convert(pajak.hitung(item.menu, item.amount), type);
			totalPajak += pajakItem;

			System.out.printf("|  %-4s  %-35s  %7.2f  %7d  %7.2f  | \n",
					item.menu.kode, item.menu.nama, CurrencyConverter.convert(item.menu.harga, type), item.amount,
					subtotal);

			System.out.printf("|  %-4s  %-35s  %7s  %7s  %7.2f  | \n",
					"", "Pajak", "", "", pajakItem);

			System.out.printf("|  %-68s  | \n", "");
		}

		double totalDiskon = cp.hitungDiskon(totalHarga + totalPajak);
		double totalBiayaAdmin = cp.getBiayaAdmin();
		double grandTotal = (totalHarga + totalPajak) - totalDiskon + totalBiayaAdmin;

		System.out.printf("|  %-68s  | \n", "");
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "Total (Tanpa Pajak)", totalHarga);
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "Total Pajak", totalPajak);
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "Diskon", totalDiskon);
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "Biaya Admin", totalBiayaAdmin);
		System.out.printf("|  %-68s  | \n", "");
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "GRAND TOTAL", grandTotal);
		System.out.printf("|  %-68s  | \n", "");
		System.out.printf(" %-68s \n", str.replace(" ", "-"));

		return grandTotal;
	}

	private static double printNota(Cart c, ChannelPembayaran cp, CurrencyType type) {
		String str = String.format("%-72s", "");
		System.out.printf(" %-68s \n", str.replace(" ", "-"));

		// System.out.printf("| %-68s | \n", "");
		// System.out.printf("| %-68s | \n", "KohiSop");
		System.out.printf("|  %-48s %19s  | \n", "KohiSop", cp.nama + " | " + CurrencyConverter.getCode(type));

		System.out.printf(" %-68s \n", str.replace(" ", "-"));

		System.out.printf("|  %-68s  | \n", "");
		System.out.printf("|  %-4s  %-35s  %7s  %7s  %7s  | \n", "Kode", "Nama", "Harga", "Jumlah", "Total");
		System.out.printf("|  %-68s  | \n", "");

		double totalHarga = 0;
		double totalPajak = 0;

		for (CartItem item : c.items) {

			double subtotal = CurrencyConverter.convert(item.menu.harga * item.amount, type);
			totalHarga += subtotal;

			Pajak pajak;

			if (item.menu.tipe == MenuType.Minuman) {
				pajak = new PajakMinuman();
			} else {
				pajak = new PajakMakanan();
			}

			double pajakItem = CurrencyConverter.convert(pajak.hitung(item.menu, item.amount), type);
			totalPajak += pajakItem;

			System.out.printf("|  %-4s  %-35s  %7.2f  %7d  %7.2f  | \n",
					item.menu.kode, item.menu.nama, CurrencyConverter.convert(item.menu.harga, type), item.amount,
					subtotal);

			System.out.printf("|  %-4s  %-35s  %7s  %7s  %7.2f  | \n",
					"", "Pajak", "", "", pajakItem);

			System.out.printf("|  %-68s  | \n", "");
		}

		double totalDiskon = cp.hitungDiskon(totalHarga + totalPajak);
		double totalBiayaAdmin = cp.getBiayaAdmin();
		double grandTotal = (totalHarga + totalPajak) - totalDiskon + totalBiayaAdmin;

		System.out.printf("|  %-68s  | \n", "");
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "Total (Tanpa Pajak)", totalHarga);
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "Total Pajak", totalPajak);
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "Diskon", totalDiskon);
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "Biaya Admin", totalBiayaAdmin);
		System.out.printf("|  %-68s  | \n", "");
		System.out.printf("|  %-4s  %-53s  %7.2f  | \n", "", "GRAND TOTAL", grandTotal);
		System.out.printf("|  %-68s  | \n", "");
		System.out.printf(" %-68s \n", str.replace(" ", "-"));

		System.out.printf("|  %-68s  | \n", "Terima kasih dan silakan datang kembali^^!");
		System.out.printf(" %-68s \n", str.replace(" ", "-"));

		return grandTotal;
	}

	public static void handleTopup(KohiSop app) {
		try {
			String jumlahAsString = app.input.next();
			double jumlah = Double.parseDouble(jumlahAsString);

			app.saldo += jumlah;
			System.out.println(Colors.BOLD + Colors.GREEN + "Saldo berhasil ditambahkan." + Colors.RESET);
			System.out.println("Ketik 'saldo' untuk melihat saldo terkini.");
		} catch (Exception e) {
			System.out.println("Input anda tidak valid.");
			return;
		}
	}

	public static void handleDisplaySaldo(KohiSop app) {
		System.out.println("IDR " + app.saldo);
	}
}