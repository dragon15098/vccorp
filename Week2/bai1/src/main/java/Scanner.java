
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scanner implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scanner.class);
    private int lastTimeChange = 0;
    private int time = 0;
    public static boolean firstTimeDone = false;

    public void run() {
        while (true) {
            try {
                scanUserId();
                Thread.sleep(2000);
                lastTimeChange += 2;
                time += 2;
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void scanUserId() {
        if (firstTimeDone) {
            if (Test1.value > Test1.oldValue * 1.015) {
                showInfoLog();
                lastTimeChange = 0;
            } else if (lastTimeChange > 12) {
                showDebugLog();
                lastTimeChange = 0;
            }
        } else if (Test1.value != null) {
            showInfoLog();
            Scanner.firstTimeDone = true;
        }
    }

    private void showInfoLog() {
        LOGGER.info("LOGGER.info - {}", "time: " + time + " " + "Test1.userId: " + Test1.value);
    }

    private void showDebugLog() {
        LOGGER.debug(" LOGGER.debug - {}", "lastTimeChange: " + lastTimeChange + " " + "Test1.userId: " + Test1.value);
    }

}
