
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scanner implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scanner.class);
    private int time = 0;
    private final Checker checker;

    public Scanner(Checker checker) {
        this.checker = checker;
    }

    public void run() {
        while (true) {
            try {
                scanUserId();
                Thread.sleep(2000);
                time += 2;
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void scanUserId() {
        checker.checkValueFromScanner(time);
    }
}
