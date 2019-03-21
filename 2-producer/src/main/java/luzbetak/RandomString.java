package luzbetak;
import java.util.Random;

public class RandomString {

    static Random r = new Random();

    public static String generateString(int len) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            sb.append((char) (r.nextInt(24) + 'a'));
        }
        return sb.toString();
    }

}