import java.util.Scanner;
import View.LoginView;

public class Main {

    public static void main(String[] args) throws Exception {
        LoginView view = new LoginView();
        Scanner sc = new Scanner(System.in);
        char choice = 'y';

        while (choice == 'y') {
            String[] inputs = view.userLogin();
            JDBC jdbc = new JDBC(inputs[0], inputs[1]);
            jdbc.connectToDB();

            System.out.println("Do you want to continue? (y/n): ");
            choice = sc.next().charAt(0);
        }
    }
}