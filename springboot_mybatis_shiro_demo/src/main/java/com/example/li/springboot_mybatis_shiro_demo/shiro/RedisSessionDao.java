package com.example.li.springboot_mybatis_shiro_demo.shiro;

import com.example.li.springboot_mybatis_shiro_demo.shiro.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.List;

/***
 * shiro缓存
 */

public class RedisSessionDao extends EnterpriseCacheSessionDAO {

	//@Autowired
	private Jedis redisClient = createClient();

	//存储在redis-1号库
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
		redisClient.select(db_num);
		redisClient.set(sessionId.toString(), session.toString());
        System.out.println("key:" + sessionId.toString());
        System.out.println("value:" + session.toString());
		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
//		String sql = "select session from sessions where id=?";
//		List<String> sessionStrList = jdbcTemplate.queryForList(sql,
//				String.class, sessionId);
		redisClient.select(db_num);
		List<String> sessionStrList = redisClient.mget(sessionId.toString());

		if (sessionStrList.get(0) == null)
			return null;
		return SerializableUtils.deserialize(sessionStrList.get(0));
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
		redisClient.select(db_num);
		redisClient.set(session.getId().toString(), session.toString());
	}

	@Override
	protected void doDelete(Session session) {
//		String sql = "delete from sessions where id=?";
////		jdbcTemplate.update(sql, session.getId());
		redisClient.select(db_num);
		redisClient.del(session.getId().toString());
	}
}
