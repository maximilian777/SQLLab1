import java.util.Scanner;
import View.LoginView;

public class Main {

    public static void main(String[] args) throws Exception {
        LoginView view = new LoginView();
        Scanner sc = new Scanner(System.in);
        char choice = 'y';
        String username;
        String password;

        while (choice == 'y') {
//            username = "";
//            password = "";
//            System.out.println(username + " " + password);
//            System.out.println("Welcome to the database!");
//            System.out.println("Please enter your username: ");
//            while(username.isEmpty()) {
//                username = sc.nextLine();
//            }
//
//            System.out.println("Please enter your password: ");
//            while (password.isEmpty()) {
//                password = sc.nextLine();
//            }
//            System.out.println(username + " " + password);
            String[] inputs = view.testBox();
            JDBC jdbc = new JDBC(inputs[0], inputs[1]);
            jdbc.connectToDB();

            //scan from here automatically fills the sc.nextLine() for username
            System.out.println("Do you want to continue? (y/n): ");
            choice = sc.next().charAt(0);
        }
    }
}