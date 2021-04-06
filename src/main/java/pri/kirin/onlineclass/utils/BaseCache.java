package pri.kirin.onlineclass.utils;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Data
public class BaseCache {
    //10分钟缓存
    private Cache<String,Object> tenMinuteCache = CacheBuilder.newBuilder()
            //缓存初始大小
            .initialCapacity(10)
            //最大值
            .maximumSize(100)
            //并发数
            .concurrencyLevel(5)
            //过期时间
            .expireAfterAccess(600, TimeUnit.SECONDS)
            .recordStats()
            .build();

    //1小时缓存
    private Cache<String,Object> oneHourCache = CacheBuilder.newBuilder()
            //缓存初始大小
            .initialCapacity(10)
            //最大值
            .maximumSize(100)
            //并发数
            .concurrencyLevel(5)
            //过期时间
            .expireAfterAccess(1, TimeUnit.HOURS)
            .recordStats()
            .build();

}
