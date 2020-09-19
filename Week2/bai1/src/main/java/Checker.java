import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checker {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSender.class);

    boolean scannerHasCheck = false;
    int setValueTime = -1;
    int value = -1;
    int oldValue = -1;
    int lastTimeChange = 0;

    public void setValue(int time, int value) {
        this.oldValue = this.value;
        this.value = value;
//
        this.setValueTime = time;

    }

    public void checkValueFromScanner(int time) {
        while (setValueTime < time) {
            System.out.println("time: " + time + ", setValueTime: " + setValueTime);
        }
        checkFromScanner(time);
    }

    private void checkFromScanner(int time) {
        System.out.println("compare = " + setValueTime + " ___ " + time);
        if (setValueTime == time && value != -1) { // value is new
            check();
        } else {
            // let sender check
            System.out.println(setValueTime + "____" + time);
            scannerHasCheck = true;
        }
    }

    private void check() {
        if (value < oldValue) {
            showInfoLog();
            lastTimeChange = 0;
        } else if (lastTimeChange >= 12) {
            showDebugLog();
            lastTimeChange = 0;
        }
        lastTimeChange += 2;
        resetState();
    }

    private void resetState() {
        scannerHasCheck = false;
    }

    private void showInfoLog() {
        LOGGER.info("LOGGER.info - {}", "time: " + setValueTime + " " + "userId: " + value);
    }

    private void showDebugLog() {
        LOGGER.debug(" LOGGER.debug - {}", "setValueTime: " + setValueTime + " " + "userId: " + value);
    }
}
