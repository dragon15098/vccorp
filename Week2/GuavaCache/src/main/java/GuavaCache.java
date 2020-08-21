import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCache {
    private static final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    return getRedisValue(key);
                }
            });

    private static String getRedisValue(String key) {
        Jedis jedis = new Jedis("localhost");
        jedis.auth("abc@123");
        String value = jedis.hget("test", key);
        jedis.close();
        return value;
    }

    public String getFromCache(String key) throws ExecutionException {
        return cache.get(key);
    }
}
