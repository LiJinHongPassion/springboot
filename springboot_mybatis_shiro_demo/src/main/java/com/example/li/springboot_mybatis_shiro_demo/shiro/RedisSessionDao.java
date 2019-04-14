package com.example.li.springboot_mybatis_shiro_demo.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.List;

/***
 * shiro缓存
 */

public class RedisSessionDao extends EnterpriseCacheSessionDAO {

	//logger
	Logger logger = LoggerFactory.getLogger(getClass());

	//@Autowired
	private Jedis redisClient = createClient();

	private final int db_num = 1;

	protected static Jedis createClient() {

		try {
			@SuppressWarnings("resource")
			JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
			return pool.getResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException("shiro初始化连接池错误");
	}




	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
//		String sql = "insert into sessions(id, session) values(?,?)";
//		jdbcTemplate.update(sql, sessionId,
//				SerializableUtils.serialize(session));
		try {
			redisClient.select(db_num);
			redisClient.set(SerializableUtils.serializeObject(sessionId), SerializableUtils.serialize(session));
			logger.info("key:" + SerializableUtils.serializeObject(sessionId) + "value:" + SerializableUtils.serialize(session));
			return session.getId();
		}catch (Exception e){
			logger.error("jedisPool线程混乱,创建session失败，返回null：" + e);
			return null;
		}
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
//		String sql = "select session from sessions where id=?";
//		List<String> sessionStrList = jdbcTemplate.queryForList(sql,
//				String.class, sessionId);
		List<String> sessionStrList = null;
		try {
			redisClient.select(db_num);
			sessionStrList = redisClient.mget(SerializableUtils.serializeObject(sessionId));

			if (sessionStrList.get(0) == null)
				return null;
			return SerializableUtils.deserialize(sessionStrList.get(0));
		}catch (Exception e){
			logger.error("jedisPool线程混乱,读取session失败，返回null：" + e);
			return null;
		}

	}

	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession
				&& !((ValidatingSession) session).isValid()) {
			return;
		}
//		String sql = "update sessions set session=? where id=?";
//		jdbcTemplate.update(sql, SerializableUtils.serialize(session),
//				session.getId());
		try {
			redisClient.select(db_num);
			redisClient.set(SerializableUtils.serializeObject(session.getId()), SerializableUtils.serialize(session));

		}catch (Exception e){
			logger.error("jedisPool线程混乱，更新session失败: " + e);
		}
	}

	@Override
	protected void doDelete(Session session) {
//		String sql = "delete from sessions where id=?";
////		jdbcTemplate.update(sql, session.getId());
		try {
			redisClient.select(db_num);
			redisClient.del(SerializableUtils.serializeObject(session.getId()));

		}catch (Exception e){
			logger.error("jedisPool线程混乱，删除session失败: " + e);
		}
	}
}
