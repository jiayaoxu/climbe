package cn.newcode.climb.Fight.tool;

import redis.clients.jedis.Jedis;

/**
 * @Description: 对战用户信息存入redis
 * @author: shine
 * @CreateDate: 2017/11/7 10:34
 * @Version: 1.0
 */
public class UserCacheManager {
    private Jedis jedis;

    /**
     * 实例化jedis对象
     */
    private UserCacheManager(){
        jedis = new RedisUtil().getJedis();
    }

    /**
     * 添加玩家
     */
    public void addPlayer(){

    }
}
