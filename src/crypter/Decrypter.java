package crypter;

/**
 * Decrypt class that have 2 services decrypt strategies.
 * This class reuse Encrypt class by reverse the strategies.
 * @author Sahatsawat Kanpai
 */
public class Decrypter implements Crypter {
    /** Encrypter attribute reused to decrypt. */
    private final Encrypter encrypter = new Encrypter();

    /**
     * Decrypt with shift algorithm.
     * @param s is the String to decrypt and only affect latin characters
     * @param key is the key to encrypt
     * @return decrypted String
     */
    @Override
    public String shift(String s, int key) {
        return encrypter.shift(s, -key);
    }

    /**
     * Decrypt with unicode algorithm.
     * @param s is the String to decrypt and affect for all characters
     * @param key is the key to encrypt
     * @return decrypted String
     */
    @Override
    public String unicode(String s, int key) {
        return encrypter.unicode(s, -key);
    }
}
