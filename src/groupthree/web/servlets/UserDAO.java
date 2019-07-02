package groupthree.web.servlets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {


    public static User getUserByUsername(Connection connection, String username) throws SQLException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM user WHERE username = ?"
        )) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
                }
            }
        }

        return user;
    }

    public static User getUserByuserId(Connection connection, int userId) throws SQLException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM user WHERE userId = ?"
        )) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
                }
            }
        }

        return user;
    }

    public static boolean insertUser(User user, Connection conn) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO user (userName, birth_year, birth_month, birth_date, country, description, firstname, lastname, email, avatarName, hashed_password, salt, salt_length, iteration) VALUES (?, ?, ? ,?,?,?,?, ? ,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUserName());
            stmt.setInt(2, user.getBirth_year());
            stmt.setInt(3, user.getBirth_month());
            stmt.setInt(4, user.getBirth_date());
            stmt.setString(5, user.getCountry());
            stmt.setString(6, user.getDescription());
            stmt.setString(7, user.getFirstName());
            stmt.setString(8, user.getLastName());
            stmt.setString(9, user.getEmail());
            stmt.setString(10, user.getAvatarName());
            stmt.setString(11, user.getHashed_password());
            stmt.setBytes(12, user.getSalt());
            stmt.setInt(13, user.getSaltLength());
            stmt.setInt(14, user.getIteration());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                user.setUserId(id);
                return true;
            }
        }
    }

    public static boolean updateUser(User user, Connection conn, int userId) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE user SET birth_year = ?, birth_month = ?, birth_date = ?, country = ?, description = ?, firstname = ?, lastname = ?, email = ?, avatarName = ?,hashed_password = ?, salt = ?, salt_length = ?, iteration = ? WHERE userId = ?")) {

            stmt.setInt(1, user.getBirth_year());
            stmt.setInt(2, user.getBirth_month());
            stmt.setInt(3, user.getBirth_date());
            stmt.setString(4, user.getCountry());
            stmt.setString(5, user.getDescription());
            stmt.setString(6, user.getFirstName());
            stmt.setString(7, user.getLastName());
            stmt.setString(8, user.getEmail());
            stmt.setString(9, user.getAvatarName());
            stmt.setString(10, user.getHashed_password());
            stmt.setBytes(11, user.getSalt());
            stmt.setInt(12, user.getSaltLength());
            stmt.setInt(13, user.getIteration());
            stmt.setInt(14, userId);

            int rowsAffected = stmt.executeUpdate();

            return (rowsAffected == 1);

        }
    }

    private static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8),
                resultSet.getString(9),
                resultSet.getString(10),
                resultSet.getString(11),
                resultSet.getString(12),
                resultSet.getBytes(13),
                resultSet.getInt(14),
                resultSet.getInt(15)
        );
    }

    public static boolean deleteUser(int userId, Connection conn) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM user WHERE userId = ?")) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            return (rowsAffected == 1);
        }

    }
// Admin area and Methods

    public static List<User> getAllUserAdminControl(Connection conn) throws SQLException {
        List<User> alluser = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT userId, userName,birth_year, birth_month,birth_date,description ,firstname,lastname ,email ,avatarName FROM user/* we have to use WHERE here and condtion will be except user who is admin*/")) {
                while (rs.next()) {
                    User user = getUserFromResultSetAdminControl(rs);
                    alluser.add(user);
                }
            }
        }
        return alluser;
    }

    private static User getUserFromResultSetAdminControl(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt(1),
                rs.getString("userName"),
                rs.getInt("birth_year"),
                rs.getInt("birth_month"),
                rs.getInt("birth_date"),
                rs.getString("description"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("avatarName")
        );
    }

    public static boolean deleteUserAdminControl(int userId, Connection conn) throws SQLException {

        // We can use prepared statements for database deletes as well as queries.
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM user WHERE userId = ? ")) {

            stmt.setInt(1, userId);

            // The executeUpdate() method will run an SQL INSERT, UPDATE, or DELETE. It will return an int value
            // specifying how many rows are affected by the statement. In this case, this will be either 1 (if a row
            // was deleted) or 0 (if it wasn't because a lecturer with the given staff_no doesn't exist).
            int rowsAffected = stmt.executeUpdate();

            // If rowsAffected is 1, it means the delete was successful and we'll return true. Otherwise we return false.
            return (rowsAffected == 1);

        }

    }

    public static boolean insertToken(int id, String token, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user  SET token = ? WHERE userId = ?")) {
            stmt.setString(1, token);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            // If rowsAffected is 1, it means the update was successful and we'll return true. Otherwise we return false.
            return (rowsAffected == 1);

        }

    }

    public static boolean insertPassword(String passwordHashed, String token, Connection conn) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE user SET hashed_password = ? WHERE token = ?")) {

            stmt.setString(1, passwordHashed);
            stmt.setString(2, token);

            int rowsAffected = stmt.executeUpdate();

            return (rowsAffected == 1);
        }
    }

    public static User getUserByToken(String token, Connection connection) throws SQLException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM user WHERE token = ?"
        )) {
            statement.setString(1, token);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
                }
            }
        }

        return user;
    }

}