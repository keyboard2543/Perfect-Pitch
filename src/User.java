import crypter.Crypter;
import crypter.Decrypter;
import crypter.Encrypter;
import musicalManager.PitchManagerEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * User class.
 * @author Sahatsawat Kanpai
 */
public class User {

    private static final int KEY = 47;
    private static List<User> users = new ArrayList<>();

    /** Crypters for this User. */
    private final Crypter encrypter = new Encrypter();
    private final Crypter decrypter = new Decrypter();

    /** A PitchManagerEnum for this User. */
    private PitchManagerEnum pitchManagerEnum;

    /** A userName attribute. */
    private String userName;
    /** A passWord attribute. */
    private String passWord;

    /**
     * A constructor for this User.
     * @param userName is a userName for this User
     * @param passWord is a passWord for this User
     * @param tested is total amount of tested for this User
     * @param correct is total correct of tested for this User
     */
    public User(String userName, String passWord, int tested, int correct) {
        this.userName = userName;
        this.passWord = passWord;
    }

    /**
     * Get a userName of this User.
     * @return a userName of this User
     */
    public String getUserName() {
        return decrypter.unicode(userName, KEY);
    }

    /**
     * Get a passWord of this User.
     * @return a passWord of this User
     */
    public String getPassWord() {
        return decrypter.unicode(passWord, KEY);
    }

    /**
     * Set a new userName for this User.
     * @param userName is a new userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Set a new passWord for this User.
     * @param passWord is a new passWord to set
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Get all users.
     * @return List of users
     */
    public static List<User> getUsers() {
        return users;
    }
}
