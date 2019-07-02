package groupthree.web.servlets;

import util.DBConnectionUtils;
import util.Keyboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthenticatorMain {

    public void start() throws IOException, SQLException {

        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            boolean done = false;
            while (!done) {
                System.out.println("Please enter an option:");
                System.out.println("1. Create account");
                System.out.println("2. Authenticate");
                System.out.println("3. Exit");
                System.out.print("> ");
                int command = Integer.parseInt(Keyboard.readInput());

                switch (command) {
//                    case 1:
//                        createNewAccount(conn);
//                        break;

                    case 2:
                        authenticate(conn);
                        break;

                    default:
                        done = true;
                        break;
                }

                System.out.println();
            }

        }
    }
//
//    private void createNewAccount(Connection conn) throws SQLException {
//        System.out.print("Enter your new username: > ");
//        String username = Keyboard.readInput();
//
//        System.out.print("Enter your new password: > ");
//        String password = Keyboard.readInput();
//
//
//        UserDAO.insertUser(AuthenticatorUtils.createAccount(username, password), conn);
//    }

    private void authenticate(Connection conn) throws SQLException {
        System.out.print("Enter your username: > ");
        String username = Keyboard.readInput();

        System.out.print("Enter your password: > ");
        String password = Keyboard.readInput();


        User user = UserDAO.getUserByUsername(conn, username);

        if (AuthenticatorUtils.authenticate(user, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Wrong Password/Username!");
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        new AuthenticatorMain().start();
    }
}