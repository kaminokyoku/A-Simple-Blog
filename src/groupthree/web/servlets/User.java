package groupthree.web.servlets;

import org.json.simple.JSONObject;

public class User {

    private int userId;
    private String userName;
    private int birth_year;
    private int birth_month;
    private int birth_date;
    private String country;
    private String description;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarName;
    private String hashed_password;
    private byte[] salt;
    private int saltLength;
    private int iteration;
    private String token;
    private String plainpassword;


    public User(String userName, int birth_year, int birth_month,
                int birth_date, String country, String description, String firstName,
                String lastName, String email, String avatarName, String hashed_password,
                byte[] salt, int saltLength, int iteration) {
        this.userName = userName;
        this.birth_year = birth_year;
        this.birth_month = birth_month;
        this.birth_date = birth_date;
        this.country = country;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarName = avatarName;
        this.hashed_password = hashed_password;
        this.salt = salt;
        this.saltLength = saltLength;
        this.iteration = iteration;
    }


    public User(int userId, String userName, int birth_year, int birth_month, int birth_date, String country, String description, String firstName, String lastName, String email, String avatarName, String hashed_password, byte[] salt, int saltLength, int iteration) {
        this.userId = userId;
        this.userName = userName;
        this.birth_year = birth_year;
        this.birth_month = birth_month;
        this.birth_date = birth_date;
        this.country = country;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarName = avatarName;
        this.hashed_password = hashed_password;
        this.salt = salt;
        this.saltLength = saltLength;
        this.iteration = iteration;
    }

    public User(int userId, String userName, int birth_year,
                int birth_month, int birth_date, String country, String description,
                String firstName, String lastName, String email, String avatarName,
                String hashed_password, byte[] salt, int saltLength, int iteration, String token) {
        this.userId = userId;
        this.userName = userName;
        this.birth_year = birth_year;
        this.birth_month = birth_month;
        this.birth_date = birth_date;
        this.country = country;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarName = avatarName;
        this.hashed_password = hashed_password;
        this.salt = salt;
        this.saltLength = saltLength;
        this.iteration = iteration;
        this.token = token;

    }

    public User(int userId, String userName, int birth_year, int birth_month, int birth_date, String country, String description, String firstName, String lastname, String email, String avatarName) {
        this.userId = userId;
        this.userName = userName;
        this.birth_year = birth_year;
        this.birth_month = birth_month;
        this.birth_date = birth_date;
        this.country = country;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastname;
        this.email = email;
        this.avatarName = avatarName;

    }

    public User(int userId, String userName, int birth_year, int birth_month, int birth_date, String description, String firstname, String lastname, String email, String avatarName) {
        //this constructors is
        // for display all the user in Admin control page
        this.userId = userId;
        this.userName = userName;
        this.birth_year = birth_year;
        this.birth_month = birth_month;
        this.birth_date = birth_date;
        this.description = description;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.avatarName = avatarName;
    }

    public User(String firstName, String lastName, String userName, int birth_year, int birth_month, int birth_date, String avatarName, String description, String country, String email, String password) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.birth_year=birth_year;
        this.birth_month=birth_month;
        this.birth_date=birth_date;
        this.avatarName=avatarName;
        this.description=description;
        this.country=country;
        this.email=email;
        this.plainpassword=password;

    }

    public User() {

    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(int birth_month) {
        this.birth_month = birth_month;
    }

    public int getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(int birth_date) {
        this.birth_date = birth_date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getHashed_password() {
        return hashed_password;
    }

    public void setHashed_password(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSaltLength() {
        return saltLength;
    }

    public void setSaltLength(int saltLength) {
        this.saltLength = saltLength;
    }


/// this is i created for beacause when a Admin add a new user then the plain password will be sent to user

    public String getPlainpassword() {
        return plainpassword;
    }

    public void setPlainpassword(String plainpassword) {
        this.plainpassword = plainpassword;
    }

    //to jason
    public  static JSONObject toJson(User u) {
        JSONObject jObj = new JSONObject();

        /*private int userId;
        private String userName;
        private int birth_year;
        private int birth_month;
        private int birth_date;
        private String country;
        private String description;
        private String firstName;
        private String lastName;
        private String email;
        private String avatarName;
        private String hashed_password;
        private byte[] salt;
        private int saltLength;
        private int iteration;
        private String token;
        private String plainpassword;*/

        if (u != null) {
            jObj.put("userId", u.getUserId());
            jObj.put("userName", u.getUserName());
            jObj.put("birth_year", u.getBirth_year());
            jObj.put("birth_month", u.getBirth_month());
            jObj.put("birth_date", u.getBirth_date());
            jObj.put("country", u.getCountry());
            jObj.put("description", u.getDescription());
            jObj.put("firstName", u.getFirstName());
            jObj.put("lastName", u.getLastName());
            jObj.put("email", u.getEmail());
            jObj.put("avatarName", u.getAvatarName());
            jObj.put("hashed_password", u.getHashed_password());
            jObj.put("salt", u.getSalt());
            jObj.put("saltLength", u.getSaltLength());
            jObj.put("iteration", u.getIteration());
            jObj.put("token", u.getToken());

        }

        return jObj;
    }
    //   this is for Unique link sent to user
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String randomAlphaNumeric() {
        int count = 8;
        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }

        return builder.toString();

    }
}


