import redis.clients.jedis.Jedis;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        GuavaCache guavaCache = new GuavaCache();
        port(8080);
        get("/prime", (req, res) -> {
            String nStr = req.queryParams("n");
            if (nStr != null) {
                try {
                    int n = Integer.parseInt(nStr);
                    if (0 < n && n <= 10000) {
                        return "" + guavaCache.getFromCache(req.queryParams("n"));
                    }
                } catch (Exception ignored) {
                }
            }
            return "Bad Request";
        });
    }
}
