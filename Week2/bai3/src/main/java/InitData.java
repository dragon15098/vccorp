import redis.clients.jedis.Jedis;

public class InitData {
    static Jedis jedis;

    public static void main(String[] args) {
        if (jedis == null) {
            jedis = new Jedis("localhost", 6379, 10);
            jedis.auth("abc@123");
        }
        for (int i = 1; i <= 10000; i++) {
            jedis.hset("test", i + "", i * i + "");
        }
    }
}
