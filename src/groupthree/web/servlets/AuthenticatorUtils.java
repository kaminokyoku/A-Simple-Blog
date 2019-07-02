package groupthree.web.servlets;

import util.Passwords;

public class AuthenticatorUtils {

    public static User createAccount(String username, int birth_year, int birth_month, int birth_date, String country, String description, String firstName, String lastName, String email, String avatarName, String password) {


        int iteration = (int) (Math.random() * 3 + 2);
        int saltLength = (int) (Math.random() * 3 + 1);
        byte[] salt = Passwords.getNextSalt(saltLength);
        byte[] passwordHashed = Passwords.hash(password.toCharArray(), salt, iteration);

        //String username, String password, String salt, int saltLength, int iteration
        User user = new User(username, birth_year, birth_month, birth_date, country, description, firstName, lastName, email, avatarName, Passwords.base64Encode(passwordHashed), salt, saltLength, iteration);


        return user;

    }

    public static boolean authenticate(User user, String password) {

        if (user.getHashed_password().equals(Passwords.base64Encode(Passwords.hash(password.toCharArray(), user.getSalt(), user.getIteration())))) {
            return true;
        } else {
            return false;
        }
    }

}
