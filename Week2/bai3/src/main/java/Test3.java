import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static spark.Spark.get;

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
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                        return "Number format exception";
                    }
                }
                jedis.close();
                return "Bad Request";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Bad Request";
        });
    }
}
