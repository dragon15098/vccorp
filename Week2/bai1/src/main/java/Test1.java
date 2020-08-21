import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test1 {
    public static Integer value = null;

    public static Integer oldValue = null;

    public static void main(String[] args) {
        HttpSender httpSender = new HttpSender();
        Thread thread = new Thread(httpSender);
        thread.start();

        Scanner scanner = new Scanner();
        Thread thread2 = new Thread(scanner);
        thread2.start();
    }
}
