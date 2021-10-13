/*
 *    Copyright 2015-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.hiyj.blog.caches.redis;

import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * Cache adapter for Redis.
 *
 * @author Eduardo Macarron
 */

public class RedisCache implements Cache {

    private final String id;

    private static JedisPool pool;

    private final RedisConfig redisConfig;

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
        redisConfig = RedisConfigurationBuilder.getRedisConfig();
        pool = new JedisPool(redisConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getConnectionTimeout(),
                redisConfig.getSoTimeout(), redisConfig.getPassword(), redisConfig.getDatabase(), redisConfig.getClientName(),
                redisConfig.isSsl(), redisConfig.getSslSocketFactory(), redisConfig.getSslParameters(),
                redisConfig.getHostnameVerifier());
    }

    // TODO Review this is UNUSED
    private Object execute(RedisCallback callback) {
        try (Jedis jedis = pool.getResource()) {
            return callback.doWithRedis(jedis);
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        return (Integer) execute(jedis -> {
            Map<byte[], byte[]> result = jedis.hgetAll(id.getBytes());
            return result.size();
        });
    }

    @Override
    public void putObject(final Object key, final Object value) {
        execute(jedis -> {
            final byte[] idBytes = id.getBytes();
            jedis.hset(idBytes, key.toString().getBytes(), redisConfig.getSerializer().serialize(value));
            if (redisConfig.getTimeout() != null && jedis.ttl(idBytes) == -1) {
                jedis.expire(idBytes, redisConfig.getTimeout());
            }
            return null;
        });
    }

    @Override
    public Object getObject(final Object key) {
        return execute(jedis -> redisConfig.getSerializer().unserialize(jedis.hget(id.getBytes(), key.toString().getBytes())));
    }

    @Override
    public Object removeObject(final Object key) {
        return execute(jedis -> jedis.hdel(id, key.toString()));
    }

    @Override
    public void clear() {
        execute(jedis -> {
            jedis.del(id);
            return null;
        });

    }

    @Override
    public String toString() {
        return "Redis {" + id + "}";
    }
}
