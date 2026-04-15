package cart;

import interfaces.Displayable;
import menu.Menu;
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

    public CartItem getItem(String kode) throws Exception {
        MenuItem item = Menu.getMenu(kode);
        if (item == null) {
            throw new Exception("Menu tidak ditemukan");
        }

        for (CartItem c : this.items) {
            if (c.menu.kode.equals(kode)) {
                return c;
            }
        }

        throw new Exception("Item tidak ditemukan di keranjang");
    }

    private int getItemAmount(String kode) {
        for (CartItem i : this.items) {
            if (i.menu.kode.equals(kode)) {
                return i.amount;
            }
        }

        return 0;
    }

    public void addItemAmount(MenuItem item, int amount) throws Exception {
        int currentAmount = getItemAmount(item.kode);
        if (item.tipe == MenuType.Makanan) {
            if (currentAmount >= 2) {
                throw new Exception(String
                        .format("Makanan dengan kode %s gagal ditambahkan, karena telah menyentuh batas", item.kode));
            }

            if (currentAmount + amount > 2) {
                throw new Exception(String
                        .format("Makanan dengan kode %s gagal ditambahkan, karena jumlah melebihi batas", item.kode));
            }

            if (currentAmount == 0) {
                CartItem new_item = new CartItem(item, amount);
                this.items.add(new_item);
            } else {
                for (CartItem i : this.items) {
                    if (i.menu.equals(item)) {
                        i.amount += amount;
                    }
                }
            }
        } else {
            if (currentAmount >= 3) {
                throw new Exception(String
                        .format("Minuman dengan kode %s gagal ditambahkan, karena jumlah telah menyentuh batas",
                                item.kode));
            }

            if (currentAmount + amount > 3) {
                throw new Exception(String
                        .format("Minuman dengan kode %s gagal ditambahkan, karena permintaan melebihi batas",
                                item.kode));
            }

            if (currentAmount == 0) {
                CartItem new_item = new CartItem(item, amount);
                this.items.add(new_item);
            } else {
                for (CartItem i : this.items) {
                    if (i.menu.equals(item)) {
                        i.amount += amount;
                    }
                }
            }
        }
    }

    public void removeItemAmount(CartItem item, int amount) throws Exception {
        int currentAmount = getItemAmount(item.menu.kode);
        CartItem chosen;

        try {
            chosen = this.getItem(item.menu.kode);
        } catch (Exception e) {
            throw e;
        }

        if (currentAmount <= 0) {
            throw new Exception(String.format("Item tidak ditemukan", item.menu.nama));
        }

        if (currentAmount - amount < 0) {
            throw new Exception(
                    String.format("Item gagal dikurangi, karena jumlah di keranjang tidak sebanyak permintaan"));
        }

        chosen.amount -= amount;
        if (chosen.amount == 0) {
            this.items.remove(chosen);
        }
    }

}