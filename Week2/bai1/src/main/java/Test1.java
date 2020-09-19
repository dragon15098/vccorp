public class Test1 {


    public static void main(String[] args) throws InterruptedException {
        Checker checker = new Checker();

        HttpSender httpSender = new HttpSender(checker);
        Thread thread = new Thread(httpSender);
        thread.start();

        Scanner scanner = new Scanner(checker);
        Thread thread2 = new Thread(scanner);
        thread2.start();
    }
}
