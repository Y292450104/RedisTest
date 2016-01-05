package com.test.redis;

/*
 * JedisTest.java
 */

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * jedis 简单使用
 * @author http://blog.csdn.net/java2000_wl
 * @version <b>1.0</b>
 */
public class JedisSimpleTest {

	private Jedis jedis;
	
	/**
	 * 初始化连接
	 * <br>------------------------------<br>
	 */
	@Before
	public void beforeClass() {
		jedis = new Jedis("127.0.0.1");
		jedis.auth("java2000_wl");
	}
	
	/**
	 * set 新增
	 * <br>------------------------------<br>
	 */
	@Test
	public void testSet() {
		jedis.set("blog", "java2000_wl");
	}
	
	/**
	 *  获取
	 * <br>------------------------------<br>
	 */
	@Test
	public void testGet() {
		System.out.println(jedis.get("blog"));
	}
	
	/**
	 * 修改key
	 * <br>------------------------------<br>
	 */
	@Test
	public void testRenameKey() {
		jedis.rename("blog", "blog_new");
	}
	
	/**
	 * 按key删除
	 * <br>------------------------------<br>
	 */
	@Test
	public void testDel() {
		jedis.del("blog_new");
	}
	
	/**
	 * 获取所有的key 
	 * <br>------------------------------<br>
	 */
	@Test
	public void testKeys() {
		System.out.println(jedis.keys("*"));
	}
}

