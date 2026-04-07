package menu;
import interfaces.Displayable;

public class Menu implements Displayable {
    MenuItem[] menu = {
            new MenuItem("A1", "Caffe Latte", 46, MenuType.Minuman),
            new MenuItem("A2", "Cappuccino", 46, MenuType.Minuman),
            new MenuItem("E1", "Caffe Americano", 37, MenuType.Minuman),
            new MenuItem("E2", "Caffe Mocha", 55, MenuType.Minuman),
            new MenuItem("E3", "Caramel Macchiato", 59, MenuType.Minuman),
            new MenuItem("E4", "Asian Dolce Latte", 55, MenuType.Minuman),
            new MenuItem("E5", "Double Shots Iced Shaken Espresso", 50, MenuType.Minuman),
            new MenuItem("B1", "Freshly Brewed Coffee", 23, MenuType.Minuman),
            new MenuItem("B2", "Vanilla Sweet Cream Cold Brew", 50, MenuType.Minuman),
            new MenuItem("B3", "Cold Brew", 44, MenuType.Minuman),
            new MenuItem("M1", "Petemania Pizza", 112, MenuType.Makanan),
            new MenuItem("M2", "Mie Rebus Super Mario", 35, MenuType.Makanan),
            new MenuItem("M3", "Ayam Bakar Goreng Rebus Spesial", 72, MenuType.Makanan),
            new MenuItem("M4", "Soto Kambing Iga Guling", 124, MenuType.Makanan),
            new MenuItem("S1", "Singkong Bakar A La Carte", 37, MenuType.Makanan),
            new MenuItem("S2", "Ubi Cilembu Bakar Arang", 58, MenuType.Makanan),
            new MenuItem("S3", "Tempe Mendoan", 18, MenuType.Makanan),
            new MenuItem("S4", "Tahu Bakso Extra Telur", 28, MenuType.Makanan)
    };

    public Menu() {
    }

    @Override
    public void display() {
        System.out.printf("-------------------------------------------------\n");
        System.out.printf("%-6s %-35s %s\n", "Kode", "Minuman", "Harga");
        System.out.printf("-------------------------------------------------\n");
        for (MenuItem menu : this.menu) {
            if (menu.tipe == MenuType.Minuman) {
                System.out.printf("%-6s %-35s %.0f\n", menu.kode, menu.nama, menu.harga);
            }
        }

        System.out.printf("\n-------------------------------------------------\n");
        System.out.printf("%-6s %-35s %s\n", "Kode", "Makanan", "Harga");
        System.out.printf("-------------------------------------------------\n");

        for (MenuItem menu : this.menu) {
            if (menu.tipe == MenuType.Makanan) {
                System.out.printf("%-6s %-35s %.0f\n", menu.kode, menu.nama, menu.harga);
            }
        }
    }

    public MenuItem getMenu(String kode) {
        for (MenuItem i : this.menu) {
            if (i.kode.equals(kode)) {
                return i;
            }
        }

        return null;
    }
}
