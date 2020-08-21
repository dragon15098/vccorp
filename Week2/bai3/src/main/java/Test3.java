import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;

import java.time.Duration;

import static spark.Spark.*;

public class Test3 {
    static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

    public static void main(String[] args) {
        get("/prime", (req, res) -> {
            try (Jedis jedis = pool.getResource()) {
                jedis.auth("abc@123");
                String nStr = req.queryParams("n");
                if (nStr != null) {
                    try {
                        int n = Integer.parseInt(nStr);
                        if (0 < n && n <= 10000) {
                            return "" + jedis.hget("test", nStr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                jedis.close();
                System.out.println(nStr);
                return "Bad Request";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Bad Request";
        });
    }
}
