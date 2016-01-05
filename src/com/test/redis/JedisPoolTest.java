package com.test.redis;

/*
 * JedisPoolTest.java
 */

import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis Pool ����
 * @author http://blog.csdn.net/java2000_wl
 * @version <b>1.0</b>
 */
public class JedisPoolTest {

	private static JedisPool jedisPool;
	
	/**
	 * initPoolConfig
	 * <br>------------------------------<br>
	 * @return
	 */
	private static JedisPoolConfig initPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// ����һ��pool����ж��ٸ�״̬Ϊidle��jedisʵ��
		jedisPoolConfig.setMaxActive(1000); 
		// ����ܹ����ֿ���״̬�Ķ�����
		jedisPoolConfig.setMaxIdle(300);
		// ��ʱʱ��
		jedisPoolConfig.setMaxWait(1000);
		// ��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����alidate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
		jedisPoolConfig.setTestOnBorrow(true); 
		// �ڻ����poolʱ���Ƿ���ǰ����validate����
		jedisPoolConfig.setTestOnReturn(true);
		return jedisPoolConfig;
	}
	
	/**
	 * ��ʼ��jedis���ӳ�
	 * <br>------------------------------<br>
	 */
	@BeforeClass
	public static void before() {
		JedisPoolConfig jedisPoolConfig = initPoolConfig();  
		// �����ļ���ȡ������Ϣ
		ResourceBundle bundle = ResourceBundle.getBundle("redis_config");
		String host = bundle.getString("redis.host");
		int port = Integer.valueOf(bundle.getString("redis.port"));
		int timeout = Integer.valueOf(bundle.getString("redis.timeout"));
		String password = bundle.getString("redis.password");
		// �������ӳ�
		jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
	}

	@Test
	public void testSet() {
		Jedis jedis = null;	
		// �ӳ��л�ȡһ��jedisʵ��
		try {
			jedis = jedisPool.getResource();
			jedis.set("blog_pool", "java2000_wl");
		} catch (Exception e) {
			// ���ٶ���
			jedisPool.returnBrokenResource(jedis);
			Assert.fail(e.getMessage());
		} finally {
			// ���ᵽ���ӳ�
			jedisPool.returnResource(jedis);
		}
	}		
	
	@Test
	public void testGet() {
		Jedis jedis = null;	
		try {
			// �ӳ��л�ȡһ��jedisʵ��
			jedis = jedisPool.getResource();
			System.out.println(jedis.get("blog_pool"));
		} catch (Exception e) {
			// ���ٶ���
			jedisPool.returnBrokenResource(jedis);
			Assert.fail(e.getMessage());
		} finally {
			// ���ᵽ���ӳ�
			jedisPool.returnResource(jedis);
		}
	}
}
