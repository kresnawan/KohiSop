package cart;
import interfaces.Displayable;
import menu.MenuItem;
import menu.MenuType;

import java.util.ArrayList;

public class Cart implements Displayable {
    public ArrayList<CartItem> items = new ArrayList<CartItem>();

    public Cart() {
    }

    @Override
    public void display() {
        System.out.printf("-------------------------------------------------\n");
        System.out.printf("%-6s %-35s %s\n", "Kode", "Minuman", "Jumlah");
        System.out.printf("-------------------------------------------------\n");
        for (CartItem cartItem : this.items) {
            if (cartItem.menu.tipe == MenuType.Minuman) {
                System.out.printf("%-6s %-35s %d\n", cartItem.menu.kode, cartItem.menu.nama, cartItem.amount);
            }
        }

        System.out.printf("\n-------------------------------------------------\n");
        System.out.printf("%-6s %-35s %s\n", "Kode", "Makanan", "Jumlah");
        System.out.printf("-------------------------------------------------\n");

        for (CartItem cartItem : this.items) {
            if (cartItem.menu.tipe == MenuType.Makanan) {
                System.out.printf("%-6s %-35s %d\n", cartItem.menu.kode, cartItem.menu.nama, cartItem.amount);
            }
        }
    }

    private int getCurrentAmount(MenuItem item) {
        for (CartItem i : this.items) {
            if (i.menu.equals(item)) {
                return i.amount;
            }
        }

        return 0;
    }

    private void addAmount(MenuItem item, int amount) {
        for (CartItem i : this.items) {
            if (i.menu.equals(item)) {
                i.amount += amount;
            }
        }
    }

    public void add(MenuItem item, int amount) throws Exception {
        int currentAmount = getCurrentAmount(item);
        if (item.tipe == MenuType.Makanan) {
            if (currentAmount >= 2) {
                throw new Exception(String.format("Pemesanan gagal, kamu udah pesan %s sebanyak 2 porsi loh :(", item.nama));
            }

            if (currentAmount + amount > 2) {
                throw new Exception(String.format("Pemesanan gagal, kamu pesan makanan ini terlalu banyak :|", item.kode));
            }

            if (currentAmount == 0) {
                CartItem new_item = new CartItem(item, amount);
                this.items.add(new_item);
            } else {
                addAmount(item, amount);
            }
        } else {
            if (currentAmount >= 3) {
                throw new Exception(String.format("Pemesanan gagal, kamu udah pesan %s sebanyak 3 porsi loh :(", item.nama));
            }

            if (currentAmount + amount > 3) {
                throw new Exception(String.format("Pemesanan gagal, kamu pesan minuman ini terlalu banyak :|", item.kode));
            }

            if (currentAmount == 0) {
                CartItem new_item = new CartItem(item, amount);
                this.items.add(new_item);
            } else {
                addAmount(item, amount);
            }
        }
    }
}