import cart.Cart;
import colors.Colors;
import help.Help;
import java.util.Scanner;
import membership.Membership;
import menu.Menu;

public class KohiSop {
    public Menu menu = new Menu();
    public Cart cart = new Cart();
    public Help help = new Help();
    public Membership membership = new Membership();
    public Scanner input = new Scanner(System.in);
    public double saldo;

    public static void main(String[] args) {
        KohiSop app = new KohiSop();

        System.out.println("\nSelamat datang di KohiSop!\nMau pesan apa?\n");
        app.menu.display();

        System.out.println("\nKetik 'help' untuk bantuan ^^!");
        System.out.println("Ketik 'add <kode>' untuk menambah menu ke keranjang");

        while (true) {
            

            System.out.print(Colors.BOLD + Colors.CYAN + "\n[KohiSop]" + Colors.RESET + "> ");
            String command = app.input.next();

            if (command.toLowerCase().equals("cc")) {
                Handler.handleExit(app);
            } else if (command.toLowerCase().equals("help")) {
                Handler.handleHelp(app);
            } else if (command.toLowerCase().equals("menu")) {
                Handler.handleMenu(app);
            } else if (command.toLowerCase().equals("cart")) {
                Handler.handleDisplayCart(app);
            } else if (command.toLowerCase().equals("add")) {
                Handler.handleAddToCart(app);
            } else if (command.toLowerCase().equals("remove")) {
                Handler.handleRemoveFromCart(app);
            } else if (command.toLowerCase().equals("clear")) {
                Handler.handleClearConsole();
            } else if (command.toLowerCase().equals("checkout")) {
                Handler.handleCheckout(app);
            } else if (command.toLowerCase().equals("topup")) {
                Handler.handleTopup(app);
            } else if (command.toLowerCase().equals("saldo")) {
                Handler.handleDisplaySaldo(app);
            } else if (command.toLowerCase().equals("member")) {
                Handler.handleDisplayMembership(app);
            } else if (command.toLowerCase().equals("dapur")) {
                Handler.handleDapur(app);
            } else {
                System.out.printf("Perintah '%s' tidak ditemukan.\n", command);
                System.out.println("Ketik 'help' untuk bantuan.");
            }

        }

    }
}