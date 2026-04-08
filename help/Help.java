package help;
import interfaces.Displayable;

public class Help implements Displayable {
    HelpItem[] items = {
        new HelpItem("help", "Menampilkan daftar perintah"),
        new HelpItem("cart", "Menampilkan keranjang"),
        new HelpItem("menu", "Menampilkan menu yang tersedia"),
        new HelpItem("clear", "Membersihkan konsol (hanya pemanis)"),
        new HelpItem("checkout", "Membuat pesanan"),
        new HelpItem("add <kode>", "Menambahkan menu kedalam keranjang"),
        new HelpItem("remove <kode>", "Mengurangi menu dari keranjang"),
    };

    @Override
    public void display() {
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-2s %-25s  %-10s\n", "", "Perintah", "Aksi");
        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < this.items.length; i++) {
            System.out.printf("%-2s %-25s  %-10s\n", i + 1, this.items[i].command, this.items[i].desc);
        }
    }
}