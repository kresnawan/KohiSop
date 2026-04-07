import java.util.Scanner;

import cart.Cart;
import help.Help;
import menu.Menu;

public class KohiSop {
    public Menu menu = new Menu();
    public Cart cart = new Cart();
    public Help help = new Help();
    public Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        KohiSop app = new KohiSop();

        System.out.println("\nSelamat datang di KohiSop!\nMau pesan apa?\n");
        app.menu.display();

        System.out.println("\nKetik 'help' untuk bantuan ^^!");

        while (true) {
            System.out.print("\n[KohiSop] > ");
            String command = app.input.next();
            System.out.println();

            if (command.toLowerCase().equals("cc")) {
                Handler.handleExit(app);
            } else if (command.toLowerCase().equals("help")) {
                Handler.handleHelp(app);
            } else if (command.toLowerCase().equals("menu")) {
                Handler.handleHelp(app);
            } else if (command.toLowerCase().equals("cart")) {
                Handler.handleDisplayCart(app);
            } else if (command.toLowerCase().equals("add")) {
                Handler.handleAddToCart(app);
            } else if (command.toLowerCase().equals("clear")) {
                Handler.handleClearConsole();
            } else {
                System.out.printf("Perintah '%s' tidak ditemukan :(\n", command);
                System.out.println("Ketik 'help' untuk bantuan ^^!");
            }

            app.input.nextLine();

        }

    }
}