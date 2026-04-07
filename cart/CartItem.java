package cart;

import menu.MenuItem;

class CartItem {
    public MenuItem menu;
    public int amount;

    public CartItem(MenuItem menu, int amount) {
        this.menu = menu;
        this.amount = amount;
    }
}