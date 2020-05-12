package crypter;

/**
 * Crypter interface for crypter.
 * @author Sahatsawat Kanpai
 */
public interface Crypter {

    /**
     * Crypt with shift algorithm.
     * @param s is the string to crypt and only affect latin characters
     * @param key is the key to crypt
     * @return crypted char
     */
    String shift(String s, int key);

    /**
     * Crypt with unicode algorithm.
     * @param s is the string to crypt and affect for all characters
     * @param key is the key to crypt
     * @return crypted char
     */
    String unicode(String s, int key);
}
