package crypter;

import java.util.List;
import java.util.ArrayList;

/**
 * Encrypt class that have 2 services encrypt strategies.
 * @author Sahatsawat Kanpai
 */
public class Encrypter implements Crypter {

    /**
     * Encrypt with shift algorithm.
     * @param s is the String to encrypt and only affect latin characters
     * @param key is the key to encrypt
     * @return encrypted String
     */
    @Override
    public String shift(String s, int key) {
        List<Character> lowerCase = new ArrayList<>();
        List<Character> upperCase = new ArrayList<>();
        for (char c = 'a'; c < 'z' + 1; c++)
            lowerCase.add(c);
        for (char c = 'A'; c < 'Z' + 1; c++)
            upperCase.add(c);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (lowerCase.contains(c)) {
                int newIndex = Math.abs(lowerCase.indexOf(c) + key) % 26;
                sb.append(lowerCase.get(newIndex));
            }

            else if (upperCase.contains(c)) {
                int newIndex = Math.abs(upperCase.indexOf(c) + key) % 26;
                sb.append(upperCase.get(newIndex));
            }

            else sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Encrypt with unicode algorithm.
     * @param s is the String to encrypt and affect for all char
     * @param key is the key to encrypt
     * @return encrypted String
     */
    @Override
    public String unicode(String s, int key) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            sb.append((char) (s.charAt(i) + key));
        }
        return sb.toString();
    }
}
