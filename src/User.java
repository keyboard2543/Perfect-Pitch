import crypter.Crypter;
import crypter.Decrypter;
import crypter.Encrypter;

import java.util.ArrayList;
import java.util.List;

/**
 * User class.
 * @author Sahatsawat Kanpai
 */
public class User {

    private static final int KEY = 47;
    private static List<User> users = new ArrayList<>();

    /** A userName attribute. */
    private String username;
    /** A passWord attribute. */
    private String password;
    private int tested;
    private int correct;

    /**
     * A constructor for this User.
     * @param username is a userName for this User
     * @param password is a passWord for this User
     * @param tested is total amount of tested for this User
     * @param correct is total correct of tested for this User
     */
    public User(String username, String password, int tested, int correct) {
        this.username = username;
        this.password = password;
        this.tested = tested;
        this.correct = correct;
    }

    /**
     * Get a userName of this User.
     * @return a userName of this User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get a passWord of this User.
     * @return a passWord of this User
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get tested amount of this User.
     * @return tested amount of this User
     */
    public int getTested() {
        return tested;
    }

    /**
     * Get correct tested of this User.
     * @return correct tested of this User
     */
    public int getCorrect() {
        return correct;
    }

    /**
     * Set tested amount of this User.
     * @param tested is tested amount of this User to set
     */
    public void setTested(int tested) {
        this.tested = tested;
    }

    /**
     * Set correct tested of this User.
     * @param correct is correct tested of this User
     */
    public void setCorrect(int correct) {
        this.correct = correct;
    }

    /**
     * Get accuracy of this User in PerfectPitch.
     * @return accuracy of this User in PerfectPitch
     */
    public double getAccuracy() {
        if (tested == 0) return 0.0;
        return (double) correct/tested * 100;
    }

    /**
     * Set a new userName for this User.
     * @param username is a new userName to set
     */
    public void setUsername(String username) {
        if (username.isBlank() || username.isEmpty())
            throw new IllegalArgumentException("Username must not be empty");
        else
            this.username = username;
    }

    /**
     * Set a new passWord for this User.
     * @param password is a new passWord to set
     */
    public void setPassword(String password) {
        if (username.isBlank() || username.isEmpty())
            throw new IllegalArgumentException("Password must not be empty");
        else
            this.password = password;
    }

    /**
     * Get all users.
     * @return List of users
     */
    public static List<User> getUsers() {
        return users;
    }

    /**
     * Set all users.
     * @param users is List of users
     */
    public static void setUsers(List<User> users) {
        User.users = users;
    }

    /**
     * Find user with a userName.
     * @param username is a userName of the user to find
     * @return a User
     */
    public static User findOrCreateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            } else if (user.getUsername().equals(username) && !user.getPassword().equals(password)) {
                return null;
            }
        }
        User newUser = new User(username, password, 0, 0);
        User.getUsers().add(newUser);
        return newUser;
    }
}
