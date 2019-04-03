package com.example.li.springboot_mybatis_demo.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MybatisRedisCache implements Cache {

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	/**
	 *
	 * Jedis客户端
	 */

	@Autowired
	private Jedis redisClient = createClient();

	private String id;

	private final int db_num = 0;

	public MybatisRedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("必须传入ID");
		}
		System.out.println("MybatisRedisCache:id=" + id);
		this.id = id;
	}

	@Override
	public void clear() {
		redisClient.select(db_num);
		redisClient.flushDB();
	}

	@Override
	public String getId() {
		redisClient.select(db_num);
		return this.id;
	}

	@Override
	public Object getObject(Object key) {
		redisClient.select(db_num);
		byte[] ob = redisClient.get(SerializeUtil.serialize(key.toString()));
		if (ob == null) {
			return null;
		}
		Object value = SerializeUtil.unSerialize(ob);
		System.out.println("mybatis获取缓存----------------------------------------");
		return value;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		redisClient.select(db_num);
		return readWriteLock;
	}

	@Override
	public int getSize() {
		redisClient.select(db_num);
		return Integer.valueOf(redisClient.dbSize().toString());
	}

	@Override
	public void putObject(Object key, Object value) {
		redisClient.select(db_num);
		redisClient.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
	}

	@Override
	public Object removeObject(Object key) {
		redisClient.select(db_num);
		return redisClient.expire(SerializeUtil.serialize(key.toString()), 0);
	}

	protected static Jedis createClient() {
		try {
			@SuppressWarnings("resource")
			JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
			return pool.getResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException("mybatis初始化连接池错误");
	}

	private static RedisTemplate redisTemplate;

	public static void setRedisTemplate(RedisTemplate redisTemplate){
		MybatisRedisCache.redisTemplate = redisTemplate;
	}


}