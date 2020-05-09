import musicalManager.PitchManagerEnum;

/**
 * User class.
 * @author Sahatsawat Kanpai
 */
public class User {

    public PitchManagerEnum pitchManagerEnum;
    private final String userName;
    private final String passWord;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}
